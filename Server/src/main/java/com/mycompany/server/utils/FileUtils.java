package com.mycompany.server.utils;

import com.mycompany.server.model.FileMetadata;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static FileMetadata generateMetadata(File file) {
        String extension = getFileExtension(file);
        String idfield = getFileId(file);
        return new FileMetadata(
                file.getName(),
                idfield,
                file.length(),
                extension
        );
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        return (dotIndex > 0) ? name.substring(dotIndex + 1) : "unknown";
    }
    private static String getFileId(File file) {
        String fileId = "Unknown"; // Valor por defecto en caso de no encontrar el fileId
        Tika tika = new Tika();
        Metadata metadata = new Metadata();

        try (FileInputStream inputStream = new FileInputStream(file)) {
            // Extraer metadata del archivo
            tika.parse(inputStream, metadata);

            // Intentar obtener el fileId de la metadata
            fileId = metadata.get("fileId");
            if (fileId == null || fileId.trim().isEmpty()) {
                fileId = "Unknown"; // Si no está presente, usar el valor por defecto
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileId;
    }
}

