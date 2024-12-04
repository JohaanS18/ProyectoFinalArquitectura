package com.mycompany.server;

import com.mycompany.server.controller.ServerController;
import com.mycompany.server.dao.FileDAO;
import com.mycompany.server.integration.RabbitMQManager;
import com.mycompany.server.service.FileService;
import com.mycompany.server.service.INotificationService;
import com.mycompany.server.service.RabbitMQNotificationService;


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

        // Crear dependencias
        FileDAO fileDAO = new FileDAO();
        RabbitMQManager rabbitMQManager = RabbitMQManager.getInstance();
        INotificationService notificationService = new RabbitMQNotificationService(rabbitMQManager); // Servicio de notificaciones
        FileService fileService = new FileService(fileDAO, notificationService); // Servicio principal

        // Crear el controlador
        ServerController serverController = new ServerController(fileService);

        // Crear un pool de hilos para manejar clientes
        ExecutorService clientHandlerPool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Manejar al cliente en un hilo separado
                clientHandlerPool.execute(() -> {
                    try {
                        serverController.handleClient(clientSocket);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientHandlerPool.shutdown();
        }
    }
}