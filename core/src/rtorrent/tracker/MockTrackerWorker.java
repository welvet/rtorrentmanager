package rtorrent.tracker;

import rtorrent.torrent.TorrentFacade;
import rtorrent.torrent.TorrentValidateException;

import java.io.File;

/**
 * User: welvet
 * Date: 21.06.2010
 * Time: 22:05:14
 */
public class MockTrackerWorker implements TrackerWorker {
    public static File torrent2File;

    public void initialize(File workDir) throws TorrentWorkerException {
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

    public TorrentFacade checkOnceTorrent(TorrentFacade torrent) throws TrackerException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Trackers whoIs() {
        return Trackers.MOCK;
    }
}
