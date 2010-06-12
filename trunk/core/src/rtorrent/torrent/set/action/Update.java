package rtorrent.torrent.set.action;

import rtorrent.notice.TorrentNotice;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.TorrentValidateException;
import rtorrent.torrent.set.TorrentSetException;
import rtorrent.utils.LoggableException;

import java.util.Date;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 0:48:36
 */
public class Update extends TorrentSetAction {
    public void action(ActionTorrent torrent) {
        if (torrent.isNeedUpdate()) {
            try {
                //проверяем есть ли файл для апдейта у торрента
                if (torrent.getTorrentFileHash() == null)
                    throw new TorrentSetException("Не найден торрен файл для " + torrent);
                //удаляем старый торрент
                rtorrentService.remove(torrent);
                if (rtorrentService.verify(torrent.getHash())) {
                    throw new TorrentSetException("Не удалось удалить старый " + torrent);
                }
                //обновляем торрент
                torrents.update(torrent);
                //запускаем его
                torrent.setNeedStart(true);
                //добавляем новый торрент
                rtorrentService.add(torrent);
                if (!rtorrentService.verify(torrent.getTorrentFileHash()))
                    throw new TorrentSetException("Не удалось добавить " + torrent);
                torrent.setLastUpdated(new Date());
                notice(torrent, TorrentNotice.UPDATE);
                log.info(torrent + " обновлен");
            } catch (LoggableException e) {
                torrent.setNeedUpdate(true); //востанавливаем статус в случае ошибки
            } catch (TorrentValidateException e) {
                log.error(e);
                //останавливаем торрент, если произошла ошибка валидации
                torrent.setNeedStop(true);
            }
        }
    }
}