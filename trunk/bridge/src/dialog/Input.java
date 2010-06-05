package dialog;

/**
 * User: welvet
 * Date: 03.06.2010
 * Time: 22:47:47
 */
public interface Input {
    /**
     * @return ид поля
     */
    public String getFieldName();

    /**
     * @return текст с описанием поля
     */
    public String getFieldText();

    /**
     * @return текст подсказки
     */
    public String getFieldDescription();

    /**
     * @return html pattern
     */
    public String getHtml();

    /**
     * @return значение поля
     */
    public String getFieldValue();
}
