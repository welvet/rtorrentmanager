package rtorrent.test;

import junit.framework.TestCase;
import ntorrent.io.xmlrpc.XmlRpcConnection;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceImpl;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSet;
import rtorrent.torrent.set.TorrentSetSingleton;

import java.io.File;

/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 21:54:34
 */
public class TorrentSetTest extends TestCase {
    File datFile;
    File torrentFile;
    File torrent2File;
    RtorrentService rtorrentService;
    TorrentSet torrentSet;

    @Override
    protected void setUp() throws Exception {
        datFile = new File(RtorrentServiceTest.class.getResource("resource/").getPath() + "torrents.dat");
        torrentFile = new File(RtorrentServiceTest.class.getResource("resource/").getPath() + "test.torrent");
        torrent2File = new File(RtorrentServiceTest.class.getResource("resource/").getPath() + "test2.torrent");
        rtorrentService = new MockRtorrentService();
        rtorrentService = new RtorrentServiceImpl(new XmlRpcConnection("serv", 5000));
        TorrentSetSingleton.initialze(rtorrentService, datFile);
        torrentSet = TorrentSetSingleton.getInstance();
    }

    /**
     * Проверям основные операции
     *
     * @throws Exception
     */
    public void testSaveLoad() throws Exception {
        torrentSet.updateSet();
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        torrentSet.addOrUpdate(torrent);
        torrentSet.updateRtorrent();
        torrentSet.updateSet();
        assertNotNull(torrentSet.getByHash(torrent.getHash()));
        torrentSet.remove(torrent);
        torrentSet.updateRtorrent();
    }

    /**
     * Проверяем update
     *
     * @throws Exception
     */
    public void testUpdate() throws Exception {
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        torrentSet.addOrUpdate(torrent);
        torrentSet.updateRtorrent();
        torrentSet.updateSet();
        torrent.setFile(torrent2File);
        torrent.setNeedUpdate(true);
        torrentSet.addOrUpdate(torrent);
        torrentSet.updateRtorrent();
        torrentSet.updateSet();
        torrent = torrentSet.getByHash(torrent.getTorrentFileHash());
        torrentSet.remove(torrent);
        torrentSet.updateRtorrent();
        torrentSet.updateSet();
    }
}
