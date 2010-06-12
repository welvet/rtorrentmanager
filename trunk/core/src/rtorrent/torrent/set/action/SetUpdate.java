package rtorrent.torrent.set.action;

import rtorrent.notice.TorrentNotice;
import rtorrent.service.RtorrentServiceException;
import rtorrent.torrent.ActionTorrent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 0:46:02
 */
public class SetUpdate extends TorrentSetAction {
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
            Boolean needNotice = false;
            ActionTorrent localTorrent = torrentSetImpl.getTorrentFromMap(remoteTorrent);

            //проверяем, скчался ли торрент
            if ((remoteTorrent.isComplite()) && (remoteTorrent.isComplite() != localTorrent.isComplite()))
                needNotice = true;

            localTorrent.updateInfo(remoteTorrent);

            if (needNotice)
                notice(localTorrent, TorrentNotice.FINISH);

            safeTorrenSet.add(localTorrent.getHash());
        }

        //удаляем ненужные торренты
        Collection<ActionTorrent> torrentCollection = torrents.values();

        for (ActionTorrent torrent : torrentCollection) {
            String hash = torrent.getHash();
            if (!safeTorrenSet.contains(hash)) {
                ActionTorrent markRemoveTorrent = torrents.get(hash);

                if ((!markRemoveTorrent.isWatching() && !markRemoveTorrent.isNeedAdd() && !markRemoveTorrent.isNeedUpdate()) || markRemoveTorrent.isNeedDelete()) {
                    torrents.remove(hash);
                    log.info(torrent + " удален из базы");
                }
            }
        }
        log.debug("TorrentSet обновлен");
    }
}
