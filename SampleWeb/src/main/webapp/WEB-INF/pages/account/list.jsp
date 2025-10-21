<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>Account List</h2>

<div style="margin: 20px 0;">
    <s:a action="create" namespace="/account" cssClass="btn btn-success">Create New Account</s:a>
</div>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Created Date</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <s:if test="accounts == null || accounts.isEmpty()">
            <tr>
                <td colspan="5" style="text-align: center;">No accounts found</td>
            </tr>
        </s:if>
        <s:else>
            <s:iterator value="accounts" var="account">
                <tr>
                    <td><s:property value="#account.id" /></td>
                    <td><s:property value="#account.name"/></td>
                    <td><s:property value="#account.email"/></td>
                    <td><s:date name="#account.createdDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                    <td>
                        <s:a action="view" namespace="/account" cssClass="btn btn-primary">
                            <s:param name="id" value="#account.id"/>
                            View
                        </s:a>
                        <s:a action="edit" namespace="/account" cssClass="btn btn-secondary">
                            <s:param name="id" value="#account.id"/>
                            Edit
                        </s:a>
                        <s:a action="delete" namespace="/account" cssClass="btn btn-danger"
                             onclick="return confirm('Are you sure you want to delete this account?');">
                            <s:param name="id" value="#account.id"/>
                            Delete
                        </s:a>
                    </td>
                </tr>
            </s:iterator>
        </s:else>
    </tbody>
</table>
