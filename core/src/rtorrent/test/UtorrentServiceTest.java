package rtorrent.test;

import rtorrent.service.UtorrentService;

/**
 * User: welvet
 * Date: 03.09.2010
 * Time: 23:00:26
 */
public class UtorrentServiceTest extends RtorrentTestCase
{
    private UtorrentService service = new UtorrentService("127.0.0.1", "8080", "admin", "", "gui");

    public void testConnectionUtorrent() throws Exception {
        assertTrue(service.isAlive());
    }

    public void testGetSet() throws Exception {
        service.getSet();
        service.getSet();
    }
}
