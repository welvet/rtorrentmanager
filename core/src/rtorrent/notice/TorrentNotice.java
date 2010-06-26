package rtorrent.notice;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 21:31:31
 */
public enum TorrentNotice {
    UPDATE("update"), //обновлен
    FINISH("done"); //скачан

    private String name;

    TorrentNotice(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
