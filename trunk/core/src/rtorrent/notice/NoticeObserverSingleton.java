package rtorrent.notice;

import org.apache.log4j.Logger;
import rtorrent.thread.ThreadQueueSingleton;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.LoggerSingleton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 21:33:00
 */
public class NoticeObserverSingleton {
    public static Set<NoticeService> services = new HashSet<NoticeService>();
    public static HashMap<NoticeService, NoticeJob> jobs = new HashMap<NoticeService, NoticeJob>();
    public static Logger log = LoggerSingleton.getLogger();

    /**
     * @param service регистрируемый сервис
     */
    public static void registerService(NoticeService service) {
        services.add(service);
    }

    public static void clearService() {
        services.clear();
    }

    public static void notice(ActionTorrent torrent, TorrentNotice notice) {
        for (NoticeService service : services) {
            NoticeJob job = jobs.get(service);
            if (job == null) {
                job = new NoticeJob();
                job.setService(service);
                jobs.put(service, job);
            }
            Notice currentNotice = new Notice();
            currentNotice.setNotice(notice);
            currentNotice.setTorrent(torrent);
            //добавляем новое уведомление
            log.debug(currentNotice+" добавлено");
            job.addNotice(currentNotice);
        }
    }

    public static void run() {
        for (NoticeJob job :jobs.values())
            ThreadQueueSingleton.add(job);
    }
}
