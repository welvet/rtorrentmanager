package rtorrent.init;

import dialog.DialogParser;
import org.apache.log4j.Logger;
import rtorrent.action.ActionManager;
import rtorrent.action.ActionManagerImpl;
import rtorrent.config.ConfigManager;
import rtorrent.config.ConfigManagerImpl;
import rtorrent.control.RtorrentControler;
import rtorrent.control.RtorrentControlerImpl;
import rtorrent.dialog.DialogParserImpl;
import rtorrent.scheduler.SchedulerSingleton;
import rtorrent.service.RtorrentService;
import rtorrent.service.RtorrentServiceImpl;
import rtorrent.torrent.set.TorrentSetSingleton;
import rtorrent.utils.BindContext;
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
        try {
//            todo доделать addTorrent            
//            todo написать уведомления по емейл
//            todo написать уведомления по рсс
//            todo написать standalone клиент            
//            todo написать хелп
//            todo написать абоут
//            todo написать хелп на google
//            todo проверить работу
            //создаем рабочую директорию
            File workDir = new File(System.getProperty("user.home") + "/" + ".rmanager");
            workDir.mkdirs();
            BindContext.bind("workdir", workDir);
            //инициализируем логер
            LoggerSingleton.initialize(workDir);
            if (args[0] != null && args[0].equals("debug"))
                LoggerSingleton.debug();
            else {
                //отключаем консоль
                System.in.close();
                System.err.close();
                System.out.close();
            }
            //инициализируем action
            ActionManager actionManager = new ActionManagerImpl();
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
