package rtorrent;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 13:51:48
 */
public class ConfigSingleton {
    private static String addres = "http://127.0.0.1:8888";
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

    public static String getAddres() {
        return addres;
    }

    public static void setAddres(String addres) {
        ConfigSingleton.addres = addres;
    }

    public static void update() {
        
    }
}
