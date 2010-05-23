package rtorrent.serv;

import java.io.File;
import java.io.IOException;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 16:36:15
 */
public class SimpleServerBuilder {
    private static String host = "localhost";
    private static Integer port = 8080;
    private static File rootdir = new File(SimpleServerBuilder.class.getResource("resource/").getPath());
    private static RequestParser requestParser = new RequestParser();

    public static void setHost(String host) {
        SimpleServerBuilder.host = host;
    }

    public static void setPort(Integer port) {
        SimpleServerBuilder.port = port;
    }

    public static void setRootdir(File rootdir) {
        SimpleServerBuilder.rootdir = rootdir;
    }

    /**
     * Задаем парсер, немогу вспомнить почему реализовал так
     * @param requestParser
     */
    public static void setRequestParser(RequestParser requestParser) {
        SimpleServerBuilder.requestParser = requestParser;
    }

    public static SimpleWebServer build() throws IOException {
        return new SimpleWebServer(host, port, rootdir, requestParser);
    }
}
