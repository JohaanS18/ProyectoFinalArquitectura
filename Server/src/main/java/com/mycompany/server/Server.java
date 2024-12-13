package com.mycompany.server;

import com.mycompany.server.config.Config;
import com.mycompany.server.controller.ServerController;
import com.mycompany.server.dao.FileDAO;
import com.mycompany.server.integration.RabbitMQManager;
import com.mycompany.server.service.FileService;
import com.mycompany.server.service.INotificationService;
import com.mycompany.server.service.RabbitMQNotificationService;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) {

        Config config = Config.getInstance();
        var port = config.getPort();
        System.out.println("Servidor escuchando en el puerto " + config.getPort());

        // Crear dependencias
        FileDAO fileDAO = new FileDAO();
        RabbitMQManager rabbitMQManager = RabbitMQManager.getInstance();
        INotificationService notificationService = new RabbitMQNotificationService(rabbitMQManager); // Servicio de notificaciones
        FileService fileService = new FileService(fileDAO, notificationService); // Servicio principal

        // Crear un pool de hilos para manejar clientes
        ExecutorService clientHandlerPool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(config.getPort()))) {
            
            while (true) {

                new ServerController(fileService).handleClient(serverSocket.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientHandlerPool.shutdown();
        }
    }
}