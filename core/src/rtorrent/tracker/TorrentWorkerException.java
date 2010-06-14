package rtorrent.tracker;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:18:47
 */
public class TorrentWorkerException extends Exception {
    public TorrentWorkerException() {
    }

    public TorrentWorkerException(String message) {
        super(message);
    }

    public TorrentWorkerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TorrentWorkerException(Throwable cause) {
        super(cause);
    }
}
