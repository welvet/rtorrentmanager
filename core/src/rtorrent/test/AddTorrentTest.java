package rtorrent.test;

import rtorrent.action.actions.AddTorrent;
import rtorrent.client.AddTorrentMessage;
import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.tracker.SimpleTrackerImpl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * User: welvet
 * Date: 29.06.2010
 * Time: 23:01:12
 */
public class AddTorrentTest extends RtorrentTestCase {
    public void testAction() throws Exception {
        AddTorrent addTorrent = new AddTorrent();
        AddTorrentMessage message = new AddTorrentMessage();
        message.setTracker("none");
        message.setUrl("123");
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(torrentFile));
        byte[] bytes = new byte[(int) torrentFile.length()];
        stream.read(bytes);
        message.setBytes(bytes);
        addTorrent.run(message);
        ActionTorrent torrent = TorrentSetSingleton.getInstance().getByHash(new ActionTorrent(torrentFile).getTorrentFileHash());
        SimpleTrackerImpl tracker = (SimpleTrackerImpl) torrent.getTracker();
        assertEquals(tracker.getUrl(), "123");

    }
}
