package com.example.jakartaee.action;

import org.apache.struts2.ActionSupport;

import jakarta.inject.Named;

@Named
public class HomeAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public String execute() {
        message = "Welcome to JakartaEE 10 with Struts 7!";
        return SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
