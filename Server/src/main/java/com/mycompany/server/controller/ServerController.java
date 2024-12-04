/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.controller;
import com.mycompany.server.service.FileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Johan Sebastian
 */


public class ServerController {
    private final FileService fileService;

    public ServerController(FileService fileService) {
        this.fileService = fileService;
    }

    public void handleClient(Socket clientSocket) throws NoSuchAlgorithmException {
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