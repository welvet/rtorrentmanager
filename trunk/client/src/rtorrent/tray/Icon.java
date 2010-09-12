package rtorrent.tray;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import rtorrent.ConfigSingleton;
import rtorrent.ServerListener;
import rtorrent.addtorrent.AddTorrent;
import rtorrent.client.RequestManager;
import rtorrent.download.LastDownloadControler;
import rtorrent.init.Initialize;
import rtorrent.settings.SettingsDialog;

import java.io.InputStream;
import java.net.URI;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:33:35
 */
public class Icon implements Runnable
{
    private RequestManager manager = new RequestManager();
    private Display display;
    private String iconState = "fail";
    private String lastIconState = "";

    private TrayItem trayIcon;
    private Boolean state = false;
    private Boolean runFlag = true;

    public void createIcon()
    {
        //инициализация
        display = Initialize.display;

        Shell shell = new Shell(display);


        final Tray tray = display.getSystemTray();
        trayIcon = new TrayItem(tray, SWT.NONE);

        trayIcon.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                switchRtorrentAndIcon();
            }
        });

        final Menu menu = new Menu(shell, SWT.POP_UP);

        //меню
        MenuItem showRT = new MenuItem(menu, SWT.PUSH);
        showRT.setText("Show RtorrentManager");
        showRT.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                try
                {
                    desktop.browse(new URI("http://" + ConfigSingleton.getHost() + ":" + ConfigSingleton.getHttpPort()));
                } catch (Exception e1)
                {
                    throw new RuntimeException(e1);
                }

            }
        });

        MenuItem last = new MenuItem(menu, SWT.PUSH);
        last.setText("Display last download");
        last.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                LastDownloadControler.instance().showForm();
            }
        });

        MenuItem add = new MenuItem(menu, SWT.PUSH);
        add.setText("AddTorrent");
        add.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                AddTorrent addTorrent = new AddTorrent();
            }
        });


        MenuItem settings = new MenuItem(menu, SWT.PUSH);
        settings.setText("Settings");
        settings.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                SettingsDialog dialog = new SettingsDialog();
            }
        });

        MenuItem exit = new MenuItem(menu, SWT.PUSH);
        exit.setText("Exit");
        exit.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                System.exit(0);
            }
        });


        trayIcon.addListener(SWT.MenuDetect, new Listener()
        {
            public void handleEvent(Event event)
            {
                menu.setVisible(true);
            }
        });
    }

    private void switchRtorrentAndIcon()
    {
        try
        {
            manager.switchTorrent();            
            changIcon(!state, false);
        }
        catch (Exception e)
        {

        }
    }

    /**
     * Обновить иконку в трее
     * @param state true - запущен / false - остановлен
     * @param fail - запрос вернул ошибку
     */
    public synchronized void changIcon(Boolean state, Boolean fail)
    {
        this.state = state;

        String s;
        if (!fail)
        {
            if (state)
                s = "load";
            else s = "not_load";
        } else
        {
            s = "fail";
        }

        iconState = s;
    }

    private void refreshIcon()
    {
        if (iconState.equals(lastIconState))
            return;

        lastIconState = iconState;

        Image image = trayIcon.getImage();

        String path = "images/" + iconState + ".png";

        InputStream stream = Icon.class.getResourceAsStream(path);
        Image newImage = new Image(display, stream);

        trayIcon.setImage(newImage);
        trayIcon.setToolTipText("Rtorrent " + iconState.replace("_", " "));

        if (image != null)
            image.dispose();
    }

    public void run()
    {
        //инициализируем меню
        //TODO: sync ненужен
        synchronized (Icon.class)
        {
            createIcon();
            Icon.class.notify();
        }


        ServerListener.setIcon(this);
        ServerListener listener = new ServerListener();
        listener.start();

        //останавливаем торрент при запуске
        new Thread()
        {
            @Override
            public void run()
            {
                if (ConfigSingleton.getNeedStop())
                {
                    RequestManager manager = new RequestManager();
                    if (!manager.checkTorrent())
                    {
                        manager.switchTorrent();
                    }
                }
            }
        }.start();

        while (runFlag)
        {
            if (!display.readAndDispatch())
            {
                refreshIcon();
                display.sleep();
            }
        }
    }
}
