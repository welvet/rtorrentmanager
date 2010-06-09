package rtorrent.web;

import dialog.CheckField;
import dialog.Dialog;
import dialog.SelectField;
import dialog.TextField;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
        TextField text = new TextField();

        text.setFieldName("text");
        text.setFieldDescription("Simple text field");
        text.setFieldText("Text field");
        text.setFieldValue("value");

        CheckField check = new CheckField();

        check.setFieldName("check");
//        check.setFieldDescription("Simple checkbox");
        check.setFieldText("Check field");
        check.setFieldValue(true);

        SelectField select = new SelectField();

        select.setFieldName("select");
        select.setFieldDescription("Simple select");
        select.setFieldText("Select field");
        List<String> values = new ArrayList<String>();
        values.add("value 1");
        values.add("value 2");
        values.add("value 3");
        select.setFieldValues(values);
        select.setFieldValue("value 3");

        dialog.addField(text);
        dialog.addField(check);
        dialog.addField(select);

        request.setAttribute("dialog", "properties.jsp");
        request.setAttribute("currentDialog", dialog);
    }
}
