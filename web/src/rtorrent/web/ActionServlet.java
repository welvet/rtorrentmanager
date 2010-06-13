package rtorrent.web;

import rtorrent.action.ActionManager;
import rtorrent.utils.ContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 19:10:09
 */
public class ActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actionName = request.getParameter("action");
        ActionManager manager = (ActionManager) ContextUtils.lookup("raction");

        if (actionName.equals("clearLog")) {
            manager.doAction("clearLog", null);
        }
    }
}
