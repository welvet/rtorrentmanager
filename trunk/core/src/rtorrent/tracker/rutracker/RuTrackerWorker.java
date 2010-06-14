package rtorrent.tracker.rutracker;

import rtorrent.torrent.TorrentFacade;
import rtorrent.tracker.TorrentWorkerException;
import rtorrent.tracker.TrackerWorker;
import rtorrent.tracker.Trackers;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 0:41:08
 */
public class RuTrackerWorker implements TrackerWorker{
    public void initialize() throws TorrentWorkerException {

    }

    public TorrentFacade work(TorrentFacade torrent) {

        return torrent;
    }

    public Trackers whoIs() {
        return Trackers.RUTRACKER;
    }
}
