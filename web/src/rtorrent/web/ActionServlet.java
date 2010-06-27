package rtorrent.web;

import rtorrent.action.ActionManager;
import rtorrent.utils.ContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

        if (actionName.equals("help")) {
            request.getRequestDispatcher("/dialogs/help.jsp").forward(request, response);
            return;
        }
        if (actionName.equals("about")) {
            request.getRequestDispatcher("/dialogs/about.jsp").forward(request, response);
            return;
        }

        if (actionName.equals("addTorrent")) {
            request.getRequestDispatcher("/dialogs/addTorrent.jsp").forward(request, response);
            return;
        }

        if (actionName.equals("rss")) {
            File rss = new File(((File) ContextUtils.lookup("workdir")).getAbsolutePath() + "/" + "rss.xml");
            if (!rss.isFile())
                return;
            BufferedReader reader = new BufferedReader(new FileReader(rss));
            String s = reader.readLine();
            while (s != null) {
                response.getWriter().write(s);
                s = reader.readLine();
            }
            return;
        }

        if (actionName.equals("checkRtorrent")) {
            String s;
            if (!(Boolean) manager.doAction("checkRtorrent", null)) s = "start";
            else s = "stop";
            request.getRequestDispatcher("/images/" + s + "_a.png").forward(request, response);
        }

        manager.doAction(actionName, null);
    }
}
