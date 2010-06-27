package rtorrent.test;

import rtorrent.client.ClientListner;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 21:11:09
 */
public class ListenerTest extends RtorrentTestCase{
    public void testListener() throws Exception {
        ClientListner listner = new ClientListner();
        listner.start();
        while (true) {
            Thread.sleep(1000L);
        }
    }
}
