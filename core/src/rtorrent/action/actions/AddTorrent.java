package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.client.AddTorrentMessage;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.tracker.SimpleTrackerImpl;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.TrackerUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * User: welvet
 * Date: 29.06.2010
 * Time: 22:51:54
 */
public class AddTorrent implements Action {

    public void initialize() {

    }

    public Object run(Object param) {
        try {
            AddTorrentMessage message = (AddTorrentMessage) param;
            File file = new File(((File) ContextUtils.lookup("workdir")).getAbsolutePath() + '/' + System.currentTimeMillis() + ".torrent");

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(message.getBytes());
            out.flush();
            out.close();
            ActionTorrent torrent = new ActionTorrent(file);
            SimpleTrackerImpl tracker = new SimpleTrackerImpl(message.getUrl(), TrackerUtils.string2trackers(message.getTracker()));
            torrent.setWatching(message.getWatched());
            torrent.setTracker(tracker);
            TorrentSetSingleton.getInstance().addOrUpdate(torrent);
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }
}
