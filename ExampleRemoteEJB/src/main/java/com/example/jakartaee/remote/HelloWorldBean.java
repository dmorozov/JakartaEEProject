package com.example.jakartaee.remote;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateless;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Stateless
public class HelloWorldBean implements HelloWorldRemote {

    private static final Logger logger = Logger.getLogger(HelloWorldBean.class.getName());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostConstruct
    public void init() {
        logger.info("HelloWorldBean initialized");
    }

    @PreDestroy
    public void destroy() {
        logger.info("HelloWorldBean destroyed");
    }

    @Override
    @PermitAll
    public String sayHello(String name) {
        logger.info("sayHello called with name: " + name);

        if (name == null || name.trim().isEmpty()) {
            name = "World";
        }

        String message = "Hello, " + name + "! Welcome to JakartaEE 10 Remote EJB.";
        logger.info("Returning message: " + message);

        return message;
    }

    @Override
    @PermitAll
    public String getServerTime() {
        logger.info("getServerTime called");

        LocalDateTime now = LocalDateTime.now();
        String timeString = now.format(formatter);

        String message = "Current server time: " + timeString;
        logger.info("Returning: " + message);

        return message;
    }

    @Override
    @PermitAll
    public String processMessage(String message) {
        logger.info("processMessage called with: " + message);

        if (message == null) {
            message = "";
        }

        String processed = "Processed: [" + message.toUpperCase() + "] at " + LocalDateTime.now().format(formatter);
        logger.info("Returning processed message: " + processed);

        return processed;
    }
}
