package rtorrent.utils;

import org.apache.log4j.Logger;

/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 17:54:01
 */
public class LoggableException extends Exception {
    private static Logger log = LoggerSingleton.getLogger();

    public LoggableException() {
        super();
        log.error(this.getClass().getName() + ". Неизвестная ошибка");
    }

    public LoggableException(String message) {
        super(message);
        log.error(this.getClass().getName() + ". " + message);
    }

    public LoggableException(String message, Throwable cause) {
        super(message, cause);
        if (message == null)
            log.error(this.getClass().getName() + ". " + cause);
        else
            log.error(this.getClass().getName() + ". " + cause + " " + message);
    }

    public LoggableException(Throwable cause) {
        super(cause);
        log.error(this.getClass().getName() + ". " + cause);
    }
}
