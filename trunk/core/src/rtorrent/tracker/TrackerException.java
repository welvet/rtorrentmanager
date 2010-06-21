package rtorrent.tracker;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 19:46:52
 */
public class TrackerException extends Exception{
    public TrackerException() {
    }

    public TrackerException(String message) {
        super(message);
    }

    public TrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrackerException(Throwable cause) {
        super(cause);
    }
}
