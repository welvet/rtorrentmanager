package rtorrent.test;

import junit.framework.TestCase;
import ntorrent.io.xmlrpc.XmlRpcConnection;
import org.eclipse.ecf.protocol.bittorrent.TorrentFile;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceException;
import rtorrent.service.RtorrentServiceImpl;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.UtilException;

import java.io.File;
import java.util.Set;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:51:22
 */
public class RtorrentServiceTest extends TestCase {
    File file;
    XmlRpcConnection connection;
    RtorrentService rtorrentService;
    private Boolean aBoolean;
    private String rawData;

    @Override
    protected void setUp() throws Exception {
        file = new File(RtorrentServiceTest.class.getResource("resource/").getPath() + "test.torrent");
        connection = new XmlRpcConnection("serv", 5000);
        rtorrentService = new RtorrentServiceImpl(connection);
//        rtorrentService = new MockRtorrentService();
        if (!(rtorrentService.isAlive()))
            throw new Exception("Rtorrent not run");
    }

    /**
     * Логично было бы реализовать метод getTorrentByHash() но он будет противоречить логике приложения
     * поэтому такой страшный тест
     *
     * @throws Exception
     */
    public void testService() throws Exception, UtilException {
        ActionTorrent torrent = new ActionTorrent(file);
        TorrentFile torrentFile = new TorrentFile(file);
        //пробуем добавить торрент
        rtorrentService.add(torrent);
        torrent = getOnceTorrent();

        //запускаем торрент
        rtorrentService.start(torrent);
        torrent = getOnceTorrent();
        assertTrue(torrent.isStart());

        //отсанавливаем
        rtorrentService.stop(torrent);
        torrent = getOnceTorrent();
        assertFalse(torrent.isStart());

        //проверям его на стороне сервиса
        assertTrue(rtorrentService.verify(torrent.getHash()));
        torrent = getOnceTorrent();

        //проверяем хеши
        assertTrue(torrentFile.getHexHash().toUpperCase().equals(torrent.getHash()));

        //удаляем и проверяем
        rtorrentService.remove(torrent);
        assertFalse(rtorrentService.verify(torrent.getHash()));
    }

    private ActionTorrent getOnceTorrent() throws RtorrentServiceException {
        ActionTorrent torrent;
        Set<ActionTorrent> set = rtorrentService.getSet();
        torrent = set.iterator().next();
        return torrent;
    }
}
