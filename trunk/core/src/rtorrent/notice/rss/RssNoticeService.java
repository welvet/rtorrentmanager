package rtorrent.notice.rss;

import org.apache.log4j.Logger;
import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.notice.NoticeService;
import rtorrent.notice.TorrentNotice;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.LoggerSingleton;
import rtorrent.utils.Saver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO наверно стоит сделать Document
 * User: welvet
 * Date: 26.06.2010
 * Time: 17:58:28
 */
public class RssNoticeService implements NoticeService {
    private Config config;
    private static final String RSS_DAT = "rss.dat";
    private static final String AUTHOR = "Honoka-chan";
    private List<RssNotice> list;
    private Saver saver = new Saver(RSS_DAT);
    private TorrentNotice level;
    private static final String HEADER = "<rss version=\"2.0\"><channel>";
    private static final String FOOTER = "</channel></rss>";
    private static final String RSS_XML = "rss.xml";
    private static final int COUNT = 10;
    private Logger log = LoggerSingleton.getLogger();

    public void initialize() {
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        config = manager.getConfig("notice");
        level = TorrentNotice.valueOf(((String) config.getFieldValue("levelRssNotice")).toUpperCase());
        list = saver.load();
    }

    public void notice(ActionTorrent torrent, TorrentNotice notice) {
        if (notice.equals(level)) {
            RssNotice rssNotice = new RssNotice();
            rssNotice.setTitle(torrent.getName());
            rssNotice.setDescription(torrent.getName() + " is " + notice);
            rssNotice.setPubDate(new Date());
            rssNotice.setLink(config.getFieldValue("path") + torrent.getDir().replace((String) config.getFieldValue("replace"), ""));
            rssNotice.setAuthor(AUTHOR);
            list.add(rssNotice);
            saver.save(list);
            try {
                genereXml();
            } catch (IOException e) {
                log.info(e);
            }
        }
    }

    private void genereXml() throws IOException {
        File dir = (File) ContextUtils.lookup("workdir");
        File file = new File(dir.getAbsolutePath() + "/" + RSS_XML);
        StringBuilder builder = new StringBuilder();
        builder.append(HEADER);
        LinkedList<RssNotice> linkedList = new LinkedList<RssNotice>(list);
        Integer i = COUNT;
        while (linkedList.size() > 0 && i >= 0) {
            i--;
            builder.append(linkedList.removeLast());
        }
        builder.append(FOOTER);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
        stream.write(builder.toString().getBytes());
        stream.flush();
        stream.close();
    }

    public boolean checkConfig() {
        return (Boolean) config.getFieldValue("needRssNotice");
    }

}
