package rtorrent.torrent.set.action;

import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSetException;
import rtorrent.utils.LoggableException;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 0:49:12
 */
public class Delete extends TorrentSetAction {
    public void action(ActionTorrent torrent) {
        if (torrent.isNeedDelete()) {
            try {
                rtorrentService.remove(torrent);
                if (rtorrentService.verify(torrent.getHash()))
                    throw new TorrentSetException("Не удалось удалить " + torrent);
                torrents.remove(torrent.getHash());
                log.info(torrent + " удален");
            } catch (LoggableException e) {
                //ничего не делаем, логирование ошибки в конструкторе исключения
            }
        }
    }
}