package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.torrent.set.TorrentSet;
import rtorrent.torrent.set.TorrentSetSingleton;

/**
 * User: welvet
 * Date: 26.06.2010
 * Time: 21:49:37
 */
public class SwitchTorrent implements Action {
    public void initialize() {

    }

    public Object run(Object param) {
        TorrentSet torrentSet = TorrentSetSingleton.getInstance();
        if (torrentSet.isForceShutdown())
            torrentSet.forceLaunch();
        else torrentSet.forceShutdown();
        return null;
    }
}
