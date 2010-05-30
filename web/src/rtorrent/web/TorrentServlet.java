package rtorrent.web;

import rtorrent.control.RtorrentControler;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * User: welvet
 * Date: 29.05.2010
 * Time: 17:39:43
 */
public class TorrentServlet extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        try {
            InitialContext context = new InitialContext();
            RtorrentControler controler = (RtorrentControler) context.lookup("rcontroler");
            controler.getList();       
        } catch (NamingException e) {
            e.printStackTrace();  // TODO change me
        }
    }
}
