<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>Contact List</h2>

<div style="margin: 20px 0;">
    <s:a action="create" namespace="/contact" cssClass="btn btn-success">Create New Contact</s:a>
</div>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <s:if test="contacts == null || contacts.isEmpty()">
            <tr>
                <td colspan="6" style="text-align: center;">No contacts found</td>
            </tr>
        </s:if>
        <s:else>
            <s:iterator value="contacts" var="contact">
                <tr>
                    <td><s:property value="#contact.id"/></td>
                    <td><s:property value="#contact.firstName"/></td>
                    <td><s:property value="#contact.lastName"/></td>
                    <td><s:property value="#contact.phone"/></td>
                    <td><s:property value="#contact.email"/></td>
                    <td>
                        <s:a action="view" namespace="/contact" cssClass="btn btn-primary">
                            <s:param name="id" value="#contact.id"/>
                            View
                        </s:a>
                        <s:a action="edit" namespace="/contact" cssClass="btn btn-secondary">
                            <s:param name="id" value="#contact.id"/>
                            Edit
                        </s:a>
                        <s:a action="delete" namespace="/contact" cssClass="btn btn-danger"
                             onclick="return confirm('Are you sure you want to delete this contact?');">
                            <s:param name="id" value="#contact.id"/>
                            Delete
                        </s:a>
                    </td>
                </tr>
            </s:iterator>
        </s:else>
    </tbody>
</table>
