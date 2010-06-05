package rtorrent.web;

import dialog.Dialog;
import dialog.TextInput;

import javax.servlet.http.HttpServletRequest;

/**
 * User: welvet
 * Date: 03.06.2010
 * Time: 23:01:10
 */
public class DialogHelper {

    public static void createDialog(HttpServletRequest request) {
//        todo test
        Dialog dialog = new Dialog();
        dialog.setName("Name");
        dialog.setPath("/path");
        TextInput input = new TextInput();
        input.setFieldName("inputName");
        input.setFieldDescription("inputDesc");
        input.setFieldText("inputTxt");

        dialog.addField(input);


        request.setAttribute("dialog", "properties.jsp");
        request.setAttribute("currentDialog", dialog);
    }
}
