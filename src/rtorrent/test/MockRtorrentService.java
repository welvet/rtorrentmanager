package rtorrent.test;

import org.apache.log4j.Logger;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceException;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.Torrent;
import rtorrent.torrent.TorrentValidateException;
import rtorrent.utils.LoggerSingleton;

import java.util.*;

/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 18:37:28
 */
public class MockRtorrentService implements RtorrentService {
    private HashMap<String, ActionTorrent> torrentHashMap = new HashMap<String, ActionTorrent>();
    private Logger log = LoggerSingleton.getLogger();

    public void add(ActionTorrent torrent) throws RtorrentServiceException {
        ActionTorrent newTorrent = new ActionTorrent();
        newTorrent.updateAll(torrent);
        try {
            if (newTorrent.getTorrentFileHash() != null) {
                newTorrent.updateHashFromFile();
            }
        } catch (TorrentValidateException e) {
            throw new RtorrentServiceException(e);
        }
        torrentHashMap.put(torrent.getHash(), newTorrent);
        torrent.setNeedStart(true);
        log.info(torrent + " добавлен");
    }

    public void start(Torrent torrent) throws RtorrentServiceException {
        torrent.setStart(1L);
        log.info(torrent + " запущен");
    }

    public void stop(Torrent torrent) throws RtorrentServiceException {
        torrent.setStart(0L);
        log.info(torrent + " остановлен");
    }

    public void remove(Torrent torrent) throws RtorrentServiceException {
        torrentHashMap.remove(torrent.getHash());
        log.info(torrent + " удален");
    }

    public Boolean verify(String hash) throws RtorrentServiceException {
        ActionTorrent torrent = torrentHashMap.get(hash);
        if (torrent != null)
            return hash.equals(torrent.getHash());
        return false;
    }

    public Set<ActionTorrent> getSet() throws RtorrentServiceException {
        Set<ActionTorrent> hashSet = new HashSet<ActionTorrent>();
        Set set = torrentHashMap.entrySet();

        Iterator iter = set.iterator();

        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            ActionTorrent torrent = new ActionTorrent();
            torrent.updateInfo((ActionTorrent) entry.getValue());
            hashSet.add(torrent);
        }
        return hashSet;
    }

    public Boolean isAlive() {
        return true;
    }
}
