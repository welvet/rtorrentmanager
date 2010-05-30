package rtorrent.torrent.set;

import org.apache.log4j.Logger;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceException;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.LoggerSingleton;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * НЕ ОТДАЕМ РЕАЛЬНУЮ ССЫЛКУ НА ТОРЕНТ, ТОЛЬКО КОПИЮ ТОРЕНТА
 * TODO Возможно стоит перейти с кучи ифоф, на отдельную GetTorrentStrategy
 * User: welvet
 * Date: 19.05.2010
 * Time: 19:33:14
 */
public class TorrentSetImpl implements TorrentSet {
    private TorrentHashtable torrents;
    private RtorrentService rtorrentService;
    private Logger log = LoggerSingleton.getLogger();
    private TorrentSetHelper torrentSetHelper;
    private TorrentSetSaver torrentSetSaver;

    public TorrentSetImpl(RtorrentService rtorrentService, File file) {
        this.rtorrentService = rtorrentService;
        //иниацилизируем именно в таком порядке
        torrentSetSaver = new TorrentSetSaver(this, file);
        torrentSetSaver.load();
        torrentSetHelper = new TorrentSetHelper(this);
        log.info("TorrentSet загружен");
    }

    public void updateSet() {
        new Thread() {
            @Override
            public void run() {
                Set<ActionTorrent> rtorrentSet;
                Set<String> safeTorrenSet = new HashSet<String>();
                try {
                    rtorrentSet = rtorrentService.getSet();
                } catch (RtorrentServiceException e) {
                    return;
                }

                //Обновляем торренты, заодно сохраняем список активных хешей
                for (ActionTorrent remoteTorrent : rtorrentSet) {
                    ActionTorrent localTorrent = getTorrentFromMap(remoteTorrent);
                    localTorrent.updateInfo(remoteTorrent);
                    safeTorrenSet.add(localTorrent.getHash());
                }

                //удаляем ненужные торренты
                Collection<ActionTorrent> torrentCollection = torrents.values();

                for (ActionTorrent torrent : torrentCollection) {
                    String hash = torrent.getHash();
                    if (!safeTorrenSet.contains(hash)) {
                        ActionTorrent markRemoveTorrent = torrents.get(hash);

                        if ((!markRemoveTorrent.isWatching() && !markRemoveTorrent.isNeedAdd() && !markRemoveTorrent.isNeedUpdate()) || markRemoveTorrent.isNeedDelete()  ) {
                            torrents.remove(hash);
                            log.info(torrent + " удален из базы");
                        }
                    }
                }
                log.debug("TorrentSet обновлен");
                torrentSetSaver.save();
            }
        }.start();
    }

    private ActionTorrent getTorrentFromMap(ActionTorrent remoteTorrent) {
        ActionTorrent localTorrent = torrents.get(remoteTorrent.getHash());
        if (localTorrent == null) {
            localTorrent = new ActionTorrent();
            torrents.put(remoteTorrent.getHash(), localTorrent);
        }
        return localTorrent;
    }

    public void updateRtorrent() {
        new Thread() {
            @Override
            public void run() {
                torrentSetHelper.work();
                torrentSetSaver.save();
                log.info("Rtorrent обновлен");
            }
        }.start();
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
            if (torrent.isWatching() && (torrent.isStart() || torrent.isNeedStart()) && !torrent.isNeedStop()) {
                ActionTorrent watchedTorrent = new ActionTorrent();
                watchedTorrent.updateAll(torrent);
                watchedSet.add(watchedTorrent);
            }
        }
        return watchedSet;
    }

    public ActionTorrent getByHash(String hash) {
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
        log.debug(torrent + " обновлен");
        torrentSetSaver.save();
    }

    public void remove(ActionTorrent torrent) {
        ActionTorrent localTorrent = torrents.get(torrent.getHash());
        localTorrent.setNeedDelete(true);
        log.debug(torrent + " помечен на удаление");
        torrentSetSaver.save();
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
