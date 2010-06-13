package rtorrent.web;

import dialog.CheckField;
import dialog.Dialog;
import dialog.DialogManager;
import dialog.Input;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User: welvet
 * Date: 03.06.2010
 * Time: 23:01:10
 */
public class DialogHelper {

    public static void createDialog(HttpServletRequest request) {
        Dialog dialog;
        if (request.getAttribute("torrentHash") != null) {
            dialog = DialogManager.createTorrentDialog((String) request.getAttribute("torrentHash"));
        } else {
            String s = (String) request.getAttribute("dialog");
            dialog = DialogManager.createDialog(s);
        }

        request.setAttribute("dialog", "properties.jsp");
        request.setAttribute("currentDialog", dialog);
    }

    public static void updateDialog(Map parameterMap) {
        Dialog dialog;
        String s = ((String[]) parameterMap.get("path"))[0];

        if (s.equals("torrent"))                    
            dialog = DialogManager.createTorrentDialog(((String[]) parameterMap.get("hash"))[0]);
        else
            dialog = DialogManager.createDialog(s);

        parameterMap.remove("path");

        for (Input input : dialog.getInputs()) {
            if (input instanceof CheckField)
                input.setFieldValue(parameterMap.get(input.getFieldName()) != null);
            else
                input.setFieldValue(((String[]) parameterMap.get(input.getFieldName()))[0]);
        }

        DialogManager.updateDialog(dialog);
    }
}
