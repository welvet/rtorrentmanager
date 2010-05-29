package rtorrent.service;

import rtorrent.utils.LoggableException;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:19:12
 */
public class RtorrentServiceException extends LoggableException {
    public RtorrentServiceException() {
    }

    public RtorrentServiceException(String message) {
        super(message);
    }

    public RtorrentServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RtorrentServiceException(Throwable cause) {
        super(cause);
    }
}
