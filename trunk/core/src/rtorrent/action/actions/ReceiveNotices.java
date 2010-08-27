package rtorrent.action.actions;

import rtorrent.action.Action;
import rtorrent.notice.client.NoticesHolder;

/**
 * User: welvet
 * Date: 27.08.2010
 * Time: 19:56:01
 */
public class ReceiveNotices implements Action
{
    public void initialize()
    {

    }

    public Object run(Object param)
    {
        return NoticesHolder.getList();
    }
}
