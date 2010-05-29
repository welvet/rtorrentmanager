/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 14:50:23
 */
package rtorrent.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerSingleton {
    private static Logger logger = setLogger();

    private static Logger setLogger() {
        PropertyConfigurator.configure(LoggerSingleton.class.getResource("log4j.properties"));
        return Logger.getRootLogger();
    }

    public static Logger getLogger() {
        return logger;
    }
}
