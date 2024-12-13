package com.mycompany.server.config;
import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static Config instance;
    private Dotenv dotenv;

    private Config() {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }


    public String getPort() {
       return getProperty("PORT");
    }

    //Rabbit

    public String getHostRabbit() {
        return getProperty("RABBIT_HOST");
    }

    public String getQueueRabbit() {
        return getProperty("RABBIT_QUEUE");
    }

    public String getUserRabbit() {
        return getProperty("RABBIT_USER");
    }

    public String getPasswordRabbit() {
        return getProperty("RABBIT_PASSWORD");
    }

    public String getPortRabbit() {
        return getProperty("RABBIT_PORT");
    }

    //Mysql
    public String getUrlMysql() {
        return getProperty("MYSQL_URL");
    }

    public String getUserMysql() {
        return getProperty("MYSQL_USER");
    }

    public String getPasswordMysql() {
        return getProperty("MYSQL_PASSWORD");
    }

    private String getProperty(String key) {
        String property = System.getenv(key);
        if (property == null) {
            property = dotenv.get(key);
        }
        return property;
    }

}
