package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.tracker.Trackers;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 22:14:24
 */
public class TorrentArray implements Action{
    public void initialize() {

    }

    public Object run(Object param) {
        String[] strings = new String[Trackers.values().length];
        Integer i = 0;
        for (Trackers trackers : Trackers.values()) {
            strings[i++] = trackers.toString();
        }
        return strings;
    }
}
