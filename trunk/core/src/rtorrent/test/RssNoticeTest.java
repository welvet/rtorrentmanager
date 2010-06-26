package rtorrent.test;

import rtorrent.notice.TorrentNotice;
import rtorrent.notice.rss.RssNoticeService;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.Saver;

import java.util.ArrayList;

/**
 * User: welvet
 * Date: 26.06.2010
 * Time: 18:17:53
 */
public class RssNoticeTest extends RtorrentTestCase {
    public void testSimpleNotice() throws Exception {
        Saver saver = new Saver("rss.dat");
        saver.save(new ArrayList());
        RssNoticeService service = new RssNoticeService();
        service.initialize();
        for (ActionTorrent torrent : torrentSet.getSet()) {
            service.notice(torrent, TorrentNotice.FINISH);
        }
        assertEquals(saver.load().size(), torrentSet.getSet().size());
    }
}
