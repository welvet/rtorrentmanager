package rtorrent.init;

import rtorrent.ConfigSingleton;
import rtorrent.ServerListener;
import rtorrent.addtorrent.AddTorrent;
import rtorrent.client.RequestManager;
import rtorrent.tray.Icon;

import java.io.File;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:32:40
 */
public class Initialize {
    public static void main(String[] args) {
        try {
            //инициализируем добавление торрента
            if (args != null && args.length > 0) {
                AddTorrent addTorrent = new AddTorrent(new File(args[0]));
                return;
            }

            //инициализируем стандартное поведение
            Icon icon = new Icon();

            Thread thread = new Thread(icon);
            thread.start();

            icon.waitIcon();

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
