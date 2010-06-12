package rtorrent.test;

import java.io.IOException;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:51:07
 */
public class WebServerTest extends RtorrentTestCase {

    public void testStart() throws IOException, InterruptedException {
        //загружаем сервер
        builder.build();
//        Socket socket = new Socket(host, Integer.parseInt(port));
//        assertTrue(socket.isConnected());
        while (true)
            Thread.sleep(1000L);
    }
}
