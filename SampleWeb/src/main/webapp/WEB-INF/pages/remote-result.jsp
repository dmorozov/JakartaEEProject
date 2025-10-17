<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>Remote EJB Test Results</h2>

<div style="margin-top: 20px;">
    <h3>Test Results:</h3>

    <div style="margin: 20px 0; padding: 15px; background: #e7f3ff; border-left: 4px solid #2196F3;">
        <h4>sayHello() Result:</h4>
        <p><s:property value="helloResult"/></p>
    </div>

    <div style="margin: 20px 0; padding: 15px; background: #e8f5e9; border-left: 4px solid #4CAF50;">
        <h4>getServerTime() Result:</h4>
        <p><s:property value="timeResult"/></p>
    </div>

    <div style="margin: 20px 0; padding: 15px; background: #fff3e0; border-left: 4px solid #FF9800;">
        <h4>processMessage() Result:</h4>
        <p><s:property value="processedResult"/></p>
    </div>
</div>

<div style="margin-top: 30px;">
    <s:a action="index" namespace="/" cssClass="btn btn-secondary">Back to Home</s:a>
    <s:a action="remoteTest" namespace="/" cssClass="btn btn-primary">Run Test Again</s:a>
</div>

<div style="margin-top: 30px; padding: 15px; background: #f9f9f9; border-radius: 4px;">
    <h4>About Remote EJB:</h4>
    <p>This demonstrates calling a remote EJB (HelloWorldRemote) from a Struts action.
    The remote EJB is deployed separately and can be called from different applications or even different servers.</p>
    <p>The @EJB annotation in the Struts action injects the remote EJB interface,
    allowing seamless communication between the web layer and the EJB layer.</p>
</div>
