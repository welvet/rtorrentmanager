package rtorrent.torrent.set;

import rtorrent.torrent.set.action.*;

class TorrentSetHelper implements Runnable {
    private TorrentSetActionFactory factory;

    public TorrentSetHelper(TorrentSetImpl torrentSetImpl) {
        factory = new TorrentSetActionFactory(torrentSetImpl.getRtorrentService(), torrentSetImpl);
    }

    public void run() {
        factory.build(SetUpdate.class).run();
        factory.build(Add.class).run();
        factory.build(Start.class).run();
        factory.build(Stop.class).run();
        factory.build(Update.class).run();
        factory.build(Delete.class).run();
    }
}