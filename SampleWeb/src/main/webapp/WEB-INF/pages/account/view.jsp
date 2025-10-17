<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>View Account</h2>

<div style="margin: 30px 0;">
    <table style="width: 50%;">
        <tr>
            <th style="width: 30%;">ID:</th>
            <td><s:property value="account.id"/></td>
        </tr>
        <tr>
            <th>Name:</th>
            <td><s:property value="account.name"/></td>
        </tr>
        <tr>
            <th>Email:</th>
            <td><s:property value="account.email"/></td>
        </tr>
        <tr>
            <th>Created Date:</th>
            <td><s:date name="account.createdDate" format="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </table>
</div>

<div class="actions">
    <s:a action="list" namespace="/account" cssClass="btn btn-secondary">Back to List</s:a>
    <s:a action="edit" namespace="/account" cssClass="btn btn-primary">
        <s:param name="id" value="account.id"/>
        Edit
    </s:a>
    <s:a action="delete" namespace="/account" cssClass="btn btn-danger"
         onclick="return confirm('Are you sure you want to delete this account?');">
        <s:param name="id" value="account.id"/>
        Delete
    </s:a>
</div>

<div style="margin-top: 30px;">
    <h3>Contacts for this Account</h3>
    <s:a action="list" namespace="/contact" cssClass="btn btn-success">
        <s:param name="accountId" value="account.id"/>
        View Contacts
    </s:a>
</div>
