package rtorrent.test;

import rtorrent.service.UtorrentService;
import rtorrent.torrent.ActionTorrent;

/**
 * User: welvet
 * Date: 03.09.2010
 * Time: 23:00:26
 */
public class UtorrentServiceTest extends RtorrentTestCase
{
    private static final String host = "127.0.0.1";
    private static final String PORT = "8080";
    private static final String login = "admin";
    private static final String pass = "";
    private static final String gui = "gui";


    public void testConnectionUtorrent() throws Exception
    {
        UtorrentService service = new UtorrentService(host, PORT, login, pass, gui);
        assertTrue(service.isAlive());
    }

    public void testGetSet() throws Exception
    {
        UtorrentService service = new UtorrentService(host, PORT, login, pass, gui);
        service.getSet();
        service.getSet();
    }

    public void testAdd() throws Exception
    {
        UtorrentService service = new UtorrentService(host, PORT, login, pass, gui);
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        service.add(torrent);
        String torrentFileHash = torrent.getTorrentFileHash();
        boolean result = service.verify(torrentFileHash);
        assertTrue(result);

        service.stop(torrent);
        service.start(torrent);
        
        torrent.updateHashFromFile();
        service.remove(torrent);

        boolean falseResult = service.verify(torrentFileHash);
        assertFalse(falseResult);
    }

}
