package dialog;

/**
 * User: welvet
 * Date: 03.06.2010
 * Time: 22:50:59
 */
public class TextField extends Input {
    private String fieldValue;

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = (String) fieldValue;
    }

    public String getHtml() {
        return "<input name=\"" + fieldName + "\" type=\"" + (hidden ? "hidden" : "text") + "\" value=\"" + fieldValue + "\"/>";
    }
}
