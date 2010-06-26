package rtorrent.utils;

import rtorrent.tracker.Trackers;

import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 15:38:39
 */
public class TrackerUtils {

    public static String trackers2string(Trackers trackers) {
        return trackers.toString();
    }

    public static Trackers[] trackers() {
        return Trackers.values();
    }

    public static List<String> strings() {
        List<String> strings = new ArrayList<String>();
        for (Trackers trak : Trackers.values())
            strings.add(trak.toString());
        return strings;
    }

    public static Trackers string2trackers(String tracker) {
        for (Trackers trak : Trackers.values())
            if (trak.compare(tracker))
                return trak;
        throw new RuntimeException("Трекер не найден");
    }
}
