package rtorrent.test;

import rtorrent.notice.NoticeService;
import rtorrent.notice.TorrentNotice;
import rtorrent.torrent.ActionTorrent;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 21:54:19
 */
public class MockNoticeService implements NoticeService{
    public void initialize() {
        System.out.println("MockNoticeService инициализирован");
    }

    public void notice(ActionTorrent torrent, TorrentNotice notice) {
        System.out.println(torrent.toString() + notice.toString());
    }
}
