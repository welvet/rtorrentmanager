package rtorrent;

import rtorrent.client.RequestManager;
import rtorrent.tray.Icon;

/**
 * User: welvet
 * Date: 28.06.2010
 * Time: 21:16:14
 */
public class ServerListener extends Thread {
    private static Icon icon;
    private static RequestManager requestManager = new RequestManager();

    public static void setIcon(Icon icon) {
        ServerListener.icon = icon;
    }

    @Override
    public void run() {
        while (true) {
            try {
                icon.changIcon(!requestManager.checkTorrent(), false);
            } catch (Exception e) {
                icon.changIcon(null, true);
            }
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e1) {
                e1.printStackTrace();  // TODO change me
            }

        }
    }
}
