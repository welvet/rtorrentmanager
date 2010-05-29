package rtorrent.web;

import rtorrent.init.Initialize;
import rtorrent.utils.UtilException;
import winstone.Launcher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: welvet
 * Date: 30.05.2010
 * Time: 0:41:03
 */
public class WebServerBuilder {
    Map<String, String> properties = new HashMap<String, String>();

    public WebServerBuilder() {
        properties.put("accessLoggerClassName", "rtorrent.web.WebServLogger");
        properties.put("ajp13Port", "-1");
        properties.put("httpPort", "8080");
        properties.put("httpsListenAddress", "0.0.0.0");
        String war = new File(Initialize.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/../web.war").getAbsolutePath();
        properties.put("warfile", war);
    }

    /**
     * Порт сервера
     *
     * @param port
     */
    public void setPort(String port) {
        properties.put("httpPort", port);
    }

    /**
     * Слушаемый адрес
     *
     * @param host
     */
    public void setHost(String host) {
        properties.put("httpsListenAddress", host);
    }

    /**
     * Абсолютный путь к war
     *
     * @param war
     */
    public void setWar(String war) {
        properties.put("warfile", war);
    }

    public Launcher build() throws UtilException {
        try {
            Launcher.initLogger(properties);
            return new Launcher(properties);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }
}
