package rtorrent.torrent.set;

import rtorrent.utils.LoggableException;

/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 21:40:28
 */
public class TorrentSetException extends LoggableException {
    public TorrentSetException() {
    }

    public TorrentSetException(String message) {
        super(message);
    }

    public TorrentSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public TorrentSetException(Throwable cause) {
        super(cause);
    }
}
