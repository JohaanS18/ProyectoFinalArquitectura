/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.controller;
import com.mycompany.server.service.FileService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Johan Sebastian
 */


public class ServerController extends Thread{
    private final FileService fileService;
    private Socket clientSocket;

    public ServerController(FileService fileService) {
        this.fileService = fileService;
    }
    

    public void handleClient(Socket socket) {
        
        this.clientSocket=socket;
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
        start();
    }


    @Override
    public void run() {

            try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ObjectInputStream ojectInput = new ObjectInputStream(clientSocket.getInputStream());
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {

            File file;
            Object obj= null;
            BufferedReader buffer;
            boolean success = false;
            String statusMesage ="Error al procesar el archivo. Verifique la ruta.";

            System.out.println("listen..."+ clientSocket.toString());
            while ((obj = ojectInput.readObject()) != null) {
                
                
                if ("salir".equalsIgnoreCase(obj.toString())) {
                    output.println("Desconectado. "+ clientSocket.getInetAddress());                    
                    break;
                }

                if(obj instanceof File){
                    
                    file = (File)obj;
                    System.out.println(file.getName());
                    
                    success = fileService.processFile(file);
                }

                if(success)
                    statusMesage =  "Archivo procesado exitosamente.";                             
                
                output.println(statusMesage);

            }

            //Close soket
            clientSocket.close();
           
            
            } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            
        
        
    }
}