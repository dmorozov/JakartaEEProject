<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>View Address</h2>

<div style="margin: 30px 0;">
    <table style="width: 50%;">
        <tr>
            <th style="width: 30%;">ID:</th>
            <td><s:property value="address.id"/></td>
        </tr>
        <tr>
            <th>Street:</th>
            <td><s:property value="address.street"/></td>
        </tr>
        <tr>
            <th>City:</th>
            <td><s:property value="address.city"/></td>
        </tr>
        <tr>
            <th>State:</th>
            <td><s:property value="address.state"/></td>
        </tr>
        <tr>
            <th>Zip Code:</th>
            <td><s:property value="address.zipCode"/></td>
        </tr>
        <tr>
            <th>Country:</th>
            <td><s:property value="address.country"/></td>
        </tr>
    </table>
</div>

<div class="actions">
    <s:a action="list" namespace="/address" cssClass="btn btn-secondary">Back to List</s:a>
    <s:a action="edit" namespace="/address" cssClass="btn btn-primary">
        <s:param name="id" value="address.id"/>
        Edit
    </s:a>
    <s:a action="delete" namespace="/address" cssClass="btn btn-danger"
         onclick="return confirm('Are you sure you want to delete this address?');">
        <s:param name="id" value="address.id"/>
        Delete
    </s:a>
</div>
