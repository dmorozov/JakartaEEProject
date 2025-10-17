<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2><s:if test="address.id == null">Create New Address</s:if><s:else>Edit Address</s:else></h2>

<s:form action="save" namespace="/address" method="post">
    <s:hidden name="address.id"/>

    <div class="form-group">
        <s:label for="address.street" value="Street:"/>
        <s:textfield name="address.street" id="address.street" required="true" placeholder="Enter street address"/>
    </div>

    <div class="form-group">
        <s:label for="address.city" value="City:"/>
        <s:textfield name="address.city" id="address.city" required="true" placeholder="Enter city"/>
    </div>

    <div class="form-group">
        <s:label for="address.state" value="State:"/>
        <s:textfield name="address.state" id="address.state" placeholder="Enter state/province"/>
    </div>

    <div class="form-group">
        <s:label for="address.zipCode" value="Zip Code:"/>
        <s:textfield name="address.zipCode" id="address.zipCode" placeholder="Enter zip/postal code"/>
    </div>

    <div class="form-group">
        <s:label for="address.country" value="Country:"/>
        <s:textfield name="address.country" id="address.country" required="true" placeholder="Enter country"/>
    </div>

    <div class="form-group">
        <s:label for="contactId" value="Contact:"/>
        <s:select name="contactId" id="contactId" list="contacts" listKey="id" listValue="fullName"
                  headerKey="" headerValue="-- Select Contact --" required="true"/>
    </div>

    <div class="actions">
        <s:submit value="Save" cssClass="btn btn-success"/>
        <s:a action="list" namespace="/address" cssClass="btn btn-secondary">Cancel</s:a>
    </div>
</s:form>
