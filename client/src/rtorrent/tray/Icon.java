package rtorrent.tray;

import rtorrent.ConfigSingleton;
import rtorrent.addtorrent.AddTorrent;
import rtorrent.client.RequestManager;
import rtorrent.settings.SettingsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:33:35
 */
public class Icon {
    private static RequestManager manager = new RequestManager();
    private TrayIcon trayIcon;
    private Boolean aBoolean = false;


    public void createIcon() throws Exception {
        try {
            if (!SystemTray.isSupported()) {
                throw new Exception("Не удалось создать иконку");
            }
            final PopupMenu popup = new PopupMenu();
            final SystemTray tray = SystemTray.getSystemTray();

            trayIcon = new TrayIcon(createImage("images/fail.png", null));


            MenuItem startBrowser = new MenuItem("Запустить RtorrentManager");
            startBrowser.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                    try {
                        desktop.browse(new URI("http://" + ConfigSingleton.getHost() + ":" + ConfigSingleton.getHttpPort()));
                    } catch (Exception e1) {
                        throw new RuntimeException(e1);
                    }
                }
            });

            MenuItem stratRtrorrent = new MenuItem("Запустить/остановить rtorrent");
            stratRtrorrent.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switchIcon();
                }
            });

            //действие при клике
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1)
                        switchIcon();
                }
            });

            MenuItem add = new MenuItem("Добавить торрент");
            add.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    AddTorrent addTorrent = new AddTorrent();
                }
            });

            MenuItem settings = new MenuItem("Настройки");
            settings.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    SettingsDialog dialog = new SettingsDialog();
                }
            });

            MenuItem exit = new MenuItem("Выход");
            exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            popup.add(startBrowser);
            popup.add(stratRtrorrent);
            popup.add(add);
            popup.add(settings);
            popup.add(exit);

            trayIcon.setPopupMenu(popup);

            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new Exception(e);
        }
    }

    private void switchIcon() {
        manager.switchTorrent();
        changIcon(!aBoolean, false);
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = Icon.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }


    public void changIcon(Boolean aBoolean, Boolean fail) {
        this.aBoolean = aBoolean;
        String s;
        if (!fail) {
            if (aBoolean)
                s = "load";
            else s = "not_load";
        } else {
            s = "fail";
        }
        trayIcon.setImage(createImage("images/" + s + ".png", null));
        trayIcon.setToolTip("Rtorrent " + s.replace("_", " "));
    }
}
