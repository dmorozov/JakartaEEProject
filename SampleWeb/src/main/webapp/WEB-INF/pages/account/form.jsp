<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:if test="account.id == null">Create New Account</s:if><s:else>Edit Account</s:else></h2>

<s:form action="save" namespace="/account" method="post">
    <s:hidden name="account.id"/>

    <div class="form-group">
        <s:label for="account.name" value="Name:"/>
        <s:textfield name="account.name" id="account.name" required="true" placeholder="Enter account name"/>
    </div>

    <div class="form-group">
        <s:label for="account.email" value="Email:"/>
        <s:textfield name="account.email" id="account.email" type="email" required="true" placeholder="Enter email address"/>
    </div>

    <div class="actions">
        <s:submit value="Save" cssClass="btn btn-success"/>
        <s:a action="list" namespace="/account" cssClass="btn btn-secondary">Cancel</s:a>
    </div>
</s:form>
