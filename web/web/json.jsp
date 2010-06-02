<%@ page language="java" %>
<%@ page contentType="text/html;UTF-8" pageEncoding="UTF-8" %>

<%--отдаем json текст--%>
<div id="dialogBody">
    <jsp:useBean id="dialog" scope="request" class="java.lang.String"/>
    <jsp:include page="<%="dialogs/"+dialog%>" flush="true"/>
</div>
<jsp:useBean id="dialogTitle" scope="request" class="java.lang.String"/>
<input id="title" type="hidden" value="<%=dialogTitle%>">
<input id="needUserNotice" type="hidden" value="true"/>
