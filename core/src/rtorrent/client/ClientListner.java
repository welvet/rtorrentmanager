package rtorrent.client;

import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.utils.ContextUtils;
import rtorrent.utils.LoggerSingleton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 21:09:51
 */
public class ClientListner extends Thread {
    private ClientRequestManager manager = new ClientRequestManager();
    private ServerSocket serverSocket;
    private String login = "";
    private String pass = "";
    private Boolean use;

    public ClientListner() {
        try {
            ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
            Config config = manager.getConfig("client");
            use = (Boolean) config.getFieldValue("useClient");
            if (use) {
                login = (String) config.getFieldValue("login");
                pass = (String) config.getFieldValue("pass");
                serverSocket = new ServerSocket(new Integer((String) config.getFieldValue("clientPort")), 0, Inet4Address.getByName((String) config.getFieldValue("clientHost")));
            }
        } catch (IOException e) {
            LoggerSingleton.getLogger().warn(e);
            System.exit(0);
        }
    }

    @Override
    public void run() {
        if (use)
            while (true) {
                try {
                    Socket socket = serverSocket.accept();

                    if (socket == null) {
                        Thread.sleep(100L);
                        continue;
                    }

                    ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
                    Message message = (Message) stream.readObject();
                    if (message.getLogin().equals(login) && message.getPass().equals(pass)) {
                        Serializable serializable = parseResponse(message.getSerializable());
                        Response response = new Response();
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        response.setSerializable(serializable);
                        outputStream.writeObject(response);
                        outputStream.flush();
                    }
                    socket.close();
                } catch (NullPointerException e) {
                    LoggerSingleton.getLogger().error(e);
                } catch (Exception e) {
                    LoggerSingleton.getLogger().warn(e);
                }
            }
    }

    private Serializable parseResponse(Serializable serializable) {
        return manager.manage(serializable);
    }
}
