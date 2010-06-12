package rtorrent.web;

import org.apache.log4j.Logger;
import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.init.Initialize;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.LoggerSingleton;
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
    private Map<String, Object> properties = new HashMap<String, Object>();
    private Logger log = LoggerSingleton.getLogger();

    public WebServerBuilder() {
        properties.put("accessLoggerClassName", "rtorrent.web.WebServLogger");
        properties.put("ajp13Port", "-1");
        properties.put("useJNDI", "true");
        ConfigManager configManager = (ConfigManager) ContextUtils.lookup("rconfig");
        Config config = configManager.getConfig("server");
        properties.put("httpsListenAddress", config.getFieldValue("host"));
        properties.put("httpPort", config.getFieldValue("port"));
        ClassLoader loader = WebServerBuilder.class.getClassLoader();
        String war = new File(Initialize.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("core.jar", "") + "/web.war").getAbsolutePath();
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

    public Launcher build() {
        try {
            Launcher.initLogger(properties);
            Launcher launcher = new Launcher(properties);
            log.info("Web сервер (" + properties.get("httpsListenAddress") + ":" + properties.get("httpPort") + ") загружен");
            return launcher;
        } catch (IOException e) {
            log.error("Не удалось загрузить web server", e);
        }
        return null;
    }
}
