package rtorrent.tracker;

import rtorrent.torrent.TorrentFacade;

import java.io.File;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:13:45
 */
public interface TrackerWorker{
    public void initialize(File workDir) throws TorrentWorkerException;

    //обработать торрент
    public TorrentFacade work(TorrentFacade torrent) throws TrackerException;

    public TorrentFacade checkOnceTorrent(TorrentFacade torrent) throws TrackerException;

    public Trackers whoIs();
}
