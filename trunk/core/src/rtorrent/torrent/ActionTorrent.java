package rtorrent.torrent;

import org.eclipse.ecf.protocol.bittorrent.TorrentFile;

import java.io.File;
import java.io.IOException;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 18:53:01
 */
public class ActionTorrent extends ActionTorrentBase {
    private File file;
    private transient TorrentFile torrentFile;

    /**
     * Создаем новый торрент на основе торрент файла. Из него заносятся имя и хеш.
     * Нельзя использовать конструктор для обновления существующено торрента, для этого есть торрент файл сеттер
     *
     * @param file
     * @throws TorrentValidateException
     */
    public ActionTorrent(File file) throws TorrentValidateException {
        TorrentFile torrentFile = initTorrentFile(file);
        this.file = file;
        setHash(getTorrentFileHash());
        setName(torrentFile.getName());
        setNeedAdd(true);
    }

    /**
     * Не сериализуемый объект, поэтому такие проблемы
     *
     * @param file
     * @return
     * @throws TorrentValidateException
     */
    private TorrentFile initTorrentFile(File file) throws TorrentValidateException {
        try {
            if ((file != null) && (torrentFile == null))
                torrentFile = new TorrentFile(file);
            if ((file != null))
                if (!torrentFile.getFile().equals(file))
                    torrentFile = new TorrentFile(file);
        } catch (IOException e) {
            throw new TorrentValidateException("Файл не найден");
        }
        return torrentFile;
    }

    public TorrentFile getFile() throws TorrentValidateException {
        return initTorrentFile(this.file);
    }

    public String getTorrentFileHash() throws TorrentValidateException {
        return initTorrentFile(file).getHexHash().toUpperCase();
    }

    /**
     * Чтобы избежать дублирования в Сете новый хеш не устанавливается в этом методе
     * его необходимо установить самостоятельно методом updateHashFromFile() после обновления на рторренте;
     *
     * @param file
     * @throws TorrentValidateException
     */
    public void setFile(File file) throws TorrentValidateException {
        torrentFile = initTorrentFile(file);
        this.file = file;
    }

    /**
     * Обновляем торрент файл, дату обновления и устанавливаем needUpdate true
     *
     * @param torrent
     */
    public void updateFile(ActionTorrent torrent) {
        file = torrent.file;
    }

    public void updateAll(ActionTorrent torrent) {
        updateInfo(torrent);
        updateAction(torrent);
        updateFile(torrent);
    }

    public void updateHashFromFile() throws TorrentValidateException {
        setHash(getTorrentFileHash());
    }

    /**
     * Пустой конструктор, должен использоваться только в RtorrentService
     */
    public ActionTorrent() {
    }

}
