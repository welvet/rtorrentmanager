package rtorrent.tray;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import rtorrent.ConfigSingleton;
import rtorrent.addtorrent.AddTorrent;
import rtorrent.client.RequestManager;
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
        display = new Display();
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
        manager.switchTorrent();
        changIcon(!state, false);
    }


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
        synchronized (Icon.class)
        {
            createIcon();
            Icon.class.notify();
        }

        while (runFlag)
        {
            if (!display.readAndDispatch())
            {
                refreshIcon();
                display.sleep();
            }
        }
    }

    public void waitIcon() throws InterruptedException
    {
        synchronized (Icon.class)
        {
            if (trayIcon == null)
                Icon.class.wait();
        }
    }
}
