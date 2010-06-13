package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.utils.LoggerSingleton;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 19:22:13
 */
public class ClearLog implements Action{
    public void initialize() {

    }

    public Object run(Object param) {
        LoggerSingleton.clearUserLog();
        return null;
    }
}
