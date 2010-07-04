package rtorrent.tracker.rutracker;

import org.apache.log4j.Logger;
import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.torrent.TorrentFacade;
import rtorrent.torrent.TorrentValidateException;
import rtorrent.tracker.*;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.LoggerSingleton;

import java.io.File;
import java.util.Date;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 0:41:08
 */
public class RuTrackerWorker implements TrackerWorker {
    public RuTrackerHelper helper;
    private Logger logger = LoggerSingleton.getLogger();
    private static String cookie;

    public void initialize(File workDir) throws TorrentWorkerException {
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        Config config = manager.getConfig("rutracker");
        helper = new RuTrackerHelper((String) config.getFieldValue("login"), (String) config.getFieldValue("pass"), workDir);
        if (cookie != null)
            helper.setRemoteCookie(cookie);
        try {
            if (!helper.checkAuth()) {
                helper.auth();
                //вторая попытка
                if (!helper.checkAuth())
                    throw new TorrentWorkerException("Ошибка авторизации на rutracker.org");
            }
            cookie = helper.getCookie();
        } catch (Exception e) {
            throw new TorrentWorkerException(e);
        }
    }

    public TorrentFacade work(TorrentFacade torrent) throws TrackerException {
        try {
            SimpleTrackerImpl tracker = (SimpleTrackerImpl) torrent.getTracker();
            if (helper.checkTorrent(tracker.getUrl())) {
                File file = helper.downloadFile(tracker.getUrl());
                torrent.setFile(file);
                torrent.setNeedUpdate(true);
                torrent.setLastUpdated(new Date());
            }
            return torrent;
        } catch (ClassCastException e) {
            logger.error("У " + torrent + " нет активного трекера");
            throw new TrackerException();
        } catch (TrackerException e) {
            throw new TrackerException(e);
        } catch (TorrentValidateException e) {
            throw new TrackerException(e);
        }
    }

    public TorrentFacade checkOnceTorrent(TorrentFacade torrent) throws TrackerException {
        try {
            SimpleTrackerImpl tracker = (SimpleTrackerImpl) torrent.getTracker();
            File file = helper.downloadFile(tracker.getUrl());
            torrent.setFile(file);
            torrent.setNeedUpdate(true);
            torrent.setLastUpdated(new Date());
            return torrent;
        } catch (ClassCastException e) {
            logger.error("У " + torrent + " нет активного трекера");
            throw new TrackerException();
        } catch (TrackerException e) {
            throw new TrackerException(e);
        } catch (TorrentValidateException e) {
            throw new TrackerException(e);
        }
    }

    public Trackers whoIs() {
        return Trackers.RUTRACKER;
    }
}
