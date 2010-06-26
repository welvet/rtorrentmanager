package rtorrent.tracker;

import java.io.Serializable;

/**
 * Поддерживаемые трекеры
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:11:41
 */
public enum Trackers implements Serializable {
    MOCK("none"), //мок трекер
    RUTRACKER("rutracker"), //rutracker.org
    LOSTFILM("lostfilm"); //lostfilm

    private String name;
    Trackers(String name) {
        this.name = name;
    }            

    public String toString() {
        return name;
    }

    public Boolean compare(String name) {
        return this.name.equals(name);
    }
}
