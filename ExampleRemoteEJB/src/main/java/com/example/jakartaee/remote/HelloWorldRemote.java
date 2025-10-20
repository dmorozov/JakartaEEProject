package com.example.jakartaee.remote;

import jakarta.ejb.Remote;

@Remote
public interface HelloWorldRemote {

    /**
     * Simple hello world method that returns a greeting message.
     *
     * @param name The name to greet
     * @return A greeting message
     */
    String sayHello(String name);

    /**
     * Returns the current server time.
     *
     * @return Server time as string
     */
    String getServerTime();

    /**
     * Returns a custom message with additional information.
     *
     * @param message Custom message
     * @return Processed message
     */
    String processMessage(String message);
}
