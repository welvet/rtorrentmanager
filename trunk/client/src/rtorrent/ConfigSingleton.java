package rtorrent;

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


