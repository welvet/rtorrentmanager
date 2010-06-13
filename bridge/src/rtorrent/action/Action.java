package rtorrent.action;

/**
 * User: welvet
 * Date: 13.06.2010
 * Time: 19:13:51
 */
public interface Action {
    public void initialize();

    public Object run(Object param);
}
