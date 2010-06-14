package rtorrent.torrent;

import java.io.Serializable;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 17:28:54
 */
public abstract class Torrent implements Serializable {
    /**
     * Не забыть изменить метод update при изменении св-в
     */
    private String name;
    private String hash;
    private String dir;
    private String label;
    private Boolean active = false;
    private Boolean open = false;
    private Boolean hashChecking = false;
    private Boolean hashChecked = false;
    private Boolean start = true;
    private Long leftBytes = 0L;
    private Long bytesDone = 0L;
    private Long sizeBytes = 0L;
    private Long upTotal = 0L;
    private Long ratio = 0L;
    private Long peersConnected = 0L;
    private Long peersComplite = 0L;
    private Long hashing = 0L;


    /**
     * Копируем св-ва
     *
     * @param torrent исходный торрент
     */
    public final void updateInfo(Torrent torrent) {
        name = torrent.name;
        hash = torrent.hash;
        dir = torrent.dir;
        label = torrent.label;
        active = torrent.active;
        open = torrent.open;
        hashChecking = torrent.hashChecking;
        hashChecked = torrent.hashChecked;
        start = torrent.start;
        leftBytes = torrent.leftBytes;
        bytesDone = torrent.bytesDone;
        sizeBytes = torrent.sizeBytes;
        upTotal = torrent.upTotal;
        ratio = torrent.ratio;
        peersConnected = torrent.peersConnected;
        peersComplite = torrent.peersComplite;
        hashing = torrent.hashing;
    }

    public void setActive(Long active) {
        this.active = active == 1;

    }

    public void setOpen(Long open) {
        this.open = open == 1;
    }

    public void setHashChecking(Long hashChecking) {
        this.hashChecking = hashChecking == 1;
    }

    public void setHashChecked(Long hashChecked) {
        this.hashChecked = hashChecked == 1;
    }

    public void setStart(Long start) {
        if (start != null)
            if (start == 1L) {
                this.start = true;
                return;
            }
        this.start = false;
    }

    /**
     * Проверка торрента на завершенность.
     * Странно что сам рторрент не предоставляет таких данных
     *
     * @return
     */
    public Boolean isComplite() {
        //если торрент только что создан, то он не скачан
        if ((sizeBytes != 0L) && (bytesDone != 0L))
            if (bytesDone / sizeBytes == 1)
                return true;
        return false;
    }

    public String toString() {
        return "Torrent{" +
                "name='" + name + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return hash != null ? hash.hashCode() : 0;
    }

    public Long getPeersComplite() {
        return peersComplite;
    }

    public void setPeersComplite(Long peersComplite) {
        this.peersComplite = peersComplite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isActive() {
        return active;
    }

    public Boolean isOpen() {
        return open;
    }

    public Boolean isHashChecking() {
        return hashChecking;
    }


    public Boolean isHashChecked() {
        return hashChecked;
    }

    public Boolean isStart() {
        return start;
    }


    public Long getLeftBytes() {
        return leftBytes;
    }

    public void setLeftBytes(Long leftBytes) {
        this.leftBytes = leftBytes;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public Long getUpTotal() {
        return upTotal;
    }

    public void setUpTotal(Long upTotal) {
        this.upTotal = upTotal;
    }

    public Long getRatio() {
        return ratio;
    }

    public void setRatio(Long ratio) {
        this.ratio = ratio;
    }

    public Long getPeersConnected() {
        return peersConnected;
    }

    public void setPeersConnected(Long peers_connected) {
        this.peersConnected = peers_connected;
    }

    public Long getHashing() {
        return hashing;
    }

    public void setHashing(Long hashing) {
        this.hashing = hashing;
    }

    public Long getBytesDone() {
        return bytesDone;
    }

    public void setBytesDone(Long bytesDone) {
        this.bytesDone = bytesDone;
    }
}
