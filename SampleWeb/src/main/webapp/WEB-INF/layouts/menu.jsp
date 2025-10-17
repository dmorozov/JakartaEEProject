<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<ul>
    <li><s:a action="index" namespace="/">Home</s:a></li>
    <li><s:a action="list" namespace="/account">Accounts</s:a></li>
    <li><s:a action="list" namespace="/contact">Contacts</s:a></li>
    <li><s:a action="list" namespace="/address">Addresses</s:a></li>
    <li><s:a action="remoteTest" namespace="/">Remote EJB Test</s:a></li>
</ul>
