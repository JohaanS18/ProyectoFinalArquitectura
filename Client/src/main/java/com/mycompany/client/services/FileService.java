package com.mycompany.client.services;


import java.io.File;

import com.mycompany.client.utils.Response;

public class FileService {
    
    private Response response;

    public Response<File> validateFile(String filePath){

        response = new Response<File>();
        
        try {
            File file = new File(filePath);
            

            if (!file.exists()) {
                response.setMessage("Archivo no encontrado. "+ filePath);            
                return response;
            }
    
            if(!file.isFile()){
                response.setMessage("Archivo no válido. "+ filePath);            
                return response;
            }
            
            response.setSuccess(true);
            response.setMessage("Successed");
            response.setAttachment(file);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error al procesar el archivo: " + e.getMessage());
            
        }

        return response;
    }
 

}
