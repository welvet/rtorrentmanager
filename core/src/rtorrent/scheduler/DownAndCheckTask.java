package rtorrent.scheduler;

import org.apache.log4j.Logger;
import rtorrent.notice.NoticeObserverSingleton;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.tracker.TorrentWorkersObserverSingleton;
import rtorrent.utils.LoggerSingleton;

import java.util.TimerTask;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 20:19:05
 */
public class DownAndCheckTask extends TimerTask {
    private static final Logger log = LoggerSingleton.getLogger();

    @Override
    public void run() {
        //если рторрент запущен, то работаем согласно этой статегии
        if (CheckStrategy.getRun()) {
            try {
                //не проверяем, запущен ли рторрент во время работы
                CheckStrategy.setNeedCheck(false);
                //останавливаем рторрент
                TorrentSetSingleton.getInstance().shutdown();
                //ждем пока рторрент остановиться
                Thread.sleep(10000);

                AfterExecuteCallback.newIterator();
                AfterExecuteCallback.setUse(true);
                NoticeObserverSingleton.run();
                TorrentWorkersObserverSingleton.run();
                //ждем другие потоки
                log.debug("DownAndCheck стратегия запущена");

            } catch (Exception e) {
                log.error(e);
                CheckStrategy.setNeedCheck(true);
                CheckStrategy.check();
                if (!CheckStrategy.getRun()) {
                    //востанавливаем рторрент в случае ошибки
                    TorrentSetSingleton.getInstance().launch();
                }
            }
        }
    }
}
