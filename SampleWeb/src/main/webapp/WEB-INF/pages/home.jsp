<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>Welcome to JakartaEE 10 with Struts 7</h2>

<div style="margin-top: 30px;">
    <h3>Project Features:</h3>
    <ul style="margin-left: 20px; line-height: 2;">
        <li>JakartaEE 10 Platform</li>
        <li>Apache Struts 7 MVC Framework</li>
        <li>Struts Tiles for Layout Management</li>
        <li>Stateless EJB Session Beans</li>
        <li>Remote EJB Interface</li>
        <li>JPA 3.1 with PostgreSQL</li>
        <li>JDBC Alternative Implementation with Repository Pattern</li>
        <li>Security Integration between Web and EJB layers</li>
        <li>Maven Multi-Module Project Structure</li>
    </ul>
</div>

<div style="margin-top: 30px;">
    <h3>Quick Links:</h3>
    <div style="margin-top: 15px;">
        <s:a action="list" namespace="/account" cssClass="btn btn-primary">Manage Accounts</s:a>
        <s:a action="list" namespace="/contact" cssClass="btn btn-primary">Manage Contacts</s:a>
        <s:a action="list" namespace="/address" cssClass="btn btn-primary">Manage Addresses</s:a>
        <s:a action="remoteTest" namespace="/" cssClass="btn btn-success">Test Remote EJB</s:a>
    </div>
</div>

<div style="margin-top: 30px; padding: 15px; background: #f0f0f0; border-radius: 4px;">
    <h4>Message: <s:property value="message"/></h4>
</div>
