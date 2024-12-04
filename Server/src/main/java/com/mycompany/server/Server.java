package com.mycompany.server;

import com.mycompany.server.service.FileService;

import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Servidor escuchando en el puerto " + SERVER_PORT);
        FileService fileService = new FileService();
        

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                new Thread(() -> {
                    try {
                        handleClient(clientSocket, fileService);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, FileService fileService) throws NoSuchAlgorithmException {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String filePath;

            while (true) {
                output.println("Especifique la ruta del archivo o 'salir':");
                filePath = input.readLine();

                if ("salir".equalsIgnoreCase(filePath)) {
                    output.println("Desconectado.");
                    break;
                }

                boolean success = fileService.processFile(filePath);
                if (success) {
                    output.println("Archivo procesado exitosamente.");
                } else {
                    output.println("Error al procesar el archivo. Verifique la ruta.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}