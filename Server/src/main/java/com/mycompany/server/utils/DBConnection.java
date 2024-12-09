package com.mycompany.server.utils;

import com.mycompany.server.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        var config = Config.getInstance();
        return DriverManager.getConnection(config.getUrlMysql(),config.getUserMysql(),config.getPasswordMysql());
    }
}
