package rtorrent.control;

import rtorrent.torrent.ActionTorrent;
import rtorrent.torrent.State;
import rtorrent.torrent.TorrentInfo;
import rtorrent.utils.SizeFormater;

/**
 * User: welvet
 * Date: 01.06.2010
 * Time: 19:49:29
 */
public class TorrentInfoImpl implements TorrentInfo {
    private String name;
    private String hash;
    private State state;
    private Integer percentage;
    private Long compliteSize;
    private Long fullSize;
    private Float ratio;
    private Integer peersConnected;
    private Integer sids;
    private static final int ITEMS_COUNT = 9;


    public void copyInfo(ActionTorrent torrent) {
        name = torrent.getName();
        hash = torrent.getHash();
        calcState(torrent);
        percentage = calcPercentage(torrent);
        compliteSize = torrent.getBytesDone();
        fullSize = torrent.getSizeBytes();
        ratio = torrent.getRatio().floatValue()/(100*10);
        peersConnected = torrent.getPeersConnected().intValue();
        sids = torrent.getPeersComplite().intValue();
    }

    private Integer calcPercentage(ActionTorrent torrent) {
        if (torrent.getSizeBytes() == 0) return 0;
        Double a = Double.longBitsToDouble(torrent.getBytesDone());
        Double b = Double.longBitsToDouble(torrent.getSizeBytes());
        Long result = Math.round( a/b *100);
        return result.intValue();
    }

    private void calcState(ActionTorrent torrent) {
        if (torrent.isStart())
            state = State.start;
        else state = State.stop;
        if (torrent.isComplite())
            state = State.finish;
        if (torrent.isHashChecking())
            state = State.hashing;
        if (torrent.isNeedDelete())
            state = State.delete;
    }

    public Object[] toArray() {
        Object[] objects = new Object[ITEMS_COUNT];
        objects[0] = hash;
        objects[1] = name;
        objects[2] = state;
        objects[3] = percentage+"%";
        objects[4] = SizeFormater.formatSize(compliteSize, 2);
        objects[5] = SizeFormater.formatSize(fullSize, 2);
        objects[6] = ratio;
        objects[7] = peersConnected;
        objects[8] = sids;
        return objects;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Long getCompliteSize() {
        return compliteSize;
    }

    public void setCompliteSize(Long compliteSize) {
        this.compliteSize = compliteSize;
    }

    public Long getFullSize() {
        return fullSize;
    }

    public Float getRatio() {
        return ratio;
    }

    public void setFullSize(Long fullSize) {
        this.fullSize = fullSize;
    }

    public Integer getPeersConnected() {
        return peersConnected;
    }

    public void setPeersConnected(Integer peersConnected) {
        this.peersConnected = peersConnected;
    }

    public Integer getSids() {
        return sids;
    }

    public void setSids(Integer sids) {
        this.sids = sids;
    }
}
