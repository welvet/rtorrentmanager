package rtorrent.web;

import rtorrent.action.ActionManager;
import rtorrent.control.RtorrentControler;
import rtorrent.utils.ContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 19:10:09
 */
public class ActionServlet extends HttpServlet {
    private File saveFile(String contentType, HttpServletRequest request) throws IOException {
        DataInputStream in = new DataInputStream(request.getInputStream());
        int formDataLength = request.getContentLength();
        byte dataBytes[] = new byte[formDataLength];
        int byteRead = 0;
        int totalBytesRead = 0;

        while (totalBytesRead < formDataLength) {
            byteRead = in.read(dataBytes, totalBytesRead,
                    formDataLength);
            totalBytesRead += byteRead;
        }

        String file = new String(dataBytes);

        String saveFile = file.substring(file.indexOf("filename=\"") + 10);
        saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
        saveFile = saveFile.substring(saveFile.lastIndexOf("\\")
                + 1, saveFile.indexOf("\""));
        int lastIndex = contentType.lastIndexOf("=");
        String boundary = contentType.substring(lastIndex + 1,
                contentType.length());
        int pos;

        pos = file.indexOf("filename=\"");
        pos = file.indexOf("\n", pos) + 1;
        pos = file.indexOf("\n", pos) + 1;
        pos = file.indexOf("\n", pos) + 1;
        int boundaryLocation = file.indexOf(boundary, pos) - 4;
        int startPos = ((file.substring(0, pos)).getBytes()).length;
        int endPos = ((file.substring(0, boundaryLocation))
                .getBytes()).length;

        File file1 = new File(((File) ContextUtils.lookup("workdir")).getAbsolutePath() + "/" + saveFile);
        FileOutputStream fileOut = new FileOutputStream(file1);
        fileOut.write(dataBytes, startPos, (endPos - startPos));
        fileOut.flush();
        fileOut.close();
        return file1;
    }


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

        if (actionName.equals("saveTorrent")) {
            File file = saveFile(request.getContentType(), request); 
            RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");
            controler.addTorrent(file);
            return;
        }

        manager.doAction(actionName, null);
    }
}
