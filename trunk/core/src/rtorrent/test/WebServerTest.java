package rtorrent.test;

import java.io.IOException;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:51:07
 */
public class WebServerTest extends RtorrentTestCase {

    public void testStart() throws IOException, InterruptedException {
        try {//��������� ������
        builder.build();
            while (true) {
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            fail();
        }
    }
}