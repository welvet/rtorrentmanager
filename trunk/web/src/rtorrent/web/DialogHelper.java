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
        String s = (String) request.getAttribute("dialog");
        Dialog dialog = DialogManager.createDialog(s);

        request.setAttribute("dialog", "properties.jsp");
        request.setAttribute("currentDialog", dialog);
    }

    public static void updateDialog(Map parameterMap) {
        Dialog dialog = DialogManager.createDialog(((String[]) parameterMap.get("path"))[0]);
        parameterMap.remove("path");

        for (Input input : dialog.getInputs()) {
            if (input instanceof CheckField)
                input.setFieldValue(parameterMap.get(input.getFieldName()) != null); 
            else
                input.setFieldValue(((String []) parameterMap.get(input.getFieldName()))[0]);
        }

        DialogManager.updateDialog(dialog);
    }
}
