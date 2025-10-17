<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>Address List</h2>

<div style="margin: 20px 0;">
    <s:a action="create" namespace="/address" cssClass="btn btn-success">Create New Address</s:a>
</div>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Street</th>
            <th>City</th>
            <th>State</th>
            <th>Zip Code</th>
            <th>Country</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <s:if test="addresses == null || addresses.isEmpty()">
            <tr>
                <td colspan="7" style="text-align: center;">No addresses found</td>
            </tr>
        </s:if>
        <s:else>
            <s:iterator value="addresses" var="address">
                <tr>
                    <td><s:property value="#address.id"/></td>
                    <td><s:property value="#address.street"/></td>
                    <td><s:property value="#address.city"/></td>
                    <td><s:property value="#address.state"/></td>
                    <td><s:property value="#address.zipCode"/></td>
                    <td><s:property value="#address.country"/></td>
                    <td>
                        <s:a action="view" namespace="/address" cssClass="btn btn-primary">
                            <s:param name="id" value="#address.id"/>
                            View
                        </s:a>
                        <s:a action="edit" namespace="/address" cssClass="btn btn-secondary">
                            <s:param name="id" value="#address.id"/>
                            Edit
                        </s:a>
                        <s:a action="delete" namespace="/address" cssClass="btn btn-danger"
                             onclick="return confirm('Are you sure you want to delete this address?');">
                            <s:param name="id" value="#address.id"/>
                            Delete
                        </s:a>
                    </td>
                </tr>
            </s:iterator>
        </s:else>
    </tbody>
</table>
