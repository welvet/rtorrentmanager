package rtorrent.action;

import org.apache.log4j.Logger;
import rtorrent.action.actions.*;
import rtorrent.utils.BindContext;
import rtorrent.utils.InContext;
import rtorrent.utils.LoggerSingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 19:13:06
 */
public class ActionManagerImpl implements ActionManager, InContext {
    private static Map<String, Class<? extends Action>> actionMap = new HashMap<String, Class<? extends Action>>();
    private static Logger log = LoggerSingleton.getLogger();

    static {
        actionMap.put("clearLog", ClearLog.class);
        actionMap.put("getLog", GetLog.class);
        actionMap.put("shutdownApp", ShutdownApp.class);
        actionMap.put("checkAll", CheckAll.class);
        actionMap.put("checkRtorrent", CheckRtorrent.class);
        actionMap.put("shitchTorrent", SwitchTorrent.class);
        actionMap.put("torrentArray", TorrentArray.class);
        actionMap.put("getTorrentUrl", GetTorrentUrl.class);
        actionMap.put("addTorrent", AddTorrent.class);
        actionMap.put("updateTorrent", UpdateTorrent.class);
        actionMap.put("receiveNotices", ReceiveNotices.class);
    }

    public ActionManagerImpl() {
        bindContext();
        log.debug("ActionManagerImpl инициализирован");
    }

    public Object doAction(String s, Object param) {
        try {
            Action action;
            action = actionMap.get(s).newInstance();
            log.debug("Action " + s + " запущен");
            action.initialize();
            return action.run(param);
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public void bindContext() {
        BindContext.bind("raction", this);
    }
}
