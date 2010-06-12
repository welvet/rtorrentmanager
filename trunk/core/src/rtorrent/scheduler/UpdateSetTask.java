package rtorrent.scheduler;

import rtorrent.torrent.set.TorrentSetSingleton;

import java.util.TimerTask;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 0:34:39
 */
public class UpdateSetTask extends TimerTask{
    @Override
    public void run() {
        TorrentSetSingleton.run();
    }
}
