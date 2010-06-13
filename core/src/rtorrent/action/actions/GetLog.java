package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.utils.LoggerSingleton;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 19:21:21
 */
public class GetLog implements Action{
    public void initialize() {

    }

    public Object run(Object param) {
        return LoggerSingleton.readUserLogFile();
    }
}
