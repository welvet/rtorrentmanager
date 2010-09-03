package rtorrent.service.gson;

/**
 * User: welvet
 * Date: 04.09.2010
 * Time: 0:22:04
 */
public class Torrents
{
    private String[][] torrents;

    public String[][] getTorrents()
    {
        return torrents;
    }

    public void setTorrents(String[][] torrents)
    {
        this.torrents = torrents;
    }
}
