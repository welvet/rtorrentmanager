package rtorrent.torrent.set.action;

import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSetException;
import rtorrent.utils.LoggableException;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 0:48:11
 */
public class Add extends TorrentSetAction {
    public void action(ActionTorrent torrent) {
        if (torrent.isNeedAdd()) {
            try {
                rtorrentService.add(torrent);
                if (!rtorrentService.verify(torrent.getHash()))
                    throw new TorrentSetException("Не удалось добавить " + torrent);
                log.info(torrent + " добавлен");
                //Меняем состояние
                torrent.setNeedStart(true);
            } catch (LoggableException e) {
                //ничего не делаем, логирование ошибки в конструкторе исключения
            }
        }
    }
}