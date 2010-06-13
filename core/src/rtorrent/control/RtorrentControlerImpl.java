package rtorrent.control;

import dialog.Dialog;
import org.apache.log4j.Logger;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.TorrentInfo;
import rtorrent.torrent.TorrentValidateException;
import rtorrent.torrent.set.TorrentSet;
import rtorrent.torrent.set.TorrentSetException;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.tracker.SimpleTrackerImpl;
import rtorrent.tracker.TrackerDialog;
import rtorrent.utils.BindContext;
import rtorrent.utils.InContext;
import rtorrent.utils.LoggerSingleton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Делегируем вызовы синглтону
 * <p/>
 * User: welvet
 * Date: 30.05.2010
 * Time: 22:55:31
 */
public class RtorrentControlerImpl implements RtorrentControler, InContext {
    private TorrentSet torrentSet;
    private Logger log = LoggerSingleton.getLogger();    

    public RtorrentControlerImpl() {
        torrentSet = TorrentSetSingleton.getInstance();
        bindContext();
        log.debug("RtorrentControlerImpl инициализирован");
    }

    /**
     * Добавляем себя в контекст
     *
     * @throws TorrentSetException
     */
    public void bindContext() {
        BindContext.bind("rcontroler", this);
    }

    public List<TorrentInfo> getList() {
        List<TorrentInfo> torrents = new ArrayList<TorrentInfo>();
        Set<ActionTorrent> set = torrentSet.getSet();
        for (ActionTorrent torrent : set) {
            TorrentInfoImpl torrentInfo = new TorrentInfoImpl();
            torrentInfo.copyInfo(torrent);
            torrents.add(torrentInfo);
        }
        return torrents;
    }

    public void addTorrent(File torrentFile) {
        ActionTorrent torrent = null;
        try {
            //атрибут needAdd выставляется в конструкторе
            torrent = new ActionTorrent(torrentFile);
            torrentSet.addOrUpdate(torrent);
        } catch (TorrentValidateException e) {
            log.error(e);
        }
    }

    public void startTorrent(String hash) {
        ActionTorrent torrent = torrentSet.getByHash(hash);
        torrent.setNeedStart(true);
        torrentSet.addOrUpdate(torrent);
    }

    public void stopTorrent(String hash) {
        ActionTorrent torrent = torrentSet.getByHash(hash);
        torrent.setNeedStop(true);
        torrentSet.addOrUpdate(torrent);
    }

    public void removeTorrent(String hash) {
        ActionTorrent torrent = torrentSet.getByHash(hash);
        torrent.setNeedDelete(true);
        torrentSet.addOrUpdate(torrent);
    }

    public void configureTorrent(Dialog dialog) {
        TrackerDialog trackerDialog = (TrackerDialog) dialog;
        SimpleTrackerImpl track = new SimpleTrackerImpl(trackerDialog.getUrl(), trackerDialog.getTracekr());
        ActionTorrent torrent = torrentSet.getByHash(trackerDialog.getHash());
        torrent.setWatching(trackerDialog.isWatching());
        torrent.setTracker(track);
        torrentSet.addOrUpdate(torrent);
    }

    public Dialog createTorrentDialog(String hash) {
        ActionTorrent torrent = torrentSet.getByHash(hash);
        return new TrackerDialog(torrent);
    }

    public void notifyUpdate() {
        TorrentSetSingleton.run();
    }
}
