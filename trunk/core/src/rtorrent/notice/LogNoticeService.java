package rtorrent.notice;

import org.apache.log4j.Logger;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.LoggerSingleton;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 1:39:14
 */
public class LogNoticeService implements NoticeService {
    private static Logger log = LoggerSingleton.getLogger();

    public void initialize() {

    }

    public void notice(ActionTorrent torrent, TorrentNotice notice) {
        log.info(torrent + " " + notice);
    }

    public boolean checkConfig() {
        return true;
    }
}
