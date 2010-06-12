package rtorrent.notice;

import rtorrent.torrent.ActionTorrent;

import java.util.HashSet;
import java.util.Set;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 21:33:00
 */
public class NoticeObserverSingleton {
    public static Set<NoticeService> services = new HashSet<NoticeService>();

    /**
     * @param service регистрируемый сервис
     */
    public static void registerService(NoticeService service) {
        services.add(service);
    }

    public static void notice(ActionTorrent torrent, TorrentNotice notice) {
        for (NoticeService service : services) {
            service.notice(torrent, notice);
        }
    }
}
