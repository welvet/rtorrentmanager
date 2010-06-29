package rtorrent.web;

import com.google.gson.Gson;
import rtorrent.action.ActionManager;
import rtorrent.control.RtorrentControler;
import rtorrent.utils.ContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: welvet
 * Date: 02.06.2010
 * Time: 22:34:38
 */
public class TorrentServlet extends HttpServlet {
    private static final String START = "start";
    private static final String STOP = "stop";
    private static final String REMOVE = "remove";
    private static final String PROPERTIES = "properties";
    private static final String REDIRECT = "redirect";

    private class nullAnsw {
        private String needUserNotice = "false";

        public String getNeedUserNotice() {
            return needUserNotice;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");

        //определяемся с действием, создаем диалог
        String action = request.getParameter("action");
        String hash = request.getParameter("hash");

        if (action.equals(REDIRECT)) {
            ActionManager manager = (ActionManager) ContextUtils.lookup("raction");
            String url = (String) manager.doAction("getTorrentUrl", hash);
            response.sendRedirect(url);
            return;
        }

        if (action.equals(START))
            controler.startTorrent(hash);
        if (action.equals(STOP))
            controler.stopTorrent(hash);
        if (action.equals(REMOVE))
            controler.removeTorrent(hash);
        if (!action.equals(PROPERTIES)) {
            Gson gson = new Gson();
            String s = gson.toJson(new nullAnsw());
            response.getWriter().print(s);
            return;
        }
        request.setAttribute("torrentHash", hash);
        request.getRequestDispatcher("/settings/").forward(request, response);
    }
}
