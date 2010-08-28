package rtorrent.download;

import rtorrent.notice.client.ClientNotice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: welvet
 * Date: 28.08.2010
 * Time: 0:13:39
 */
public class MockList
{
    private static List<ClientNotice> noticeList = new ArrayList<ClientNotice>();

    static
    {
        ClientNotice notice = new ClientNotice();
        notice.setDate(new Date());
        notice.setLink("/home/swm");
        notice.setNoticeType("UPDATE");
        notice.setTorrentName("Torrent Torrent Torrent");
        noticeList.add(notice);
    }

    public static List<ClientNotice> getList()
    {
        return noticeList;
    }
}
