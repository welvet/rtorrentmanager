package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.torrent.set.TorrentSetSingleton;

/**
 * User: welvet
 * Date: 26.06.2010
 * Time: 21:48:33
 */
public class CheckRtorrent implements Action{
    public void initialize() {

    }

    public Object run(Object param) {
        return TorrentSetSingleton.getInstance().isForceShutdown();
    }
}
