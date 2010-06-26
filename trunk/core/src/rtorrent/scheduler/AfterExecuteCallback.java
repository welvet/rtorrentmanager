package rtorrent.scheduler;

import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.utils.LoggerSingleton;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 20:40:14
 */
public class AfterExecuteCallback {
    private static volatile Boolean use = false;
    private static volatile Integer i = 0;

    static void setUse(Boolean use) {
        AfterExecuteCallback.use = use;
    }

    public static synchronized void afterExecute() {
        if (use) {
            if (!(i == 0))
                return;
            //отключаем себя
            use = false;
            //запускаем рторрент
            TorrentSetSingleton.getInstance().launch();
            //востанавливаем проверку
            CheckStrategy.setNeedCheck(true);
            LoggerSingleton.getLogger().debug("AfterExecuteCallback выполнен");
        }

    }

    public static void newIterator() {
        i = 0;
    }

    public static synchronized void jobStart() {
        i++;
    }

    public static synchronized void jobDone() {
        i--;
    }
}
