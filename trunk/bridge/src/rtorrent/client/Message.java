package rtorrent.client;

import java.io.Serializable;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 21:26:31
 */
public class Message implements Serializable{
    private String login;
    private String pass;
    private Serializable serializable;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Serializable getSerializable() {
        return serializable;
    }

    public void setSerializable(Serializable serializable) {
        this.serializable = serializable;
    }
}
