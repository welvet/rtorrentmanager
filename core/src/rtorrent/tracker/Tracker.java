package rtorrent.tracker;

import java.io.Serializable;

/**
 * Обект с необходимыми св-вами, они должны быть сериализуемыми
 * User: welvet
 * Date: 18.05.2010
 * Time: 18:56:24
 */
public interface Tracker extends Serializable{

    /**
     * @return новый объект, с скопироваными свойствами
     */
    public Tracker copy();
    public Trackers getTracker();
}
