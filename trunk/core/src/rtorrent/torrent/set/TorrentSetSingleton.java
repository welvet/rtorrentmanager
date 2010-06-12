/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 19:01:13
 */
package rtorrent.torrent.set;

import rtorrent.service.RtorrentService;
import rtorrent.utils.LoggerSingleton;

import java.io.File;

public class TorrentSetSingleton {
    private static TorrentSet ourInstance;

    private TorrentSetSingleton() {
    }

    public static void initialze(RtorrentService rtorrentService, File file) {
        ourInstance = new TorrentSetImpl(rtorrentService, file);
        LoggerSingleton.getLogger().debug("TorrentSet инициализирован");
    }

    public static void changeService(RtorrentService service) {
        ourInstance.setService(service);
    }

    public static TorrentSet getInstance() {
        if (ourInstance == null)
            throw new RuntimeException("TorrentSet не инициализирован");
        return ourInstance;
    }

    public static void run() {
        ourInstance.update();
    }
}
