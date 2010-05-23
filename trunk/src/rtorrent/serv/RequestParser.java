package rtorrent.serv;

import rtorrent.serv.parsers.Parser;
import rtorrent.serv.parsers.ParserException;

import java.util.HashMap;

/**
 * Содержим в себе хешмеп с идентефикатором парсера. TODO необходимо стандартизировать колличество символов для выбора парсера
 * User: welvet
 * Date: 18.05.2010
 * Time: 16:15:30
 */
public class RequestParser {
    private static HashMap<String, Parser> parserMap = new HashMap<String, Parser>();
    /**
     * Заполняем мап парсеров
     * Необходимо добалять new Parser();
     * TODO м.б. сделать рефлексией?
     */
    static {
        Parser[] parsers = new Parser[] {

        };
        for (Parser parser : parsers) {
            parserMap.put(parser.getKey(), parser);
        }

    }

    public String parse(String request) throws ParserException {
        Parser parser = parserMap.get(request.substring(2, 6));
        if (parser != null)
            return parser.doParse(request);
        return null;
    }
}
