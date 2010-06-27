package rtorrent.client;

import java.io.Serializable;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 21:28:44
 */
public class Response implements Serializable{
    private Serializable serializable;

    public Serializable getSerializable() {
        return serializable;
    }

    public void setSerializable(Serializable serializable) {
        this.serializable = serializable;
    }
}
