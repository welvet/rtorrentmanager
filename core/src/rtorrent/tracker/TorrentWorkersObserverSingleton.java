package rtorrent.tracker;

import rtorrent.thread.ThreadQueueSingleton;
import rtorrent.tracker.lostfilm.LostFilmWorker;
import rtorrent.tracker.rutracker.RuTrackerWorker;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:59:23
 */
public class TorrentWorkersObserverSingleton {
    private static TorrentWorkersObserver ourInstance = new TorrentWorkersObserver();

    static {
        initialize();
    }

    public static TorrentWorkersObserver getInstance() {
        return ourInstance;
    }

    public static void initialize() {
        clearWorkers();
        registerWorker(RuTrackerWorker.class);
        registerWorker(LostFilmWorker.class);
    }

    public static void clearWorkers() {
        ourInstance.clearWorkers();
    }

    public static void registerWorker(Class <? extends TrackerWorker> worker) {
        ourInstance.registerWorker(worker);
    }

    public static void run() {
        ThreadQueueSingleton.add(ourInstance);
    }

    private TorrentWorkersObserverSingleton() {
    }
}
