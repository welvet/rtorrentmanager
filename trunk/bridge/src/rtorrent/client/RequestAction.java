package rtorrent.client;

import java.io.Serializable;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 21:57:26
 */
public class RequestAction implements Serializable{
    private String name;
    private Object param;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
