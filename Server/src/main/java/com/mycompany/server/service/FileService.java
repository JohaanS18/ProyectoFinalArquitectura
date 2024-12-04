package com.mycompany.server.service;

import com.mycompany.server.dao.FileDAO;
import com.mycompany.server.integration.RabbitMQManager;
import com.mycompany.server.model.FileMetadata;
import com.mycompany.server.utils.FileUtils;



import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


public class FileService {
    private final FileDAO fileDAO = new FileDAO();
    private final RabbitMQManager rabbitMQManager = RabbitMQManager.getInstance();
    
    

public boolean processFile(String filePath) throws IOException, NoSuchAlgorithmException {
    File file = new File(filePath);
    if (!file.exists() || !file.isFile()) {
        System.err.println("Archivo no encontrado o no es un archivo válido: " + filePath);
        return false;
    }

    try {
        FileMetadata metadata = FileUtils.generateMetadata(file);
        Map<String, Object> result = fileDAO.saveFile(metadata, file);
        if (result.containsKey("uuid")) {
            String uuid = (String) result.get("uuid");
            rabbitMQManager.notifyNewFile("Llegó un nuevo archivo con UUID: " + uuid);
            return true;
        }
    } catch (Exception e) {
        System.err.println("Error al procesar el archivo: " + e.getMessage());
        e.printStackTrace();
    }

    return false;
    }
}
