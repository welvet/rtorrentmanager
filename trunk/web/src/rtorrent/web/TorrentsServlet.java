package rtorrent.web;

import com.google.gson.Gson;
import rtorrent.control.RtorrentControler;
import rtorrent.torrent.TorrentInfo;
import rtorrent.utils.ContextUtils;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 29.05.2010
 * Time: 17:39:43
 */
public class TorrentsServlet extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }
    //обертка для корректной работы json парсера

    private class JsonList {
        private List aaData;

        private JsonList(List aaData) {
            this.aaData = aaData;
        }

        public List getAaData() {
            return aaData;
        }
    }

    //парсим лист в json
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");
        List<TorrentInfo> list = controler.getList();
        final Gson gson = new Gson();

        final List objects = new ArrayList();
        for (TorrentInfo aList : list) {
            objects.add(aList.toArray());
        }
        JsonList jsonList = new JsonList(objects);
        String rsp = gson.toJson(jsonList, JsonList.class);
        response.getWriter().print(rsp);
    }
}
