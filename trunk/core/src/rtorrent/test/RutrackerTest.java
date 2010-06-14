package rtorrent.test;

import rtorrent.torrent.ActionTorrent;
import rtorrent.tracker.rutracker.RuTrackerHelper;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 1:02:52
 */
public class RutrackerTest extends RtorrentTestCase {
    public void testConnection() throws Exception {
        if (true) throw new RuntimeException("Для того чтобы этот тест работал нужно ввести логин и пароль");
        RuTrackerHelper helper = new RuTrackerHelper("", "", dir);
        helper.auth();
        assertTrue(helper.checkAuth());
        assertNotNull(helper.checkTorrent("2616371"));
        ActionTorrent torrent = new ActionTorrent(helper.downloadFile("2616371"));
        assertNotNull(torrent.getTorrentFileHash());
    }
}
