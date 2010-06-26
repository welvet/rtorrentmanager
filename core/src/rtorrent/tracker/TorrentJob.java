package rtorrent.tracker;

import org.apache.log4j.Logger;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSet;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.LoggerSingleton;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:13:28
 */
public class TorrentJob implements Runnable {
    private TrackerWorker worker;
    private Set<ActionTorrent> torrents = new HashSet<ActionTorrent>();
    private Logger log = LoggerSingleton.getLogger();
    private TorrentSet torrentSet = TorrentSetSingleton.getInstance();

    public TrackerWorker getWorker() {
        return worker;
    }

    public void setWorker(TrackerWorker worker) {
        this.worker = worker;
    }

    public void addTorrent(ActionTorrent torrent) {
        torrents.add(torrent);
    }

    public void clear() {
        torrents.clear();
    }

    public void run() {
        try {
            //инициализируем торрент сервис
            worker.initialize((File) ContextUtils.lookup("workdir"));
            for (ActionTorrent torrent : torrents) {
                torrentSet.addOrUpdate((ActionTorrent) worker.work(torrent));
                log.debug(torrent + " синхронизирован с трекером");
            }
        } catch (TorrentWorkerException e) {
            log.warn(e);
        } catch (TrackerException e) {
            log.warn(e);
        }
    }
}
