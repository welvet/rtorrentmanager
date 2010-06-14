package rtorrent.test;

import rtorrent.notice.NoticeObserverSingleton;
import rtorrent.notice.TorrentNotice;
import rtorrent.scheduler.DownAndCheckTask;
import rtorrent.torrent.ActionTorrent;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 0:31:05
 */
public class SchedulerTest extends RtorrentTestCase {
    public void testScheduler() throws Exception {

        NoticeObserverSingleton.notice(new ActionTorrent(), TorrentNotice.FINISH);

        DownAndCheckTask task = new DownAndCheckTask();
        task.run();
        Thread.sleep(10000);
    }
}
