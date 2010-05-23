/* 
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of Mini Wegb Server / SimpleWebServer.

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: ServerSideScriptEngine.java,v 1.4 2004/02/01 13:37:35 pjm2 Exp $

*/

package rtorrent.serv;

import rtorrent.serv.parsers.ParserException;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.Date;

/**
 * Copyright Paul Mutton
 * http://www.jibble.org/
 */
public class RequestThread extends Thread {
    private RequestParser parser;


    public RequestThread(Socket socket, File rootDir, RequestParser parser) {
        _socket = socket;
        _rootDir = rootDir;
        this.parser = parser;
    }

    private static void sendHeader(BufferedOutputStream out, int code, String contentType, long contentLength, long lastModified) throws IOException {
        out.write(("HTTP/1.0 " + code + " OK\r\n" +
                "Date: " + new Date().toString() + "\r\n" +
                "Server: JibbleWebServer/1.0\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Expires: Thu, 01 Dec 1994 16:00:00 GMT\r\n" +
                ((contentLength != -1) ? "Content-Length: " + contentLength + "\r\n" : "") +
                "Last-modified: " + new Date(lastModified).toString() + "\r\n" +
                "\r\n").getBytes());
    }

    private static void sendError(BufferedOutputStream out, int code, String message) throws IOException {
        message = message + "<hr>" + SimpleWebServer.VERSION;
        sendHeader(out, code, "text/html", message.length(), System.currentTimeMillis());
        out.write(message.getBytes());
        out.flush();
        out.close();
    }

    public void run() {
        InputStream reader = null;
        try {
            _socket.setSoTimeout(30000);
            BufferedReader in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            BufferedOutputStream out = new BufferedOutputStream(_socket.getOutputStream());

            String request = in.readLine();

            request = request.substring(4, request.length() - 9);
            //либо парсим нашим парсером, либо отдаем файл из ресурсов. TODO inb4 реализовать подобие шаблонов
            String response = parser.parse(request);
            if (!(responseAction(out, response)))
                if (!(responseFile(out, request)))
                    sendError(out, 500, "Don't know what u want ^_^\"");
            out.flush();
            out.close();
        }
        //TODO ВАЖНО! Переделать на нормальный перехват исключений
        catch (IOException e) {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (Exception anye) {
                    // Do nothing.
                }
            }
        } catch (ParserException e) {
            e.printStackTrace();  // TODO change me
        }
    }

    private boolean responseAction(BufferedOutputStream out, String response) throws IOException {
        if ((response != null)) {
            long time = new Date().getTime();
            sendHeader(out, 200, (String) SimpleWebServer.MIME_TYPES.get(".html"), response.length(), time);
            out.write(response.getBytes());
            return true;
        }
        return false;
    }

    private boolean responseFile(BufferedOutputStream out, String request) throws IOException {
        File file = new File(_rootDir, URLDecoder.decode(request, "UTF-8")).getCanonicalFile();
        if (!file.toString().startsWith(_rootDir.toString())) {
            // Uh-oh, it looks like some lamer is trying to take a peek
            // outside of our web root directory.
            sendError(out, 403, "Permission Denied.");
            return  true;
        } else if (!file.exists()) {
            // The file was not found.
            sendError(out, 404, "File Not Found.");
            return true;
        } else if (file.isFile()) {
            returnFile(out, file);
            return true;
        }
        return false;
    }

    private void returnFile(BufferedOutputStream out, File file) throws IOException {
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));

        String contentType = (String) SimpleWebServer.MIME_TYPES.get(SimpleWebServer.getExtension(file));
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        sendHeader(out, 200, contentType, file.length(), file.lastModified());

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = reader.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        reader.close();
    }

    private File _rootDir;
    private Socket _socket;

}