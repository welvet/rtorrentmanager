package rtorrent.tracker;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 18:56:24
 */
public class Tracker {

    public Tracker clone() throws CloneNotSupportedException {
        super.clone();
        return new Tracker();
    }

    /**
     * @return новый объект, с скопироваными свойствами
     */
    public Tracker copy() {
        return new Tracker();  //TODO change me
    }
}
