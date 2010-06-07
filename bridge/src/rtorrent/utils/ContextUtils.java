package rtorrent.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * User: welvet
 * Date: 07.06.2010
 * Time: 22:40:36
 */
public class ContextUtils {
    public static Object lookup(String name) {
        try {
            InitialContext context = new InitialContext();
            return context.lookup(name);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
