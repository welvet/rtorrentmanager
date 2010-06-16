package rtorrent.service;

import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.Torrent;

import java.util.List;
import java.util.Set;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:16:34
 */
public interface RtorrentService {
    /**
     * Добавить торрент (торрент добавляется остановленым)
     * Торрент файл, в результате успешного добавления получает параметр needStart равным true
     *
     * @param torrent файл торрента
     * @throws RtorrentServiceException
     */
    public void add(ActionTorrent torrent) throws RtorrentServiceException;

    /**
     * Запустить торрент
     *
     * @param torrent
     * @throws RtorrentServiceException
     */
    public void start(Torrent torrent) throws RtorrentServiceException;

    /**
     * Остановить торрент
     *
     * @param torrent
     * @throws RtorrentServiceException
     */
    public void stop(Torrent torrent) throws RtorrentServiceException;

    /**
     * Удалить торрент
     *
     * @param torrent
     * @throws RtorrentServiceException
     */
    public void remove(Torrent torrent) throws RtorrentServiceException;

    /**
     * Проверка наличия торрента на рторренте
     *
     * @param hash
     * @return true если есть
     * @throws RtorrentServiceException
     */
    public Boolean verify(String hash) throws RtorrentServiceException;

    /**
     * Получить список всех торрентов
     *
     * @return
     * @throws RtorrentServiceException
     */
    public Set<ActionTorrent> getSet() throws RtorrentServiceException;

    /**
     * Проверям, можем ли соединиться с рторрентом
     *
     * @return
     */
    public Boolean isAlive();

    /**
     * Запустить rtorrent
     */
    public void launch(List<Torrent> list) throws RtorrentServiceException;

    /**
     * Оставновить rtorrent
     */
    public void shutdown(List<Torrent> list) throws RtorrentServiceException;
}
