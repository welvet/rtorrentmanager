package rtorrent.web;

import org.apache.commons.fileupload.FileUploadException;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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
            copyFile(((DiskFileItem) items.get(0)).getStoreLocation(), file);
            RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");
            controler.addTorrent(file);
        } catch (FileUploadException e) {
            e.printStackTrace();  // TODO change me
        }

    }

    public static void copyFile(File in, File out)
            throws IOException {
        FileChannel inChannel = new
                FileInputStream(in).getChannel();
        FileChannel outChannel = new
                FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(),
                    outChannel);
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }

}
