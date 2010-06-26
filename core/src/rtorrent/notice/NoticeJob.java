package rtorrent.notice;

import org.apache.log4j.Logger;
import rtorrent.utils.LoggerSingleton;

import java.util.HashSet;
import java.util.Set;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 23:21:11
 */
public class NoticeJob implements Runnable {
    private NoticeService service;
    private Set<Notice> notices = new HashSet<Notice>();
    private Logger log = LoggerSingleton.getLogger();

    public NoticeService getService() {
        return service;
    }

    public void setService(NoticeService service) {
        this.service = service;
    }

    public void addNotice(Notice notice) {
        notices.add(notice);
    }

    public void clearNotices() {
        notices.clear();
    }

    public void run() {
        try {
            service.initialize();
            for (Notice notice : new HashSet<Notice>(notices)) {
                service.notice(notice.getTorrent(), notice.getNotice());
                log.debug(notice + " выполнено");
                notices.remove(notice);
            }
        } catch (Exception e) {
            log.info(e);
        } 
    }
}
