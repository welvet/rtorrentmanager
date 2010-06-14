package rtorrent.tracker.rutracker;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 19:46:52
 */
public class RuTrackerException extends Exception{
    public RuTrackerException() {
    }

    public RuTrackerException(String message) {
        super(message);
    }

    public RuTrackerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuTrackerException(Throwable cause) {
        super(cause);
    }
}
