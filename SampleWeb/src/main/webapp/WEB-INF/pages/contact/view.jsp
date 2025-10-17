<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>View Contact</h2>

<div style="margin: 30px 0;">
    <table style="width: 50%;">
        <tr>
            <th style="width: 30%;">ID:</th>
            <td><s:property value="contact.id"/></td>
        </tr>
        <tr>
            <th>First Name:</th>
            <td><s:property value="contact.firstName"/></td>
        </tr>
        <tr>
            <th>Last Name:</th>
            <td><s:property value="contact.lastName"/></td>
        </tr>
        <tr>
            <th>Phone:</th>
            <td><s:property value="contact.phone"/></td>
        </tr>
        <tr>
            <th>Email:</th>
            <td><s:property value="contact.email"/></td>
        </tr>
    </table>
</div>

<div class="actions">
    <s:a action="list" namespace="/contact" cssClass="btn btn-secondary">Back to List</s:a>
    <s:a action="edit" namespace="/contact" cssClass="btn btn-primary">
        <s:param name="id" value="contact.id"/>
        Edit
    </s:a>
    <s:a action="delete" namespace="/contact" cssClass="btn btn-danger"
         onclick="return confirm('Are you sure you want to delete this contact?');">
        <s:param name="id" value="contact.id"/>
        Delete
    </s:a>
</div>

<div style="margin-top: 30px;">
    <h3>Addresses for this Contact</h3>
    <s:a action="list" namespace="/address" cssClass="btn btn-success">
        <s:param name="contactId" value="contact.id"/>
        View Addresses
    </s:a>
</div>
