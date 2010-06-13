package rtorrent.tracker;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 14:27:33
 */
public class SimpleTrackerImpl implements Tracker {
    private String url;
    private Trackers trackers;

    public SimpleTrackerImpl(String url, Trackers trackers) {
        this.url = url;
        this.trackers = trackers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Tracker copy() {
        return new SimpleTrackerImpl(url, trackers);
    }

    public void setTracker(Trackers tracker) {
        this.trackers = tracker;
    }

    public Trackers getTracker() {
        return trackers;
    }
}
