package rtorrent.notice.client;

import rtorrent.notice.NoticeService;
import rtorrent.notice.TorrentNotice;
import rtorrent.torrent.ActionTorrent;

import java.util.Date;

/**
 * User: welvet
 * Date: 27.08.2010
 * Time: 19:47:42
 */
public class ClientNoticeService implements NoticeService
{

    public void initialize()
    {

    }

    public synchronized void notice(ActionTorrent torrent, TorrentNotice notice)
    {
        ClientNotice clientNotice = new ClientNotice();
        clientNotice.setDate(new Date());
        clientNotice.setTorrentName(torrent.getName());
        clientNotice.setNoticeType(notice.toString());
        NoticesHolder.addNotice(clientNotice);
    }

    public boolean checkConfig()
    {
        //всегда доступно
        return true;
    }
}
