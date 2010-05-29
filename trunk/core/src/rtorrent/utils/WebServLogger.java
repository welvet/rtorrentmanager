package rtorrent.utils;

import org.apache.log4j.Logger;
import winstone.AccessLogger;
import winstone.WinstoneRequest;
import winstone.WinstoneResponse;

/**
 * User: welvet
 * Date: 29.05.2010
 * Time: 18:12:14
 */
public class WebServLogger implements AccessLogger {
    private static Logger log = LoggerSingleton.getLogger();

    public WebServLogger(winstone.WebAppConfiguration webAppConfig, java.util.Map startupArgs) throws java.io.IOException {
    }

    public void log(String s, WinstoneRequest winstoneRequest, WinstoneResponse winstoneResponse) {
        log.info(s + "request: " + winstoneRequest.getRequestURI());
    }

    public void destroy() {

    }
}
