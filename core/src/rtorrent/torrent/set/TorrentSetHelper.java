package rtorrent.torrent.set;

import org.apache.log4j.Logger;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceException;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.TorrentValidateException;
import rtorrent.utils.LoggableException;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

class TorrentSetHelper implements Runnable{
    private TorrentHashtable torrents;
    private RtorrentService rtorrentService;
    private Logger log;
    private TorrentSetImpl torrentSetImpl;

    public TorrentSetHelper(TorrentSetImpl torrentSetImpl) {
        torrents = torrentSetImpl.getTorrents();
        rtorrentService = torrentSetImpl.getRtorrentService();
        log = torrentSetImpl.getLog();
        this.torrentSetImpl = torrentSetImpl;
    }

    public void run() {
        execute(new SetUpdate());
        execute(new Add());
        execute(new Start());
        execute(new Stop());
        execute(new Update());
        execute(new Delete());
    }

    private void execute(Runnable runnable) {
        runnable.run();
    }

    private class SetUpdate implements Runnable {
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
                ActionTorrent localTorrent = torrentSetImpl.getTorrentFromMap(remoteTorrent);
                localTorrent.updateInfo(remoteTorrent);
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

    private class Start implements Runnable {
        /**
         * Этот и следующий методы, не имеют проверок.
         */
        public void run() {
            for (ActionTorrent torrent : torrents.values()) {
                if (torrent.isNeedStart()) {
                    try {
                        rtorrentService.start(torrent);
                        torrent.setNeedStart(false);
                        log.info(torrent + " запущен");
                    } catch (RtorrentServiceException e) {
                        return;    //все кетчи перехватывают исключения, возникшие из-за неправильной связи с сервером.
                        // Логгирование ошибки происходит в конструкторе исключения
                    }
                }
            }
        }
    }

    private class Stop implements Runnable {

        public void run() {
            for (ActionTorrent torrent : torrents.values()) {
                if (torrent.isNeedStop()) {
                    try {
                        rtorrentService.stop(torrent);
                        torrent.setNeedStop(false);
                        log.info(torrent + " остановлен");
                    } catch (RtorrentServiceException e) {
                        return;
                    }
                }
            }
        }
    }

    private class Add implements Runnable {
        public void run() {
            for (ActionTorrent torrent : torrents.values()) {
                if (torrent.isNeedAdd()) {
                    try {
                        rtorrentService.add(torrent);
                        if (!rtorrentService.verify(torrent.getHash()))
                            throw new TorrentSetException("Не удалось добавить " + torrent);
                        log.info(torrent + " добавлен");
                        //Меняем состояние
                        torrent.setNeedAdd(false);
                        torrent.setNeedStart(true);
                    } catch (LoggableException e) {
                        return;
                    }
                }
            }
        }
    }

    private class Update implements Runnable {
        public void run() {
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
                        log.info(torrent + " обновлен");
                    } catch (LoggableException e) {
                        torrent.setNeedUpdate(true); //востанавливаем статус в случае ошибки
                        return;
                    } catch (TorrentValidateException e) {
                        log.error(e);
                    }
                }
            }
        }
    }

    private class Delete implements Runnable {
        public void run() {
            for (ActionTorrent torrent : torrents.values()) {
                if (torrent.isNeedDelete()) {
                    try {
                        rtorrentService.remove(torrent);
                        if (rtorrentService.verify(torrent.getHash()))
                            throw new TorrentSetException("Не удалось удалить " + torrent);
                        torrents.remove(torrent.getHash());
                        log.info(torrent + " удален");
                    } catch (LoggableException e) {
                        return;
                    }
                }
            }
        }
    }
}