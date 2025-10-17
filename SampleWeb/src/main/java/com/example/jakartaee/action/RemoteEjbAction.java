package com.example.jakartaee.action;

import com.example.jakartaee.remote.HelloWorldRemote;
import org.apache.struts2.ActionSupport;
import jakarta.ejb.EJB;
import jakarta.inject.Named;

/**
 * Example action demonstrating remote EJB calls
 */
@Named
public class RemoteEjbAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    @EJB
    private HelloWorldRemote helloWorldRemote;

    private String testName = "JakartaEE User";
    private String helloResult;
    private String timeResult;
    private String processedResult;

    public String test() {
        try {
            // Call remote EJB methods
            helloResult = helloWorldRemote.sayHello(testName);
            timeResult = helloWorldRemote.getServerTime();
            processedResult = helloWorldRemote.processMessage("Testing Remote EJB");

            addActionMessage("Remote EJB calls executed successfully!");
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error calling remote EJB: " + e.getMessage());
            e.printStackTrace();
            return ERROR;
        }
    }

    // Getters and Setters
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getHelloResult() {
        return helloResult;
    }

    public String getTimeResult() {
        return timeResult;
    }

    public String getProcessedResult() {
        return processedResult;
    }
}
