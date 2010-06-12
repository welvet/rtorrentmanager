package dialog;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 19:27:40
 */
public class CheckField extends Input {
    private Boolean fieldValue = false;

    public Boolean getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = (Boolean) (fieldValue == null ? false : fieldValue);
    }

    @Override
    public String getHtml() {
        return "<input type=\"checkbox\" name=\"" + fieldName + "\" " + (fieldValue ? "checked" : "") + ">";
    }
}
