package rtorrent.test;

import rtorrent.tracker.rutracker.RuTrackerHelper;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 1:02:52
 */
public class RutrackerTest extends RtorrentTestCase {
    public void testConnection() throws Exception {
        RuTrackerHelper helper = new RuTrackerHelper("welvet", "");
        helper.auth();
        assertTrue(helper.checkAuth());
//        helper.checkTorrent("2616371");
        helper.downloadFile("2616371");
    }
}
