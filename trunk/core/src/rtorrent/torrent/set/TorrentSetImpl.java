package rtorrent.torrent.set;

import org.apache.log4j.Logger;
import rtorrent.service.RtorrentService;
import rtorrent.thread.ThreadQueueSingleton;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.LoggerSingleton;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * �� ������ �������� ������ �� ������, ������ ����� �������
 * User: welvet
 * Date: 19.05.2010
 * Time: 19:33:14
 */
public class TorrentSetImpl implements TorrentSet, Runnable {
    private TorrentHashtable torrents;
    private RtorrentService rtorrentService;
    private Logger log = LoggerSingleton.getLogger();
    private TorrentSetHelper torrentSetHelper;
    private TorrentSetSaver torrentSetSaver;

    public TorrentSetImpl(RtorrentService rtorrentService, File file) {
        this.rtorrentService = rtorrentService;
        //�������������� ������ � ����� �������
        torrentSetSaver = new TorrentSetSaver(this, file);
        torrentSetSaver.load();
        torrentSetHelper = new TorrentSetHelper(this);
        log.info("TorrentSet ��������");
    }

    public ActionTorrent getTorrentFromMap(ActionTorrent remoteTorrent) {
        ActionTorrent localTorrent = torrents.get(remoteTorrent.getHash());
        if (localTorrent == null) {
            localTorrent = new ActionTorrent();
            torrents.put(remoteTorrent.getHash(), localTorrent);
        }
        return localTorrent;
    }

    @Deprecated
    public void updateSet() {
        update();
    }

    @Deprecated
    public void updateRtorrent() {
        update();
    }

    public void update() {
        //���� �������� ���, �� �����������
        if (rtorrentService.isAlive())
            ThreadQueueSingleton.add(this);
    }

    public void run() {
        torrentSetHelper.run();
        torrentSetSaver.save();
    }

    public Set<ActionTorrent> getSet() {
        Set<ActionTorrent> set = new HashSet<ActionTorrent>();
        for (ActionTorrent torrent : torrents.values()) {
            ActionTorrent resultTorrent = new ActionTorrent();
            resultTorrent.updateAll(torrent);
            set.add(resultTorrent);
        }
        return set;
    }

    public Set<ActionTorrent> getWatchedSet() {
        Set<ActionTorrent> watchedSet = new HashSet<ActionTorrent>();
        for (ActionTorrent torrent : torrents.values()) {
            //������ ������� ������ ���� �� ����������� � ���� �� �� ����������
            if (torrent.isWatching() && (torrent.isStart() || torrent.isNeedStart()) && !torrent.isNeedStop()) {
                ActionTorrent watchedTorrent = new ActionTorrent();
                watchedTorrent.updateAll(torrent);
                watchedSet.add(watchedTorrent);
            }
        }
        return watchedSet;
    }

    public ActionTorrent getByHash(String hash) {
        //���������� ����� ��������
        ActionTorrent foundedTorrent = torrents.get(hash);
        if (foundedTorrent != null) {
            ActionTorrent torrent = new ActionTorrent();
            torrent.updateAll(foundedTorrent);
            return torrent;
        }
        return null;
    }

    public void addOrUpdate(ActionTorrent torrent) {
        ActionTorrent localTorrent = getTorrentFromMap(torrent);
        localTorrent.updateAll(torrent);
        log.debug(torrent + " ��������");
        torrentSetSaver.save();
    }

    public void setService(RtorrentService service) {
        rtorrentService = service;
        torrentSetHelper = new TorrentSetHelper(this);
    }

    TorrentHashtable getTorrents() {
        return torrents;
    }

    RtorrentService getRtorrentService() {
        return rtorrentService;
    }

    Logger getLog() {
        return log;
    }

    void setTorrents(TorrentHashtable torrents) {
        this.torrents = torrents;
    }
}
