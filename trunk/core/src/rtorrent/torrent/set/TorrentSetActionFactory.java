package rtorrent.torrent.set;

import org.apache.log4j.Logger;
import rtorrent.service.RtorrentService;
import rtorrent.torrent.set.action.TorrentSetAction;
import rtorrent.utils.LoggerSingleton;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 0:35:28
 */
public class TorrentSetActionFactory {
    private TorrentHashtable torrents;
    private RtorrentService rtorrentService;
    private Logger log = LoggerSingleton.getLogger();
    private TorrentSetImpl torrentSetImpl;

    public TorrentSetActionFactory(RtorrentService rtorrentService, TorrentSetImpl torrentSetImpl) {
        this.torrents = torrentSetImpl.getTorrents();
        this.rtorrentService = rtorrentService;
        this.torrentSetImpl = torrentSetImpl;
    }

    public TorrentSetAction build(Class<? extends TorrentSetAction> aClass) {
        try {
            TorrentSetAction action = aClass.newInstance();
            action.setDependes(torrents, rtorrentService, torrentSetImpl);
            return action;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
}
