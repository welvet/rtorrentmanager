package rtorrent.inititalize;

import rtorrent.settings.SettingsDialog;
import rtorrent.tray.Icon;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:32:40
 */
public class Initialize {
    public static void main(String[] args) {
        try {
            new SettingsDialog();
            new Icon().createIcon();
        } catch (Exception e) {

        }
    }
}
