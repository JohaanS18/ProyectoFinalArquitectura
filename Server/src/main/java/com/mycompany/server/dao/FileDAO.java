package com.mycompany.server.dao;

import com.mycompany.server.model.FileMetadata;
import com.mycompany.server.utils.DBConnection;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FileDAO implements IFileDAO {

    @Override
    public Map<String, Object> saveFile(String metadata, File file) throws IOException, NoSuchAlgorithmException {
        String sql = "INSERT INTO file (namefile, hash, metadata, archivo, uuid) VALUES (?, ?, ?, ?, ?)";
        Map<String, Object> result = new HashMap<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
             FileInputStream fileInputStream = new FileInputStream(file)) {

            // Calcular hash del archivo
            String hash = calculateFileHash(file);

            // Generar un UUID para este archivo
            String uuid = java.util.UUID.randomUUID().toString();

            // Insertar datos
            statement.setString(1, file.getName());               // Nombre del archivo
            statement.setString(2, hash);                        // Hash del archivo
            statement.setString(3, metadata);           // Metadata como JSON
            statement.setBinaryStream(4, fileInputStream);       // Contenido binario del archivo
            statement.setString(5, uuid);                        // UUID generado

            // Ejecutar la consulta
            statement.executeUpdate();

            // Obtener ID generado automáticamente
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1);
                result.put("id", generatedId);
                result.put("uuid", uuid); // Guardamos el UUID generado en el resultado
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String calculateFileHash(File file) throws NoSuchAlgorithmException {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteArray = new byte[1024];
            int bytesCount;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }

            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
