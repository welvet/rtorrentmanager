<%@ page import="dialog.Dialog" %>
<%@ page import="dialog.Input" %>
<%@ page language="java" %>
<%@ page contentType="text/html;UTF-8" pageEncoding="UTF-8" %>
<%
    Dialog dialog = (Dialog) request.getAttribute("currentDialog");
    request.setAttribute("dialogTitle", dialog.getName());
    request.setAttribute("dialogPath", dialog.getPath());
%>
<table id="torrentDialogTable">
    <%for (Input input : dialog.getInputs()) {%>
    <tr <%=input.getFieldDescription() != null && !input.getFieldDescription().isEmpty() ? " title=\"" + input.getFieldDescription() + "\"" : ""%>>
        <td>
            <%=input.getFieldText()%>:
        </td>
        <td>
            <%=input.getHtml()%>
        </td>
    </tr>
    <%}%>
</table>