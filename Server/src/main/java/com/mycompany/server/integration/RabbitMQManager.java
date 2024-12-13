package com.mycompany.server.integration;

import com.mycompany.server.config.Config;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQManager {
    
    private static RabbitMQManager instance;
    private Connection connection;
    private Channel channel;

    private RabbitMQManager() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            var config = Config.getInstance();
            factory.setHost(config.getHostRabbit());
            factory.setUsername(config.getUserRabbit());
            factory.setPassword(config.getPasswordRabbit());
            factory.setPort(Integer.parseInt(config.getPortRabbit()));
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();
            channel.queueDeclare(config.getQueueRabbit(), true, false, false, null);
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

    public void notifyNewFile(String message) {
        try {
            var config = Config.getInstance();
            channel.basicPublish("", config.getQueueRabbit(), null, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
