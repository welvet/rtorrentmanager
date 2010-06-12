package rtorrent.tracker;

import rtorrent.thread.ThreadQueueSingleton;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:59:23
 */
public class TorrentWorkersObserverSingleton {
    private static TorrentWorkersObserver ourInstance = new TorrentWorkersObserver();

    public static TorrentWorkersObserver getInstance() {
        return ourInstance;
    }

    public static void clearWorkers() {
        ourInstance.clearWorkers();
    }

    public static void registerWorker(TrackerWorker worker) {
        ourInstance.registerWorker(worker);
    }

    public static void run() {
        ThreadQueueSingleton.add(ourInstance);
    }

    private TorrentWorkersObserverSingleton() {
    }
}
