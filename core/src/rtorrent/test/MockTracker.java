package rtorrent.test;

import rtorrent.tracker.Tracker;
import rtorrent.tracker.Trackers;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 23:56:44
 */
public class MockTracker implements Tracker {

    public Tracker copy() {
        //возвращаем объект
        return new MockTracker();
    }

    public Trackers getTracker() {
        return Trackers.MOCK;
    }
}
