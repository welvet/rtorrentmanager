package rtorrent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:51:48
 */
public class ConfigSingleton {
    private static String host = "127.0.0.1";
    private static String port = "4444";
    private static String httpPort = "8888";
    private static String login = "";
    private static String pass = "";
    private static Boolean needStop = true;
    private static Saver saver = new Saver(System.getProperty("user.home") + "/" + "rmanager.cfg");

    static {
        List list = saver.load();
        if (list != null) {
            host = (String) list.get(0);
            port = (String) list.get(1);
            httpPort = (String) list.get(2);
            login = (String) list.get(3);
            pass = (String) list.get(4);
            needStop = (Boolean) list.get(5);
        }
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        ConfigSingleton.login = login;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        ConfigSingleton.pass = pass;
    }

    public static Boolean getNeedStop() {
        return needStop;
    }

    public static void setNeedStop(Boolean needStop) {
        ConfigSingleton.needStop = needStop;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        ConfigSingleton.host = host;
    }

    public static void update() {
        List<Serializable> list = new ArrayList<Serializable>();
        list.add(0, host);
        list.add(1, port);
        list.add(2, httpPort);
        list.add(3, login);
        list.add(4, pass);
        list.add(5, needStop);
        saver.save(list);
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        ConfigSingleton.port = port;
    }

    public static String getHttpPort() {
        return httpPort;
    }

    public static void setHttpPort(String httpPort) {
        ConfigSingleton.httpPort = httpPort;
    }
}


