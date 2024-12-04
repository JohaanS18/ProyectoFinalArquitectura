package com.mycompany.server.integration;

import com.rabbitmq.client.*;

public class RabbitMQManager {
    private static final String QUEUE_NAME = "file_notifications";
    private static RabbitMQManager instance;
    private Connection connection;
    private Channel channel;

    private RabbitMQManager() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("181.79.9.72");
            factory.setUsername("usuario");
            factory.setPassword("usuario123");
            factory.setPort(6457);
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        } catch (Exception e) {
        System.err.println("Error al configurar RabbitMQ: " + e.getMessage());
        e.printStackTrace();
        }
    }

    public static RabbitMQManager getInstance() {
        if (instance == null) {
            instance = new RabbitMQManager();
        }
        return instance;
    }

    public void notifyNewFile(String metadataJson) {
        try {
            channel.basicPublish("", QUEUE_NAME, null, metadataJson.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
