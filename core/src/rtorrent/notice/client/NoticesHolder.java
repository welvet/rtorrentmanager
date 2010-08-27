package rtorrent.notice.client;

import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 27.08.2010
 * Time: 19:59:01
 */
public class NoticesHolder
{
    private static List<ClientNotice> notices = new ArrayList<ClientNotice>();

    public static synchronized List<ClientNotice> getList()
    {
        List<ClientNotice> clientNotices = new ArrayList<ClientNotice>(notices);
        notices.clear();
        return clientNotices;
    }

    public static synchronized void addNotice(ClientNotice notice)
    {
        notices.add(notice);
    }
}
