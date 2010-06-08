package rtorrent.torrent.set.action;

import rtorrent.service.RtorrentServiceException;
import rtorrent.torrent.ActionTorrent;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 0:47:45
 */
public class Stop extends TorrentSetAction {
    public void action(ActionTorrent torrent) {
        if (torrent.isNeedStop()) {
            try {
                rtorrentService.stop(torrent);
                torrent.setNeedStop(false);
                log.info(torrent + " остановлен");
            } catch (RtorrentServiceException e) {
                //ничего не делаем, логирование ошибки в конструкторе исключения
            }
        }
    }
}
