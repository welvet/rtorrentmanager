package lib.dialog;

/**
 * User: welvet
 * Date: 03.06.2010
 * Time: 22:50:59
 */
public class TextInput implements Input {
    private String fieldName;
    private String fieldText;
    private String fieldDescription;
    private String fieldValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldText() {
        return fieldText;
    }

    public void setFieldText(String fieldText) {
        this.fieldText = fieldText;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getHtml() {
        return "<input id=\"" + fieldName + "\" type=\"text\"/>";
    }
}
