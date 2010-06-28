package rtorrent.test;

import rtorrent.client.RequestManager;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 22:16:32
 */
public class ConnectorTest {
    public static void main(String[] args) {
        RequestManager manager = new RequestManager();
        String[] strings = manager.getTorrents();
        for (String s : strings) {
            System.out.println(s);
        }
        System.out.println(manager.checkTorrent());
        manager.switchTorrent();
        manager.switchTorrent();        
    }
}
