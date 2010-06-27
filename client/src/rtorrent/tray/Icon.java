package rtorrent.tray;

import rtorrent.ConfigSingleton;
import rtorrent.settings.SettingsDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URL;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:33:35
 */
public class Icon {
    private TrayIcon trayIcon;


    public void createIcon() throws Exception {
        try {
            if (!SystemTray.isSupported()) {
                throw new Exception("Не удалось создать иконку");
            }
            final PopupMenu popup = new PopupMenu();
            final SystemTray tray = SystemTray.getSystemTray();

            trayIcon = new TrayIcon(createImage("images/fail.png", "tray icon"));


            MenuItem startBrowser = new MenuItem("Запустить RtorrentManager");
            startBrowser.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(ConfigSingleton.getAddres()));
                    } catch (Exception e1) {
                        throw new RuntimeException(e1);
                    }
                }
            });

            MenuItem stratRtrorrent = new MenuItem("Запустить/остановить rtorrent");

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
            popup.add(settings);
            popup.add(exit);

            trayIcon.setPopupMenu(popup);

            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new Exception(e);
        }
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

}
