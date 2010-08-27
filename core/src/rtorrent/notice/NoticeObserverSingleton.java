package rtorrent.notice;

import org.apache.log4j.Logger;
import rtorrent.notice.client.ClientNoticeService;
import rtorrent.notice.email.EmailNoticeService;
import rtorrent.notice.rss.RssNoticeService;
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

    static {
        initialize();
    }

    public static void initialize() {
        clearService();
        registerService(RssNoticeService.class);
        registerService(EmailNoticeService.class);
        registerService(ClientNoticeService.class);
    }

    /**
     * @param service регистрируемый сервис
     */
    public static void registerService(Class<? extends NoticeService> service) {
        try {
            NoticeService noticeService = service.newInstance();
            services.add(noticeService);
        } catch (Exception e) {
            log.error(e);
        }
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
            log.debug(currentNotice + " добавлено");
            job.addNotice(currentNotice);
        }
    }

    public static void run() {
        for (NoticeJob job : jobs.values())
            ThreadQueueSingleton.add(job);
    }
}
