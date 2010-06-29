package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.tracker.SimpleTrackerImpl;

/**
 * User: welvet
 * Date: 29.06.2010
 * Time: 21:02:01
 */
public class GetTorrentUrl implements Action {
    public void initialize() {

    }

    public Object run(Object param) {
        ActionTorrent torrent = TorrentSetSingleton.getInstance().getByHash((String) param);
        if (torrent.getTracker() instanceof SimpleTrackerImpl) {
            SimpleTrackerImpl tracker = (SimpleTrackerImpl) torrent.getTracker();
            return tracker.getTracker().getUrl() + tracker.getUrl();
        }
        return null;
    }
}
