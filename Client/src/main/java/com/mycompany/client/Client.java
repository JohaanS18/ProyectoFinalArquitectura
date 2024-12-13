package com.mycompany.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.mycompany.client.services.FileService;
import com.mycompany.client.utils.Response;


public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {

        FileService fileService = new FileService();
        Response fileServiceResponse;
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor. Escriba 'salir' para desconectarse.");
            String filePath="";
            File file = null;
            

            while (true) {
                
                System.out.println("Ingrese la ruta del archivo o 'salir':"); // Mensaje inicial del servidor
                filePath = consoleInput.readLine();  // Entrada del usuario
                
                if ("salir".equalsIgnoreCase(filePath)) {
                    
                    objOut.writeObject(filePath);
                    System.out.println("Desconectando...");
                    objOut.flush();          
                                       
                    break;
                }

                fileServiceResponse = fileService.validateFile(filePath);

                if (!fileServiceResponse.isSuccess()){
                    System.out.println(fileServiceResponse.getMessage());
                    continue;
                }               

                //Get file 
                file = (File)fileServiceResponse.getAttachment();
                //Send file to server
                objOut.writeObject(file);
                objOut.flush();

                //Wait for server status message
                System.out.println("\n"+ serverInput.readLine()+"\n"); 


            }
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
}
