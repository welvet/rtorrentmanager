package rtorrent.scheduler;

import rtorrent.notice.NoticeObserverSingleton;

import java.util.TimerTask;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 0:35:44
 */
public class UpdateNoticeTask extends TimerTask {
    @Override
    public void run() {
        //если рторрент не запущен, то обрабатываем нотисы
        if (!CheckStrategy.getRun())
            NoticeObserverSingleton.run();
    }
}
