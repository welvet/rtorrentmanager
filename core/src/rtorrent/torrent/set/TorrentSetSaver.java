package rtorrent.torrent.set;

import org.apache.log4j.Logger;
import rtorrent.utils.LoggerSingleton;

import java.io.*;

public class TorrentSetSaver {
    private File file;
    private final TorrentSetImpl torrentSetImpl;
    private Logger log = LoggerSingleton.getLogger();

    public TorrentSetSaver(TorrentSetImpl torrentSetImpl, File file) {
        this.file = file;
        this.torrentSetImpl = torrentSetImpl;
    }

    void load() {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            torrentSetImpl.setTorrents((TorrentHashtable) inputStream.readObject());
            inputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            torrentSetImpl.setTorrents(new TorrentHashtable());
        }
    }

    synchronized void save() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(torrentSetImpl.getTorrents());
            outputStream.flush();
            outputStream.close();
            fileOutputStream.close();
            log.debug(file + " сохранен");
        } catch (Exception e) {
            log.error(e);
        }
    }
}