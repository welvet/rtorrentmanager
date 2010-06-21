package rtorrent.test;

import rtorrent.torrent.ActionTorrent;
import rtorrent.tracker.MockTrackerWorker;
import rtorrent.tracker.TorrentWorkersObserver;
import rtorrent.tracker.TorrentWorkersObserverSingleton;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:44:03
 */
public class TrackerTest extends RtorrentTestCase {


    public void testTorrentWorker() throws Exception {
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        
        ActionTorrent torrent2 = new ActionTorrent(torrent2File);
        try {
            //создаем торрент

            //устанавливаем обработчик трекера
            torrent.setWatching(true);
            torrent.setTracker(new MockTracker());
            //обновляем торрент
            torrentSet.addOrUpdate(torrent);
            torrentSet.update();
            Thread.sleep(WAIT_TIME);

            MockTrackerWorker.torrent2File = torrent2File;
            //устанавливаем обработчик
            TorrentWorkersObserver observer = TorrentWorkersObserverSingleton.getInstance();
            observer.clearWorkers();
            observer.registerWorker(MockTrackerWorker.class);
            //запускаем его
            TorrentWorkersObserverSingleton.run();
            Thread.sleep(WAIT_TIME);

            torrentSet.update();
            Thread.sleep(WAIT_TIME);


            torrent = torrentSet.getByHash(torrent2.getHash());

            assertNotNull(torrent);
        } finally {
            torrent2.setNeedDelete(true);
            torrentSet.addOrUpdate(torrent2);
            torrentSet.updateRtorrent();
        }
    }
}
