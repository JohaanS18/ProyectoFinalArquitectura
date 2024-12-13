package com.mycompany.client;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter serverOutput = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor. Escriba 'salir' para desconectarse.");

            while (true) {
                System.out.println(serverInput.readLine()); // Mensaje inicial del servidor
                String filePath = consoleInput.readLine();  // Entrada del usuario
                serverOutput.println(filePath);

                if ("salir".equalsIgnoreCase(filePath)) {
                    System.out.println("Desconectando...");
                    break;
                }

                System.out.println(serverInput.readLine()); // Respuesta del servidor
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
