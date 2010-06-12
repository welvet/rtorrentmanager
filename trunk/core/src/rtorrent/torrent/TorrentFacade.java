package rtorrent.torrent;

import rtorrent.tracker.Tracker;

import java.io.File;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 22:23:38
 */
public interface TorrentFacade {
    /**
     * @return информация, нужная трекеру для обновления
     */
    public Tracker getTracker();

    /**
     * @param file файл с новым торрентом
     * @throws TorrentValidateException ошибка при обработке файла
     */
    public void setFile(File file) throws TorrentValidateException;

    /**
     * @param needUpdate обновить торрент
     */
    public void setNeedUpdate(Boolean needUpdate);
}
