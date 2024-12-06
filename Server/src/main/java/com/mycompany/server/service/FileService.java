package com.mycompany.server.service;

import com.mycompany.server.dao.IFileDAO;
import com.mycompany.server.model.FileMetadata;
import com.mycompany.server.service.getmetadata.impl.GetMetadataJPG;
import com.mycompany.server.service.getmetadata.impl.GetMetadataPDF;
import com.mycompany.server.service.getmetadata.impl.GetMetadataText;
import com.mycompany.server.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import com.mycompany.server.service.getmetadata.interfaces.GetMetadata;

public class FileService {

    private final IFileDAO fileDAO;
    private final INotificationService notificationService;

    public FileService(IFileDAO fileDAO, INotificationService notificationService) {
        this.fileDAO = fileDAO;
        this.notificationService = notificationService;
    }

    public boolean processFile(String filePath) throws IOException, NoSuchAlgorithmException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.err.println("Archivo no encontrado o no es un archivo válido: " + filePath);
            return false;
        }

        try {
            String metadata = this.getMetadata(filePath);

            Map<String, Object> result = fileDAO.saveFile(metadata, file);
            if (result.containsKey("uuid")) {
                String uuid = (String) result.get("uuid");
                notificationService.notify("Llegó un nuevo archivo con UUID: " + uuid);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
    
    public String getMetadata(String path) {
        GetMetadata meta = this.getInstance(path);
        String metadataJson = meta.getMetadata(path);
        return metadataJson;
    }
    
    public GetMetadata getInstance(String rutaArchivo) {

        String extension = this.obtenerExtension(rutaArchivo);
        extension = extension.toUpperCase();
        if("JPG".equals(extension)) {
            return new GetMetadataJPG();
        } else if("PDF".equals(extension)) {
            return new GetMetadataPDF();
        } else if("DOC".equals(extension) || "DOCX".equals(extension) || "TXT".equals(extension) ) {
            return new GetMetadataText();
        }
        
        return new GetMetadataText();
    }

    private String obtenerExtension(String rutaArchivo) {
        String extension = rutaArchivo.substring(rutaArchivo.lastIndexOf(".")+1, rutaArchivo.length());
        return extension;
    }
    
}
