package rtorrent.client;

import rtorrent.action.ActionManager;
import rtorrent.utils.ContextUtils;

import java.io.Serializable;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 21:58:21
 */
public class ClientRequestManager {
    public Serializable manage(Serializable serializable) {
        if (serializable instanceof RequestAction) {
            RequestAction action = (RequestAction) serializable;
            ActionManager actionManager = (ActionManager) ContextUtils.lookup("raction");
            return (Serializable) actionManager.doAction(action.getName(), action.getParam());
        }
        return null;   
    }
}
