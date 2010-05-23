/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 19:01:13
 */
package rtorrent.torrent.set;

import rtorrent.service.RtorrentService;

import java.io.File;

public class TorrentSetSingleton {
    private static TorrentSet ourInstance;

    public static void initialze(RtorrentService rtorrentService, File file) {
        ourInstance = new TorrentSetImpl(rtorrentService, file);
    }

    public static TorrentSet getInstance() throws TorrentSetException {
        if (ourInstance == null)
            throw new TorrentSetException("TorrentSet не инициализирован");
        return ourInstance;
    }
}
