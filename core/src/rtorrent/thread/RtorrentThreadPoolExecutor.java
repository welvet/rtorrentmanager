package rtorrent.thread;

import org.apache.log4j.Logger;
import rtorrent.notice.NoticeJob;
import rtorrent.scheduler.AfterExecuteCallback;
import rtorrent.tracker.TorrentJob;
import rtorrent.tracker.TorrentWorkersObserver;
import rtorrent.utils.LoggerSingleton;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: welvet
 * Date: 07.06.2010
 * Time: 22:08:32
 */
public class RtorrentThreadPoolExecutor extends ThreadPoolExecutor {
    Logger log = LoggerSingleton.getLogger();

    public RtorrentThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        log.debug("RtorrentThreadPoolExecutor инициализирован");
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        log.debug("Нить: " + t.getName() + " обрабатывает: " + r.getClass().getName());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        String msg = (t == null) ? "" : " Ошибка: " + t.getMessage();
        log.debug(r.getClass().getName() + " обработано" + msg);
        if ((r instanceof TorrentJob) || (r instanceof NoticeJob) || (r instanceof TorrentWorkersObserver)) {
            AfterExecuteCallback.jobDone();
            AfterExecuteCallback.afterExecute();
        }
    }
}
