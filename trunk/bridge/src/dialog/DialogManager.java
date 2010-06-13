package dialog;

import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.control.RtorrentControler;     
import rtorrent.utils.ContextUtils;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 13:10:25
 */
public class DialogManager {

    public static Dialog createTorrentDialog(String hash) {
        //если это торрент диалог, то делегируем создание контролеру
        RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");
        return controler.createTorrentDialog(hash);
    }

    public static Dialog createDialog(String name) {
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        DialogParser parser = (DialogParser) ContextUtils.lookup("rdialog");

        Config config = manager.getConfig(name);
        Dialog dialog = parser.parse(name);

        dialog.setPath(name);

        for (Input input : dialog.getInputs()) {
            Object value = config.getFieldValue(input.getFieldName());
            if (value != null)
                input.setFieldValue(value);
        }
        return dialog;
    }

    public static void updateDialog(Dialog dialog) {
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        Config config = manager.getConfig(dialog.getPath());

        for (Input input : dialog.getInputs()) {
            config.setFieldValue(input.getFieldName(), input.getFieldValue());
        }
        //если это диалог настройки торрента, то делегируем его создание контролеру
        if (dialog.getPath().equals("torrent")) {
            RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");
            controler.configureTorrent(dialog);
        } else
            manager.saveConfig(config);
    }
}
