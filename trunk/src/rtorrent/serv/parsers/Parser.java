package rtorrent.serv.parsers;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:28:00
 */
public interface Parser {
    /**
     * Строка инициализации парсера
     * @return
     */
    public String getKey();

    /**
     * Парсим
     * @param request реквест без имени парсера
     * @return
     * @throws ParserException
     */
    public String doParse(String request) throws ParserException;
}
