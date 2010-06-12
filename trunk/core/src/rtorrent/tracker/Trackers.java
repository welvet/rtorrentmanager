package rtorrent.tracker;

import java.io.Serializable;

/**
 * Поддерживаемые трекеры
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:11:41
 */
public enum Trackers implements Serializable {
    RUTRACKER, //rutracker.org
    LOSTFILM, //lostfilm
    MOCK //мок трекер
}
