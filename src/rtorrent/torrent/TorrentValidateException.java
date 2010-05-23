package rtorrent.torrent;

import rtorrent.utils.LoggableException;

/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 17:52:20
 */
public class TorrentValidateException extends LoggableException {
    public TorrentValidateException() {
    }

    public TorrentValidateException(String message) {
        super(message);
    }

    public TorrentValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TorrentValidateException(Throwable cause) {
        super(cause);
    }
}
