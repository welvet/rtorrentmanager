package rtorrent.torrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 22:02:14
 */
public class TorrentHashtable extends ConcurrentHashMap<String, ActionTorrent> {
    /**
     * Обновляем торрент в базе по новому хешу из файла
     * @param torrent
     * @throws TorrentValidateException
     */
    public void update(ActionTorrent torrent) throws TorrentValidateException {
        this.remove(torrent.getHash());
        torrent.updateHashFromFile();
        this.put(torrent.getHash(), torrent);
    }

}
