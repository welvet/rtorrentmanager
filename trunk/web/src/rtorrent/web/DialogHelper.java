package rtorrent.web;

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
        Dialog dialog = DialogManager.createDialog("test");

        request.setAttribute("dialog", "properties.jsp");
        request.setAttribute("currentDialog", dialog);
    }

    public static void updateDialog(Map parameterMap) {
        Dialog dialog = DialogManager.createDialog((String) parameterMap.get("path"));
        parameterMap.remove("path");

        for (Input input :dialog.getInputs()) {
            //todo проверка на boolean * if input instance of...
            input.setFieldValue(parameterMap.get(input.getFieldName()));
        }

//        DialogManager.updateDialog();
    }
}
