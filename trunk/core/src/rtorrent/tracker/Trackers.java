package rtorrent.tracker;

import rtorrent.tracker.lostfilm.LostFilmWorker;
import rtorrent.tracker.rutracker.RuTrackerWorker;

import java.io.Serializable;

/**
 * Поддерживаемые трекеры
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:11:41
 */
public enum Trackers implements Serializable {
    MOCK("none", null, null), //мок трекер
    RUTRACKER("rutracker", "http://rutracker.org/forum/viewtopic.php?t=", RuTrackerWorker.class), //rutracker.org
    LOSTFILM("lostfilm", "http://www.lostfilm.tv/browse.php?cat=", LostFilmWorker.class); //lostfilm

    private String name;
    private String url;
    private Class<? extends TrackerWorker> workerClass;

    Trackers(String name, String url, Class<? extends TrackerWorker> aClass) {
        this.url = url;
        this.name = name;
        workerClass = aClass;
    }            

    public String toString() {
        return name;
    }

    public Boolean compare(String name) {
        return this.name.equals(name);
    }

    public String getUrl() {
        return url;
    }

    public Class<? extends TrackerWorker> getWorkerClass() {
        return workerClass;
    }
}
