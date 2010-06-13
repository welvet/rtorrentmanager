package rtorrent.torrent.set;

import rtorrent.service.RtorrentService;
import rtorrent.torrent.ActionTorrent;

import java.util.Set;

/**
 * Класс инкапсулирует в себе (транзакционную ^_^) работу с рторрент сервисом и хранение данных
 * <p/>
 * Класс актив торрента обладает следующими с-вами
 * private Boolean needStart = false; - будет запущен при следущей связи с рторрентом
 * private Boolean needStop = false; - будет остановлен при следующей связи с рторрентом
 * (важно помнить что остановленый торрент не будет выдаваться при вызове метода getWatchedSet(),
 * поэтому данный метод стоит использовать только из шедулера)
 * private Boolean needAdd = true; - торрент будет добавлен при следующей связи. (NB! торрент должен иметь файл на диске)
 * (по умолчанию создаваемый торрент будет автоматически добавляться)
 * private Boolean needUpdate = false; - торрент должен иметь НОВЫЙ файл на диске.
 * private Boolean needDelete = false; - торрент будет удален из рторрента. Также торрент не будет показываться при выдаче getWatchedSet()
 * однако будет в выдаче всего листа, для возможности отмены удаления
 * User: welvet
 * Date: 18.05.2010
 * Time: 17:29:03
 */
public interface TorrentSet {
    /**
     * Запросить обновление у рторрента, и обновить все локальные торренты
     */
    @Deprecated
    public void updateSet();

    /**
     * Обновить (добавить, остановить, удалить) все торренты на рторренте
     */
    @Deprecated
    public void updateRtorrent();

    /**
     * Обновить
     */
    public void update();

    /**
     * Получить список всех торрентов
     *
     * @return
     */
    public Set<ActionTorrent> getSet();

    /**
     * Получить список наблюдаемых торрентов
     *
     * @return
     */
    public Set<ActionTorrent> getWatchedSet();

    /**
     * Получить торрент файл по хешу
     *
     * @param hash UPPER хеш
     * @return связаный торрент
     */
    public ActionTorrent getByHash(String hash);

    /**
     * Обновить параметры торрента и поставить его в очередь
     *
     * @param torrent Торрент файл, для обновления
     */
    public void addOrUpdate(ActionTorrent torrent);

    void setService(RtorrentService service);
}
