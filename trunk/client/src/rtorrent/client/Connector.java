package rtorrent.client;

import rtorrent.ConfigSingleton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 21:35:46
 */
public class Connector{

    public Connector() throws IOException {

    }


    public Serializable request(Serializable serializable) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("serv", 4444);
        Message message = new Message();
        message.setLogin(ConfigSingleton.getLogin());
        message.setPass(ConfigSingleton.getPass());
        message.setSerializable(serializable);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(message);
        outputStream.flush();
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        Response response = (Response) inputStream.readObject();
        return response.getSerializable();
    }

    public static void main(String[] args) throws Exception {
        Connector connector = new Connector();
        RequestAction action = new RequestAction();
        action.setName("checkTorrent");
        Serializable serializable = connector.request(action);
        System.out.println(serializable);
    }
}
