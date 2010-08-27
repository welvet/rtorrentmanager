package rtorrent.notice.client;

import java.io.Serializable;
import java.util.Date;

/**
 * User: welvet
 * Date: 27.08.2010
 * Time: 19:50:03
 */
public class ClientNotice implements Serializable
{
    private String torrentName;
    private Date date;
    private String link;
    private String noticeType;

    public String getNoticeType()
    {
        return noticeType;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public String getTorrentName()
    {
        return torrentName;
    }

    public void setTorrentName(String torrentName)
    {
        this.torrentName = torrentName;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }
}
