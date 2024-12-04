/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server.service;

import com.mycompany.server.integration.RabbitMQManager;

/**
 *
 * @author Johan Sebastian
 */
public class RabbitMQNotificationService implements INotificationService {
    private final RabbitMQManager rabbitMQManager;

    public RabbitMQNotificationService(RabbitMQManager rabbitMQManager) {
        this.rabbitMQManager = rabbitMQManager;
    }

@Override
    public void notify(String message) {
        try {
            rabbitMQManager.notifyNewFile(message);
        } catch (Exception e) {
            System.err.println("Error al enviar notificación a RabbitMQ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}