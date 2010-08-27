package rtorrent.test;

import rtorrent.client.RequestManager;
import rtorrent.notice.client.ClientNotice;

import java.util.List;


/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 22:16:32
 */
public class ConnectorTest {
    public static void main(String[] args) {
        RequestManager manager = new RequestManager();
        List<ClientNotice> strings = manager.getNotices();
        for (ClientNotice s : strings) {
            System.out.println(s.getNoticeType());
        }
        System.out.println(manager.checkTorrent());
        manager.switchTorrent();
        manager.switchTorrent();        
    }
}
