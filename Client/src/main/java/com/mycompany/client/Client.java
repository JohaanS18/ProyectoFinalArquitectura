package com.mycompany.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    
    public static void main(String[] args) {

        FileService fileService = new FileService();
        Response fileServiceResponse;
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);             
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor. Escriba 'salir' para desconectarse.");
            String filePath="";
            File file = null;
            FileInputStream fileInputStream;
            BufferedInputStream fileReader;

            while (true) {
                System.out.println("Ingrese la ruta del archivo o 'salir':"); // Mensaje inicial del servidor
                filePath = consoleInput.readLine();  // Entrada del usuario
                //serverOutput.println(filePath);
                
                if ("salir".equalsIgnoreCase(filePath)) {
                    System.out.println("Desconectando...");
                    break;
                }

                fileServiceResponse = fileService.validateFile(filePath);

                if (!fileServiceResponse.isSuccess()){
                    System.out.println(fileServiceResponse.getMessage());
                    continue;
                }

                file = (File)fileServiceResponse.getAttachment();

                fileInputStream = new FileInputStream(file);
                fileReader = new BufferedInputStream(fileInputStream);
                
                //Send file to server
                fileReader.transferTo(socket.getOutputStream());

                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
