package rtorrent.test;

import junit.framework.TestCase;
import winstone.Launcher;

import java.util.HashMap;
import java.util.Map;

/**
 * User: welvet
 * Date: 18.05.2010
 * Time: 20:51:07
 */
public class WebServerTest extends TestCase {
    public void testStart() throws Exception {
//        todo REAZIE ME
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("accessLoggerClassName", "rtorrent.utils.WebServLogger");
        properties.put("ajp13Port", "-1");
        properties.put("httpPort", "8080");
        properties.put("httpsListenAddress     ", "127.0.0.1");
        properties.put("webroot", "C:/");
        Launcher.initLogger(properties);
        Launcher winstone = new Launcher(properties);
        while (true) {
            Thread.sleep(1000);
        }
    }
}
