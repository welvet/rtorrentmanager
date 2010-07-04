package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.tracker.SimpleTrackerImpl;
import rtorrent.tracker.TrackerWorker;
import rtorrent.tracker.Trackers;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.LoggerSingleton;

import java.io.File;

/**
 * User: welvet
 * Date: 04.07.2010
 * Time: 14:15:22
 */
public class UpdateTorrent implements Action {
    public void initialize() {
    }

    public Object run(final Object param) {
        new Thread() {
            @Override
            public void run() {
                ActionTorrent torrent = TorrentSetSingleton.getInstance().getByHash((String) param);
                try {
                    SimpleTrackerImpl tracker = (SimpleTrackerImpl) torrent.getTracker();
                    Trackers track = tracker.getTracker();
                    TrackerWorker worker = track.getWorkerClass().newInstance();
                    worker.initialize((File) ContextUtils.lookup("workdir"));
                    TorrentSetSingleton.getInstance().addOrUpdate((ActionTorrent) worker.checkOnceTorrent(torrent));
                } catch (Exception e) {
                    LoggerSingleton.getLogger().warn(e);
                }
            }
        }.start();
        return null;
    }
}
