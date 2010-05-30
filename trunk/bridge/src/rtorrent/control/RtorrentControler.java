package rtorrent.control;

import java.io.File;
import java.util.Set;

/**
 * User: welvet
 * Date: 30.05.2010
 * Time: 22:52:19
 */
public interface RtorrentControler {
    /**
     * Получить лист с активными торрентами
     *
     * @return
     */
    public Set getList();

    /**
     * Добавить торрент
     *
     * @param torrentFile
     */
    public void addTorrent(File torrentFile);

    /**
     * Запустить торрент
     *
     * @param hash
     */
    public void startTorrent(String hash);

    /**
     * Остановить торрент
     *
     * @param hash
     */
    public void stopTorrent(String hash);

    /**
     * Удалить торрент
     *
     * @param hash
     */
    public void removeTorrent(String hash);

    /**
     * Обновить настройки торрента
     * ссылку, параметр isWatching
     *
     * @param hash
     */
    public void configureTorrent(String hash);
}
