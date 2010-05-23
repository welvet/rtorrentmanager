package rtorrent.torrent.set;

import org.apache.log4j.Logger;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceException;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.TorrentHashtable;
import rtorrent.utils.LoggableException;

import java.util.Date;

class TorrentSetHelper {
    private TorrentHashtable torrents;
    private RtorrentService rtorrentService;
    private Logger log;

    public TorrentSetHelper(TorrentSetImpl torrentSetImpl) {
        torrents = torrentSetImpl.getTorrents();
        rtorrentService = torrentSetImpl.getRtorrentService();
        log = torrentSetImpl.getLog();
    }

    void work() {
        updateStart();
        updateStop();
        updateAdd();
        updateUpdate();
        updateDelete();
    }

    /**
     * Этот и следующий методы, не имеют проверок.
     */
    void updateStart() {
        for (ActionTorrent torrent : torrents.values()) {
            if (torrent.isNeedStart()) {
                try {
                    rtorrentService.start(torrent);
                    torrent.setNeedStart(false);
                    log.debug(torrent + " запущен");
                } catch (RtorrentServiceException e) {
                    return;    //все кетчи перехватывают исключения, возникшие из-за неправильной связи с сервером.
                    // Логгирование ошибки происходит в конструкторе исключения
                }
            }
        }
    }

    void updateStop() {
        for (ActionTorrent torrent : torrents.values()) {
            if (torrent.isNeedStop()) {
                try {
                    rtorrentService.stop(torrent);
                    torrent.setNeedStop(false);
                    log.debug(torrent + " остановлен");
                } catch (RtorrentServiceException e) {
                    return;
                }
            }
        }
    }

    void updateAdd() {
        for (ActionTorrent torrent : torrents.values()) {
            if (torrent.isNeedAdd()) {
                try {
                    rtorrentService.add(torrent);
                    if (!rtorrentService.verify(torrent.getHash()))
                        throw new TorrentSetException("Не удалось добавить " + torrent);
                    log.debug(torrent + " добавлен");
                    //Меняем состояние
                    torrent.setNeedAdd(false);
                    torrent.setNeedStart(true);
                } catch (LoggableException e) {
                    return;
                }
            }
        }
    }

    void updateUpdate() {
        for (ActionTorrent torrent : torrents.values()) {
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
                    //отменям Апдейт
                    torrent.setNeedUpdate(false);
                    //обновляем торрент
                    torrents.update(torrent);
                    //запускаем его
                    torrent.setNeedStart(true);
                    //добавляем новый торрент
                    rtorrentService.add(torrent);
                    if (!rtorrentService.verify(torrent.getTorrentFileHash()))
                        throw new TorrentSetException("Не удалось добавить " + torrent);
                    torrent.setLastUpdated(new Date());
                    log.debug(torrent + " обновлен");
                } catch (LoggableException e) {
                    torrent.setNeedUpdate(true); //востанавливаем статус в случае ошибки
                    return;
                }
            }
        }
    }

    void updateDelete() {
        for (ActionTorrent torrent : torrents.values()) {
            if (torrent.isNeedDelete()) {
                try {
                    rtorrentService.remove(torrent);
                    if (rtorrentService.verify(torrent.getHash()))
                        throw new TorrentSetException("Не удалось удалить " + torrent);
                    torrents.remove(torrent.getHash());
                    log.debug(torrent + " удален");
                } catch (LoggableException e) {
                    return;
                }
            }
        }
    }
}