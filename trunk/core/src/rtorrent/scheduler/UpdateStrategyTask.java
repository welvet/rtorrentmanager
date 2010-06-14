package rtorrent.scheduler;

import java.util.TimerTask;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 20:10:43
 */
public class UpdateStrategyTask extends TimerTask {
    @Override
    public void run() {
        CheckStrategy.check();
    }
}
