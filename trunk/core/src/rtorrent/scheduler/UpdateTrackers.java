package rtorrent.scheduler;

import rtorrent.tracker.TorrentWorkersObserverSingleton;

import java.util.TimerTask;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 0:36:36
 */
public class UpdateTrackers extends TimerTask{
    @Override
    public void run() {
        TorrentWorkersObserverSingleton.run();
    }
}
