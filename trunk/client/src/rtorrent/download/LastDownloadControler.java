package rtorrent.download;

import rtorrent.ConfigSingleton;
import rtorrent.client.RequestManager;
import rtorrent.init.Initialize;
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
    private NoticeForm form;
    private Thread thread;

    private LastDownloadControler()
    {
    }

    public synchronized void run()
    {
        if (!ConfigSingleton.isNeedCheck())
            return;

        List<ClientNotice> clientNoticeList = manager.getNotices();

        clientNotices.addAll(clientNoticeList);
        ConfigSingleton.update();

        if (!clientNoticeList.isEmpty())
        {
            createOrUpdateForm(clientNoticeList);
        }
    }

    private void createOrUpdateForm(List<ClientNotice> clientNoticeList)
    {
        if (form != null && !form.getShell().isDisposed())
        {
            form.update(clientNoticeList);
        }
        else
        {
            form = new NoticeForm(clientNoticeList);
            Initialize.display.syncExec(form);
        }
    }

    /**
     * Показать форму со всеми нотисами
     */
    public synchronized void showForm()
    {
        List<ClientNotice> list = new ArrayList<ClientNotice>();
        for (ClientNotice notice : clientNotices)
        {
            if (notice.getNoticeType().equals(ConfigSingleton.getNoticesType()))
                list.add(notice);
        }

        createOrUpdateForm(list);
    }

    public static LastDownloadControler instance()
    {
        return instance;
    }
}
