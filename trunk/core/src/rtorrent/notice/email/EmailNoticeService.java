package rtorrent.notice.email;

import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.notice.NoticeService;
import rtorrent.notice.TorrentNotice;
import rtorrent.torrent.ActionTorrent;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.LoggerSingleton;

/**
 * User: welvet
 * Date: 26.06.2010
 * Time: 20:39:49
 */
public class EmailNoticeService implements NoticeService {
    private Config config;
    private TorrentNotice level;
    private Logger logger = LoggerSingleton.getLogger();

    public void initialize() {
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        config = manager.getConfig("notice");
        level = TorrentNotice.valueOf(((String) config.getFieldValue("levelEmailNotice")).toUpperCase());
    }

    public void notice(ActionTorrent torrent, TorrentNotice notice) {
        try {
            if (notice.equals(level)) {
                SimpleEmail email = new SimpleEmail();
                email.setHostName((String) config.getFieldValue("host"));
                email.setAuthentication((String) config.getFieldValue("login"), (String) config.getFieldValue("pass"));
                email.setFrom((String) config.getFieldValue("fromMail"));
                email.addTo((String) config.getFieldValue("toMail"));
                email.setSubject(torrent.getName());
                email.setMsg(torrent.getName() + " is " + notice);
                email.send();
            }
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    public boolean checkConfig() {
        return (Boolean) config.getFieldValue("needEmailNotice");
    }
}
