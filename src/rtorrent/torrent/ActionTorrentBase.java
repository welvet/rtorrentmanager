package rtorrent.torrent;

import rtorrent.tracker.Tracker;

import java.util.Date;

/**
 * User: welvet
 * Date: 20.05.2010
 * Time: 16:27:19
 */
public abstract class ActionTorrentBase extends Torrent {
    private Boolean needStart = false;  //Запустить торрент после запуска рторрента (и далее по анологии)
    private Boolean needStop = false;
    private Boolean needAdd = false;
    private Boolean needUpdate = false;
    private Boolean needDelete = false;
    private Boolean watching = false; //Определяем, будем ли следить за обновлениями на трекере TODO необходимо добавить валидацию на url чтобы избежать лишних ошибок. (необходимо это делать на сервисном слое, а не на вью)
    private String url;
    private Date lastUpdated = new Date(0L); //Дата последнего обновления торрента
    private Tracker tracker;

    public void updateAction(ActionTorrentBase torrent) {
        needStart = torrent.needStart;
        needStop = torrent.needStop;
        needAdd = torrent.needAdd;
        needUpdate = torrent.needUpdate;
        needDelete = torrent.needDelete;
        watching = torrent.watching;
        url = torrent.url;
        lastUpdated = (Date) torrent.lastUpdated.clone();
        tracker = torrent.tracker;
    }

    public Boolean isNeedStart() {
        if (isNeedDelete())
            return false;
        return needStart;
    }

    public Boolean isNeedStop() {
        if (isNeedDelete())
            return false;
        return needStop;
    }

    public Boolean isNeedAdd() {
        if (isNeedDelete())
            return false;
        return needAdd;
    }

    public Boolean isNeedUpdate() {
        if (isNeedDelete())
            return false;
        return needUpdate;
    }

    public Boolean isWatching() {
        if (isNeedDelete())
            return false;
        return watching;
    }

    public void setNeedStart(Boolean needStart) {
        this.needStart = needStart;
    }

    public void setNeedStop(Boolean needStop) {
        this.needStop = needStop;
    }

    public void setNeedAdd(Boolean needAdd) {
        this.needAdd = needAdd;
    }

    public void setNeedUpdate(Boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public Boolean isNeedDelete() {
        return needDelete;
    }

    public void setNeedDelete(Boolean needDelete) {
        this.needDelete = needDelete;
    }

    public void setWatching(Boolean watching) {
        this.watching = watching;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Tracker getTracker() {
        return tracker;
    }

    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }
}
