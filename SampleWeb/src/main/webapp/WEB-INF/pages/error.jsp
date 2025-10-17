<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h2>Error</h2>

<div class="message error" style="margin: 20px 0;">
    <h3>An error occurred:</h3>
    <s:if test="hasActionErrors()">
        <s:actionerror/>
    </s:if>
    <s:else>
        <p>An unexpected error occurred. Please try again later.</p>
    </s:else>
</div>

<div style="margin-top: 20px;">
    <s:a action="index" namespace="/" cssClass="btn btn-primary">Go to Home</s:a>
</div>
