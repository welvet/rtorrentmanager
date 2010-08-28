package rtorrent.download;

import rtorrent.ConfigSingleton;
import rtorrent.client.RequestManager;
import rtorrent.notice.client.ClientNotice;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * User: welvet
 * Date: 27.08.2010
 * Time: 22:52:54
 */
public class LastDownloadControler extends TimerTask
{
    private static RequestManager manager = new RequestManager();
    private static List<ClientNotice> clientNotices = ConfigSingleton.getClientNotices();
    private static LastDownloadControler instance = new LastDownloadControler();

    private LastDownloadControler()
    {
    }

    public synchronized void run()
    {
        List<ClientNotice> clientNoticeList = manager.getNotices();

        clientNotices.addAll(clientNoticeList);
        ConfigSingleton.update();

        if (!clientNoticeList.isEmpty())
        {
            NoticeForm form = new NoticeForm(clientNoticeList);
            Thread thread = new Thread(form);
            thread.start();
        }
    }

    private synchronized List<ClientNotice> getList()
    {
        List<ClientNotice> list = new ArrayList<ClientNotice>(clientNotices);
        return list;
    }

    public static LastDownloadControler instance()
    {
        return instance;
    }
}
