package rtorrent.init;

import org.eclipse.swt.widgets.Display;
import rtorrent.addtorrent.AddTorrent;
import rtorrent.download.LastDownloadControler;
import rtorrent.tray.Icon;

import java.io.File;
import java.util.Timer;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:32:40
 */
public class Initialize
{
    private static final Integer PER_SEC = 1000 * 60;
    public static Display display = new Display();

    public static void main(String[] args)
    {
        try
        {
            //инициализируем добавление торрента
            if (args != null && args.length > 0)
            {
                AddTorrent addTorrent = new AddTorrent(new File(args[0]));
                return;
            }
          
            //инициализируем стандартное поведение
            Icon icon = new Icon();

            //обработчик уведомлений
            Timer timer = new Timer();
            timer.schedule(LastDownloadControler.instance(), 60 * PER_SEC, 60 * PER_SEC);

            display.syncExec(icon);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
