<%@ page import="rtorrent.action.ActionManager" %>
<%@ page import="rtorrent.utils.ContextUtils" %>
<%
    ActionManager manager = (ActionManager) ContextUtils.lookup("raction");
%>
<%=manager.doAction("getLog", null)%>