package rtorrent.test;

import junit.framework.TestCase;
import rtorrent.control.RtorrentControlerImpl;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.utils.UtilException;
import rtorrent.web.WebServerBuilder;

import java.io.File;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:51:07
 */
public class WebServerTest extends TestCase {
    //todo переделать на темп дир
    private static final String WAR_PATH = "C:\\rtorrentmanager\\out\\rtorrentmanager\\web.war";

    public void testStart() throws Exception, UtilException {
        if (false)
            throw new RuntimeException("Этот метод в тесте нужен только для разработки" +
                    " Необходимо будет удалить его");

        File datFile = new File(RtorrentServiceTest.class.getResource("resource/").getPath() + "torrents.dat");
        TorrentSetSingleton.initialze(new MockRtorrentService(), datFile);
        new RtorrentControlerImpl().bindContext();

        WebServerBuilder builder = new WebServerBuilder();
        builder.setPort("8081");
        builder.setWar(WAR_PATH);
        builder.build();

        while (true) {
            Thread.sleep(1000);
        }
    }
}
