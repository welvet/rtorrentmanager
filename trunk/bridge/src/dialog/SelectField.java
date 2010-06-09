package dialog;

import java.util.List;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 19:38:57
 */
public class SelectField extends Input {
    private String fieldValue;
    private List<String> fieldValues;

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = (String) fieldValue;
    }

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public String getHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<select name=\"").append(fieldName).append("\">");
        for (String value : fieldValues)
            builder.append("<option value=\"").append(value).append("\"").append(value.equals(fieldValue) ? "selected" : "").append(">")
                    .append(value).append("</option>");
        builder.append("</select>");
        return builder.toString();
    }
}
