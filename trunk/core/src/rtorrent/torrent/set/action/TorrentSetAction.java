package rtorrent.torrent.set.action;

import org.apache.log4j.Logger;
import rtorrent.service.RtorrentService;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentHashtable;
import rtorrent.torrent.set.TorrentSetImpl;
import rtorrent.utils.LoggerSingleton;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 0:36:22
 */
public abstract class TorrentSetAction {
    protected TorrentHashtable torrents;
    protected RtorrentService rtorrentService;
    protected TorrentSetImpl torrentSetImpl;
    protected Logger log = LoggerSingleton.getLogger();

    protected TorrentSetAction() {
    }

    public void setDependes(TorrentHashtable torrents, RtorrentService rtorrentService, TorrentSetImpl torrentSetImpl) {
        this.torrents = torrents;
        this.rtorrentService = rtorrentService;
        this.torrentSetImpl = torrentSetImpl;
    }

    public void run() {
        for (ActionTorrent torrent : torrents.values())
            action(torrent);
    }

    protected void action(ActionTorrent torrent) {};
}
