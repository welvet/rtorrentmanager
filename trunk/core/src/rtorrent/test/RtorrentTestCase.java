package rtorrent.test;

import junit.framework.TestCase;
import rtorrent.config.ConfigManagerImpl;
import rtorrent.control.RtorrentControlerImpl;
import rtorrent.dialog.DialogParserImpl;
import rtorrent.notice.NoticeObserverSingleton;
import rtorrent.notice.NoticeService;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceImpl;
import rtorrent.torrent.set.TorrentSet;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.web.WebServerBuilder;

import javax.naming.InitialContext;
import java.io.File;

/**
 * User: welvet
 * Date: 05.06.2010
 * Time: 13:59:21
 */
public abstract class RtorrentTestCase extends TestCase {
    File dir;
    String warPath;
    File datFile;
    File torrentFile;
    File torrent2File;
    RtorrentService rtorrentService;
    TorrentSet torrentSet;
    RtorrentControlerImpl controler;
    ConfigManagerImpl configManager;
    WebServerBuilder builder;
    private Boolean loaded = false;
    static final int WAIT_TIME = 3000;

    @Override
    protected void setUp() throws Exception {
        //для корректной работы необходимо выполнить таргет prepre-test
        datFile = new File(RtorrentTestCase.class.getResource("resource/").getPath());
        torrentFile = new File(RtorrentTestCase.class.getResource("resource/").getPath() + "test.torrent");
        torrent2File = new File(RtorrentTestCase.class.getResource("resource/").getPath() + "test2.torrent");
        //ссылка на темп директорию
        dir = new File(System.getProperty("java.io.tmpdir"));
        //сслка на собраный варник
        //todo переделать на темп дир
        warPath = "C:\\rtorrentmanager\\out\\rtorrentmanager\\web.war";
        //создаем рторрент сервис
//        rtorrentService = new MockRtorrentService();
        //создаем синглтон
        rtorrentService = new RtorrentServiceImpl("serv", 500);
        TorrentSetSingleton.initialze(rtorrentService, datFile);
        torrentSet = TorrentSetSingleton.getInstance();
        //создаем контролер
        controler = new RtorrentControlerImpl();
        controler.bindContext();
        //создаем конфиги
        configManager = new ConfigManagerImpl(dir);
        configManager.bindContext();
        //загружаем парсер
        DialogParserImpl dialogParser = new DialogParserImpl();
        dialogParser.bindContext();
        //создаем веб сервер
        builder = new WebServerBuilder();
        builder.setWar(warPath);
        //регистрируем NoticeService
        NoticeService service = new MockNoticeService();
        NoticeObserverSingleton.clearService();
        NoticeObserverSingleton.registerService(service);
    }

    @Override
    protected void tearDown() throws Exception {
        //очищаем контекст
        InitialContext context = new InitialContext();
        context.unbind("rcontroler");
        context.unbind("rconfig");
    }
}
