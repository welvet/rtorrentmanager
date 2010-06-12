package rtorrent.tracker;

import rtorrent.torrent.TorrentFacade;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:13:45
 */
public interface TrackerWorker{
    public void initialize() throws TorrentWorkerException;    

    //обработать торрент
    public TorrentFacade work(TorrentFacade torrent);

    public Trackers whoIs();
}
