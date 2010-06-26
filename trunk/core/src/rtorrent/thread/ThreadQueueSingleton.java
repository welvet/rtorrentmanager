package rtorrent.thread;

import rtorrent.notice.NoticeJob;
import rtorrent.scheduler.AfterExecuteCallback;
import rtorrent.tracker.TorrentJob;
import rtorrent.tracker.TorrentWorkersObserver;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * User: welvet
 * Date: 07.06.2010
 * Time: 22:09:32
 */
public class ThreadQueueSingleton {
    private static final int CORE_POOL_SIZE = 2;
    private static final int MAXIMUM_POOL_SIZE = 4;
    private static final int KEEP_ALIVE_TIME = 30;
    private static ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(20);
    private static RtorrentThreadPoolExecutor threadPool = new RtorrentThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, queue);

    public static void add(Runnable runnable) {
            if (queue.contains(runnable))
                    return;
        if ((runnable instanceof TorrentJob) || (runnable instanceof NoticeJob) || runnable instanceof TorrentWorkersObserver)
            AfterExecuteCallback.jobStart();
        threadPool.execute(runnable);
    }

    public static int size() {
        return queue.size();
    }
}
