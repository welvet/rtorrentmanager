package rtorrent.torrent.set.action;

import rtorrent.service.RtorrentServiceException;
import rtorrent.torrent.ActionTorrent;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 0:47:22
 */
public class Start extends TorrentSetAction {
    public void action(ActionTorrent torrent) {
        if (torrent.isNeedStart()) {
            try {
                rtorrentService.start(torrent);
                torrent.setNeedStart(false);
                log.info(torrent + " запущен");
            } catch (RtorrentServiceException e) {
                //ничего не делаем, логирование ошибки в конструкторе исключения
            }
        }
    }
}
