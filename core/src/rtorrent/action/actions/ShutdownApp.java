package rtorrent.action.actions;

import rtorrent.action.Action;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 20:51:18
 */
public class ShutdownApp implements Action {
    public void initialize() {

    }

    public Object run(Object param) {
        System.exit(0);        
        return null;
    }
}
