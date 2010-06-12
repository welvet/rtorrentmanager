package rtorrent.notice;

import rtorrent.torrent.ActionTorrent;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 23:22:20
 */
public class Notice {
    private TorrentNotice notice;
    private ActionTorrent torrent;

    public TorrentNotice getNotice() {
        return notice;
    }

    public void setNotice(TorrentNotice notice) {
        this.notice = notice;
    }

    public ActionTorrent getTorrent() {
        return torrent;
    }

    public void setTorrent(ActionTorrent torrent) {
        this.torrent = torrent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notice notice1 = (Notice) o;

        if (notice != notice1.notice) return false;
        if (torrent != null ? !torrent.equals(notice1.torrent) : notice1.torrent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = notice != null ? notice.hashCode() : 0;
        result = 31 * result + (torrent != null ? torrent.hashCode() : 0);
        return result;
    }
}
