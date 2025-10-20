package com.example.jakartaee.action;

import com.example.jakartaee.remote.HelloWorldRemote;
import org.apache.struts2.ActionSupport;
import jakarta.ejb.EJB;
import jakarta.inject.Named;

/**
 * Example action demonstrating remote EJB calls.
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

    public final String test() {
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
    public final String getTestName() {
      return testName;
    }

    public final void setTestName(final String testName) {
      this.testName = testName;
    }

    public final String getHelloResult() {
      return helloResult;
    }

    public final String getTimeResult() {
      return timeResult;
    }

    public final String getProcessedResult() {
        return processedResult;
    }
}
