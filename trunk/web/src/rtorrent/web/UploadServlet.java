package rtorrent.web;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import rtorrent.control.RtorrentControler;
import rtorrent.utils.ContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 17:53:52
 */
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();

        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List /* FileItem */ items = upload.parseRequest(req);
            File file = new File(((File) ContextUtils.lookup("workdir")).getAbsolutePath() + '/' + System.currentTimeMillis() + ".torrent");
            ((DiskFileItem) items.get(0)).write(file);
            RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");
            controler.addTorrent(file);
        } catch (Exception e) {
            e.printStackTrace();  // TODO change me
        }

    }
}
