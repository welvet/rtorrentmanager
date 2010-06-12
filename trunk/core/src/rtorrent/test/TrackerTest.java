package rtorrent.test;

import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.TorrentFacade;
import rtorrent.torrent.TorrentValidateException;
import rtorrent.tracker.*;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:44:03
 */
public class TrackerTest extends RtorrentTestCase {
    private class MockTrackerWorker implements TrackerWorker {

        public void initialize() throws TorrentWorkerException {
            System.out.println("MockTrackerWorker инициализирован");
        }

        public TorrentFacade work(TorrentFacade torrent) {
            try {
                //обновляем торрент
                torrent.setFile(torrent2File);
                torrent.setNeedUpdate(true);
                return torrent;
            } catch (TorrentValidateException e) {
                throw new RuntimeException("Не найден тестовый файл");
            }
        }

        public Trackers whoIs() {
            return Trackers.MOCK;
        }
    }

    public void testTorrentWorker() throws Exception {
        ActionTorrent torrent = new ActionTorrent(torrentFile);
        
        ActionTorrent torrent2 = new ActionTorrent(torrent2File);
        try {//создаем воркер
            MockTrackerWorker worker = new MockTrackerWorker();
            //создаем торрент

            //устанавливаем обработчик трекера
            torrent.setWatching(true);
            torrent.setTracker(new MockTracker());
            //обновляем торрент
            torrentSet.addOrUpdate(torrent);
            torrentSet.update();
            Thread.sleep(WAIT_TIME);

            //устанавливаем обработчик
            TorrentWorkersObserver observer = TorrentWorkersObserverSingleton.getInstance();
            observer.clearWorkers();
            observer.registerWorker(worker);
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
