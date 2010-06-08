package rtorrent.control;

import org.apache.log4j.Logger;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.TorrentInfo;
import rtorrent.torrent.TorrentValidateException;
import rtorrent.torrent.set.TorrentSet;
import rtorrent.torrent.set.TorrentSetException;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.utils.LoggerSingleton;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Делегируем вызовы синглтону
 * <p/>
 * User: welvet
 * Date: 30.05.2010
 * Time: 22:55:31
 */
public class RtorrentControlerImpl implements RtorrentControler {
    private TorrentSet torrentSet;
    private Logger log = LoggerSingleton.getLogger();;

    public RtorrentControlerImpl() throws TorrentSetException {
        torrentSet = TorrentSetSingleton.getInstance();
    }

    /**
     * Добавляем себя в контекст
     *
     * @throws TorrentSetException
     */
    public void bindContext() throws TorrentSetException {
        try {
            //todo заменить на утилс
            InitialContext context = new InitialContext();
            context.bind("rcontroler", this);
            log.debug("RtorrentControler загружен");
        } catch (NamingException e) {
            throw new TorrentSetException(e);
        }
    }

    public List<TorrentInfo> getList() {
        List<TorrentInfo> torrents = new ArrayList<TorrentInfo>();
        Set<ActionTorrent> set = torrentSet.getSet();
        for (ActionTorrent torrent : set) {
            TorrentInfoImpl torrentInfo = new TorrentInfoImpl();
            torrentInfo.copyInfo(torrent);
            torrents.add(torrentInfo);
        }
        return torrents;
    }

    public void addTorrent(File torrentFile) {
        ActionTorrent torrent = null;
        try {
            //атрибут needAdd выставляется в конструкторе
            torrent = new ActionTorrent(torrentFile);
            torrentSet.addOrUpdate(torrent);
        } catch (TorrentValidateException e) {
            log.error(e);
        }
    }

    public void startTorrent(String hash) {
        ActionTorrent torrent = torrentSet.getByHash(hash);
        torrent.setNeedStart(true);
        torrentSet.addOrUpdate(torrent);
    }

    public void stopTorrent(String hash) {
        ActionTorrent torrent = torrentSet.getByHash(hash);
        torrent.setNeedStop(true);
        torrentSet.addOrUpdate(torrent);
    }

    public void removeTorrent(String hash) {
        ActionTorrent torrent = torrentSet.getByHash(hash);
        torrent.setNeedDelete(true);
        torrentSet.addOrUpdate(torrent);
    }

    public void configureTorrent(String hash) {
        throw new UnsupportedOperationException();
    }
}
