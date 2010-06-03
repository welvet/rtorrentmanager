package rtorrent.web;

import com.google.gson.Gson;
import rtorrent.control.RtorrentControler;

import javax.naming.InitialContext;
import javax.naming.NamingException;
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

    private class nullAnsw {
        private static final String FALSE = "false";
        private String needUserNotice = FALSE;

        public String getNeedUserNotice() {
            return needUserNotice;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InitialContext context = null;
        try {
            context = new InitialContext();
            RtorrentControler controler = (RtorrentControler) context.lookup("rcontroler");

            //определяемся с действием, создаем диалог
            String action = request.getParameter("action");
            String hash = request.getParameter("hash");
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

            //редиректим на jsp с диалогом
//            todo инфо торрента нужно передавать тут же
            //я тоже хочу как попов, у меня будет свой mvc ^_^
            //создаем диалог
            DialogHelper.createDialog(request);
            getServletConfig().getServletContext()
                    .getRequestDispatcher("/json.jsp").forward(request, response);
        } catch (NamingException e) {
            e.printStackTrace();  // TODO change me
        }
    }
}
