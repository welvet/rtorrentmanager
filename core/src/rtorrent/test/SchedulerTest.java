package rtorrent.test;

import rtorrent.scheduler.SchedulerSingleton;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 0:31:05
 */
public class SchedulerTest extends RtorrentTestCase {
    public void testScheduler() throws Exception {
        SchedulerSingleton.startDefaultTask();
    }
}
