package rtorrent.init;

import rtorrent.ConfigSingleton;
import rtorrent.ServerListener;
import rtorrent.client.RequestManager;
import rtorrent.tray.Icon;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:32:40
 */
public class Initialize {
    public static void main(String[] args) {
        try {
            Icon icon = new Icon();
            icon.createIcon();
            ServerListener.setIcon(icon);
            ServerListener listener = new ServerListener();
            listener.start();

            new Thread() {
                @Override
                public void run() {
                    if (ConfigSingleton.getNeedStop()) {
                        RequestManager manager = new RequestManager();
                        if (!manager.checkTorrent()) {
                            manager.switchTorrent();
                        }
                    }
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
