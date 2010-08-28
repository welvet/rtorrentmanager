package rtorrent.download;

import rtorrent.notice.client.ClientNotice;

import java.util.ArrayList;

/**
 * User: welvet
 * Date: 28.08.2010
 * Time: 0:18:39
 */
public class FormTest
{
    public static void main(String[] args) throws InterruptedException
    {
        ArrayList<ClientNotice> list = new ArrayList<ClientNotice>();
        for (int i = 0; i < 4; i++)
        {
            ClientNotice notice = new ClientNotice();
            notice.setTorrentName("Title " + i);
            list.add(notice);
        }
        Thread thread = new Thread(new NoticeForm(list));
        thread.start();
        thread.join();
    }
}
