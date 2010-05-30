package rtorrent.test;

import junit.framework.TestCase;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceImpl;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSet;
import rtorrent.torrent.set.TorrentSetSingleton;

import java.io.File;
import java.util.Date;

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
    private String hash;
    private static final int WAIT_TIME = 3000;

    @Override
    protected void setUp() throws Exception {
        datFile = new File(RtorrentServiceTest.class.getResource("resource/").getPath() + "torrents.dat");
        torrentFile = new File(RtorrentServiceTest.class.getResource("resource/").getPath() + "test.torrent");
        torrent2File = new File(RtorrentServiceTest.class.getResource("resource/").getPath() + "test2.torrent");
        rtorrentService = new MockRtorrentService();
        TorrentSetSingleton.initialze(rtorrentService, datFile);
        torrentSet = TorrentSetSingleton.getInstance();
    }

    /**
     * Проверям основные операции
     *
     * @throws Exception
     */
    public void testSaveLoad() throws Exception {
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        try {
            torrentSet.updateSet();

            torrentSet.addOrUpdate(torrent);

            torrentSet.updateRtorrent();
            torrentSet.updateSet();

            Thread.sleep(WAIT_TIME);

            assertNotNull(torrentSet.getByHash(torrent.getHash()));
        } finally {
            torrentSet.remove(torrent);
            torrentSet.updateRtorrent();
        }
    }

    /**
     * Проверяем update
     *
     * @throws Exception
     */
    public void testUpdate() throws Exception {
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        try {

            torrentSet.addOrUpdate(torrent);
            torrentSet.updateRtorrent();
            torrentSet.updateSet();

            Thread.sleep(WAIT_TIME/2);

            torrent = torrentSet.getByHash(torrent.getHash());

            torrent.setFile(torrent2File);
            torrent.setNeedUpdate(true);
            torrent.setLastUpdated(new Date(System.currentTimeMillis()));
            torrentSet.addOrUpdate(torrent);
            torrentSet.updateRtorrent();
            torrentSet.updateSet();

            Thread.sleep(WAIT_TIME/2);

            torrent = torrentSet.getByHash(torrent.getTorrentFileHash());
        } finally {
            torrentSet.remove(torrent);
            torrentSet.updateRtorrent();
            torrentSet.updateSet();
        }
    }

    public void testStartStop() throws Exception {
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        try {
            hash = torrent.getHash();
            torrentSet.addOrUpdate(torrent);
            torrentSet.updateRtorrent();

            //ждем пока рторрент нам ответит
            Thread.sleep(WAIT_TIME);
            torrentSet.updateRtorrent();
            Thread.sleep(WAIT_TIME / 2);
            torrentSet.updateSet();
            Thread.sleep(WAIT_TIME / 2);

            torrent = torrentSet.getByHash(hash);
            assertTrue(torrent.isStart());

            torrent.setNeedStop(true);
            torrentSet.addOrUpdate(torrent);
            torrentSet.updateRtorrent();

            Thread.sleep(WAIT_TIME);
            torrentSet.updateSet();
            Thread.sleep(WAIT_TIME / 2);

            torrent = torrentSet.getByHash(hash);
            assertFalse(torrent.isStart());
            torrent.setNeedStart(true);

            torrentSet.addOrUpdate(torrent);
            torrentSet.updateRtorrent();

            Thread.sleep(WAIT_TIME);
            torrentSet.updateSet();

            Thread.sleep(WAIT_TIME/2);
            torrent = torrentSet.getByHash(hash);
            assertTrue(torrent.isStart());
        } finally {
            torrentSet.remove(torrent);
            torrentSet.updateRtorrent();
        }
    }
}
