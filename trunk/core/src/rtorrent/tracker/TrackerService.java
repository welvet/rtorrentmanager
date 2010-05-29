package rtorrent.tracker;

import rtorrent.torrent.ActionTorrent;

/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 21:45:31
 */
public interface TrackerService {
    /**
     * Проверяем жив ли трекер
     *
     * @return true если жив
     * @throws TrackerException
     */
    public Boolean isAlive() throws TrackerException;

    /**
     * Проверяем на авторизацию, и авторизируемся, если это нужно
     * TODO Возможно стоит объединить оба метода
     *
     * @return true если смогли авторизоваться
     * @throws TrackerException
     */
    public Boolean isAuth() throws TrackerException;

    /**
     * Проверяем, нужен ли торренту апдейт
     *
     * @param torrent - торрент в процессе проверки меняет свойство needTorrentFileUpdate
     * @return возвращаем flase если проверка завершилась неудачей
     * @throws TrackerException
     */
    public Boolean chechUpdate(ActionTorrent torrent) throws TrackerException;

    /**
     * Обновляем с-во File, needUpdate у торрент файла. Если обновиться не получилось, то обновлять needTorrentFileUpdate и Date не нужно
     * TODO необходимо писать в лог сообщения, а затем показывать их пользователю после его входа.
     *
     * @param torrent обновляемый торрент
     * @return
     */
    public Boolean update(ActionTorrent torrent) throws TrackerException;
}
