package rtorrent.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 19:04:27
 */
public class SettingsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("path") != null) {
            DialogHelper.updateDialog(request.getParameterMap());
            return;
        }

        if (request.getAttribute("dialog") == null)
            request.setAttribute("dialog", request.getParameter("dialog"));
        DialogHelper.createDialog(request);              
        request.getRequestDispatcher("/json.jsp").forward(request, response);
    }
}
