package rtorrent.control;

import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.TorrentValidateException;
import rtorrent.torrent.set.TorrentSet;
import rtorrent.torrent.set.TorrentSetException;
import rtorrent.torrent.set.TorrentSetSingleton;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.File;
import java.util.Set;

/**
 * ���������� ������ ���������
 * <p/>
 * User: welvet
 * Date: 30.05.2010
 * Time: 22:55:31
 */
public class RtorrentControlerImpl implements RtorrentControler {
    private TorrentSet torrentSet;

    public RtorrentControlerImpl() throws TorrentSetException {
        torrentSet = TorrentSetSingleton.getInstance();
    }

    /**
     * ��������� ���� � ��������
     *
     * @throws TorrentSetException
     */
    public void bindContext() throws TorrentSetException {
        try {
            InitialContext context = new InitialContext();
            context.bind("rcontroler", this);
        } catch (NamingException e) {
            throw new TorrentSetException(e);
        }
    }

    public Set<ActionTorrent> getList() {
        return torrentSet.getSet();
    }

    public void addTorrent(File torrentFile) throws TorrentValidateException {
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        torrent.setNeedAdd(true);
        torrentSet.addOrUpdate(torrent);
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
