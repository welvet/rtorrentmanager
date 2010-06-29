package rtorrent.scheduler;

import rtorrent.torrent.set.TorrentSetSingleton;

import java.util.TimerTask;

/**
 * User: welvet
 * Date: 29.06.2010
 * Time: 21:49:51
 */
public class RestartWhenClientStop extends TimerTask {
    private static final int COUNT = 4;
    private static Integer iter = COUNT;

    public static void restartIter() {
        iter = COUNT;
    }

    @Override
    public void run() {
        if (iter == 0) {
            TorrentSetSingleton.getInstance().forceLaunch();
        }
        iter--;
    }
}
