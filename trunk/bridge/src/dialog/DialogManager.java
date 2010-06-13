package dialog;

import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.control.RtorrentControler;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.DialogUtils;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 13:10:25
 */
public class DialogManager {

    public static Dialog createTorrentDialog(String hash) {
        //если это торрент диалог, то делегируем создание контролеру
        RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");
        Dialog dialog = controler.createTorrentDialog(hash);
        new DialogUtils().sort(dialog);
        return dialog;
    }

    public static Dialog createDialog(String name) {
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        DialogParser parser = (DialogParser) ContextUtils.lookup("rdialog");

        Config config = manager.getConfig(name);
        Dialog dialog = parser.parse(name);

        new DialogUtils().sort(dialog);

        dialog.setPath(name);

        for (Input input : dialog.getInputs()) {
            Object value = config.getFieldValue(input.getFieldName());
            if (value != null)
                input.setFieldValue(value);
        }
        return dialog;
    }

    public static void updateDialog(Dialog dialog) {
        if (dialog.getPath().equals("torrent")) {
            //если это диалог настройки торрента, то делегируем его создание контролеру
            RtorrentControler controler = (RtorrentControler) ContextUtils.lookup("rcontroler");
            controler.configureTorrent(dialog);
            return;
        }
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        Config config = manager.getConfig(dialog.getPath());

        for (Input input : dialog.getInputs()) {
            config.setFieldValue(input.getFieldName(), input.getFieldValue());
        }

        manager.saveConfig(config);
    }
}
