package rtorrent.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * User: welvet
 * Date: 12.06.2010
 * Time: 13:21:42
 */
public class BindContext {

    public static void bind(String s, Object o) {
        try {
            InitialContext context = new InitialContext();
            context.bind(s, o);
        } catch (NamingException e) {
            LoggerSingleton.getLogger().error(e);
        }
    }
}
