package rtorrent.client;

import java.io.Serializable;

/**
 * User: welvet
 * Date: 29.06.2010
 * Time: 22:41:17
 */
public class AddTorrentMessage implements Serializable {
    private byte[] bytes;
    private Boolean watched;
    private String url;
    private String tracker;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Boolean getWatched() {
        return watched;
    }

    public void setWatched(Boolean watched) {
        this.watched = watched;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }
}
