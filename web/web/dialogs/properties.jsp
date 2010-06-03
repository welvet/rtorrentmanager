<%@ page import="lib.dialog.Dialog" %>
<%@ page import="lib.dialog.Input" %>
<%@ page language="java" %>
<%@ page contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<%
    Dialog dialog = (Dialog) request.getAttribute("currentDialog");
    request.setAttribute("dialogTitle", dialog.getName());
    request.setAttribute("dialogPath", dialog.getPath());
%>
<% for (Input input : dialog.getInputs()) {

%>
<%=input.getFieldText()%> <%=input.getHtml()%> <br/>
<%
    }
%>