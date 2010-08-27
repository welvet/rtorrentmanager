package rtorrent.client;

import rtorrent.notice.client.ClientNotice;

import java.io.Serializable;
import java.util.List;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 22:08:48
 */
public class RequestManager {

    private synchronized Serializable doRequest(Serializable serializable) {
        try {
            Connector connector = new Connector();
            return connector.request(serializable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @return true если остановлен
     */
    public Boolean checkTorrent() {
        RequestAction action = new RequestAction();
        action.setName("checkRtorrent");
        return (Boolean) doRequest(action);
    }

    public void switchTorrent() {
        RequestAction action = new RequestAction();
        action.setName("shitchTorrent");
        doRequest(action);
    }

    public String[] getTorrents() {
        RequestAction action = new RequestAction();
        action.setName("torrentArray");
        return (String[]) doRequest(action);
    }


    public String addTorrent(AddTorrentMessage message) {
        RequestAction action = new RequestAction();
        action.setName("addTorrent");
        action.setParam(message);
        return (String) doRequest(action);
    }

    public List<ClientNotice> getNotices()
    {
        RequestAction action = new RequestAction();
        action.setName("receiveNotices");
        return (List<ClientNotice>) doRequest(action);
    }
}
