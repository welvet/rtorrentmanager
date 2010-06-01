package rtorrent.torrent;

/**
 * User: welvet
 * Date: 01.06.2010
 * Time: 19:34:58
 */
public interface TorrentInfo {
    /**
     * Возвращает имя торрент файла
     *
     * @return
     */
    public String getName();

    /**
     * Возвращает хеш торрент файла
     *
     * @return
     */
    public String getHash();

    /**
     * Возвращает статус торрента
     *
     * @return
     */
    public State getState();

    /**
     * Возвращает процент выполнения (загрузки или хеширования)
     *
     * @return
     */
    public Integer getPercentage();

    /**
     * Возвращает скаченый размер (в байтах)
     *
     * @return
     */
    public Long getCompliteSize();

    /**
     * Возвращает полный размер (в байтах)
     *
     * @return
     */
    public Long getFullSize();

    /**
     * возвращает ратио
     * @return
     */
    public Float getRatio();

    /**
     * Возвращает общее количество пиров
     *
     * @return
     */
    public Integer getPeersConnected();

    /**
     * Вовращает общее количество сидов
     *
     * @return
     */
    public Integer getSids();

    /**
     * Возвращает массив с данными
     * @return
     */
    public Object[] toArray();
}
