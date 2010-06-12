package dialog;

import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.utils.ContextUtils;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 13:10:25
 */
public class DialogManager {
    public static Dialog createDialog(String name) {
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        DialogParser parser = (DialogParser) ContextUtils.lookup("rdialog");

        Config config = manager.getConfig(name);
        Dialog dialog = parser.parse(name);

        dialog.setPath(name);

        if (config != null)
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
    }
}
