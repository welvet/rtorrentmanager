package rtorrent.test;

import bittorrent.TorrentFile;
import rtorrent.service.RtorrentServiceException;
import rtorrent.torrent.ActionTorrent;

import java.util.Set;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:51:22
 */
public class RtorrentServiceTest extends RtorrentTestCase {
    private Boolean aBoolean;
    private String rawData;

    /**
     * Логично было бы реализовать метод getTorrentByHash() но он будет противоречить логике приложения
     * поэтому такой страшный тест
     *
     * @throws Exception
     */
    public void testService() throws Exception {
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        TorrentFile torrentFile = new TorrentFile(this.torrentFile);
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
