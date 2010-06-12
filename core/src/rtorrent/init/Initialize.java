package rtorrent.init;

import dialog.DialogParser;
import org.apache.log4j.Logger;
import rtorrent.config.ConfigManager;
import rtorrent.config.ConfigManagerImpl;
import rtorrent.control.RtorrentControler;
import rtorrent.control.RtorrentControlerImpl;
import rtorrent.dialog.DialogParserImpl;
import rtorrent.notice.LogNoticeService;
import rtorrent.notice.NoticeObserverSingleton;
import rtorrent.notice.NoticeService;
import rtorrent.scheduler.SchedulerSingleton;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceImpl;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.utils.LoggerSingleton;
import rtorrent.web.WebServerBuilder;

import java.io.File;
import java.io.IOException;

/**
 * User: welvet
 * Date: 29.05.2010
 * Time: 19:31:36
 */
public class Initialize {
    private static Logger log = LoggerSingleton.getLogger();

    public static void main(String[] args) throws InterruptedException, IOException {
        //todo не работают проценты
        //todo сделать форсед обновление, если страничка открыта
        try {        //отключаем консоль
//        System.in.close();
//        System.out.close();
        //создаем рабочую директорию
        File workDir = new File(System.getProperty("user.home") + "/" + ".rmanager");
        workDir.mkdirs();
        //инициализируем диалоги
        DialogParser parser = new DialogParserImpl();
        //инициализируем конфиги
        ConfigManager manager = new ConfigManagerImpl(workDir);
        //инициализируем рторрент сервис
        RtorrentService service = new RtorrentServiceImpl();
        //инициализируем синглтон
        TorrentSetSingleton.initialze(service, workDir);
        //инициализируем контролер
        RtorrentControler controler = new RtorrentControlerImpl();
        //регистрируем нотисы
        NoticeService noticeService = new LogNoticeService();
        NoticeObserverSingleton.registerService(noticeService);
        //регистрируем трекеры
//        TorrentWorkersObserverSingleton.registerWorker();
        //запускаем шедулер
        SchedulerSingleton.startDefaultTask();
        //запускаем веб сервер
        WebServerBuilder builder = new WebServerBuilder();
        builder.build();
        //переводим основную нить в слип
        while (true) {
            Thread.sleep(1000);
        }
        } catch (Exception e) {
            log.error(e);
        }

    }
}
