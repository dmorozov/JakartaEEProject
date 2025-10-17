<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:if test="contact.id == null">Create New Contact</s:if><s:else>Edit Contact</s:else></h2>

<s:form action="save" namespace="/contact" method="post">
    <s:hidden name="contact.id"/>

    <div class="form-group">
        <s:label for="contact.firstName" value="First Name:"/>
        <s:textfield name="contact.firstName" id="contact.firstName" required="true" placeholder="Enter first name"/>
    </div>

    <div class="form-group">
        <s:label for="contact.lastName" value="Last Name:"/>
        <s:textfield name="contact.lastName" id="contact.lastName" required="true" placeholder="Enter last name"/>
    </div>

    <div class="form-group">
        <s:label for="contact.phone" value="Phone:"/>
        <s:textfield name="contact.phone" id="contact.phone" placeholder="Enter phone number"/>
    </div>

    <div class="form-group">
        <s:label for="contact.email" value="Email:"/>
        <s:textfield name="contact.email" id="contact.email" type="email" placeholder="Enter email address"/>
    </div>

    <div class="form-group">
        <s:label for="accountId" value="Account:"/>
        <s:select name="accountId" id="accountId" list="accounts" listKey="id" listValue="name"
                  headerKey="" headerValue="-- Select Account --" required="true"/>
    </div>

    <div class="actions">
        <s:submit value="Save" cssClass="btn btn-success"/>
        <s:a action="list" namespace="/contact" cssClass="btn btn-secondary">Cancel</s:a>
    </div>
</s:form>
