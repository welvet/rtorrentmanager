package rtorrent.tracker;

import java.io.Serializable;

/**
 * Поддерживаемые трекеры
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:11:41
 */
public enum Trackers implements Serializable {
    MOCK("none", null), //мок трекер
    RUTRACKER("rutracker", "http://rutracker.org/forum/viewtopic.php?t="), //rutracker.org
    LOSTFILM("lostfilm", "http://www.lostfilm.tv/browse.php?cat="); //lostfilm

    private String name;
    private String url;
    Trackers(String name, String url) {
        this.url = url;
        this.name = name;
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
}
