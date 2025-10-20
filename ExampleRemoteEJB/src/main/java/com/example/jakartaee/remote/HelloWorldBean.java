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

  private static final Logger LOG = Logger.getLogger(HelloWorldBean.class.getName());
  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @PostConstruct
  public final void init() {
    LOG.info("HelloWorldBean initialized");
  }

  @PreDestroy
  public final void destroy() {
    LOG.info("HelloWorldBean destroyed");
  }

  @Override
  @PermitAll
  public final String sayHello(final String nameValue) {
    LOG.info("sayHello called with name: " + nameValue);

    String name = nameValue;
    if (name == null || name.trim().isEmpty()) {
      name = "World";
    }

    String message = "Hello, " + name + "! Welcome to JakartaEE 10 Remote EJB.";
    LOG.info("Returning message: " + message);

    return message;
  }

  @Override
  @PermitAll
  public final String getServerTime() {
    LOG.info("getServerTime called");

    LocalDateTime now = LocalDateTime.now();
    String timeString = now.format(FORMATTER);

    String message = "Current server time: " + timeString;
    LOG.info("Returning: " + message);

    return message;
  }

  @Override
  @PermitAll
  public final String processMessage(final String messageValue) {
    LOG.info("processMessage called with: " + messageValue);

    String message = messageValue;
    if (message == null) {
      message = "";
    }

    String processed =
        "Processed: [" + message.toUpperCase() + "] at " + LocalDateTime.now().format(FORMATTER);
    LOG.info("Returning processed message: " + processed);

    return processed;
  }
}
