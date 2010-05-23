/**
 *   nTorrent - A GUI client to administer a rtorrent process 
 *   over a network connection.
 *
 *   Copyright (C) 2007  Kim Eik
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ntorrent.io.xmlrpc;

import ntorrent.io.rtorrent.*;
import ntorrent.io.rtorrent.System;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcProxy;
import redstone.xmlrpc.XmlRpcSocketClient;


/**
 * This class handles an xmlrpc connection.
 *
 * @author Kim Eik
 */
public class XmlRpcConnection {
    private XmlRpcClient client;
    private String host;
    private Integer port;
    private long timeOut;

    public XmlRpcConnection(String host, Integer port) throws XmlRpcException {
        this(host, port, 5);
    }

    public XmlRpcConnection(String host, Integer port, long timeOutSec) throws XmlRpcException {
        this.host = host;
        this.port = port;
        this.timeOut = timeOutSec * 1000;  // mils > second
        connect();
    }

    private void connect() throws XmlRpcException {
        try {
            client = new XmlRpcSocketClient(host, port, timeOut);
        } catch (Exception e) {
            throw new XmlRpcException(e.getMessage());
        }
    }

    public void reconnect() {
        connect();
    }

    public void disconnect() {
        client = null;
    }

    public boolean isConnected() {
        try {
            if (getSystemClient().pid() > 0)
                return true;
            else return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    public XmlRpcClient getClient() {
        return client;
    }

    public System getSystemClient() {
        return (System) XmlRpcProxy.createProxy("system", new Class[]{System.class}, client);
    }

    public Download getDownloadClient() {
        return (Download) XmlRpcProxy.createProxy("d", new Class[]{Download.class}, client);
    }

    public Global getGlobalClient() {
        return (Global) XmlRpcProxy.createProxy("", new Class[]{Global.class}, client);
    }

    public PeerConnection getPeerConnectionClient() {
        return (PeerConnection) XmlRpcProxy.createProxy("p", new Class[]{PeerConnection.class}, client);
    }

    public File getFileClient() {
        return (File) XmlRpcProxy.createProxy("f", new Class[]{File.class}, client);
    }

    public Tracker getTrackerClient() {
        return (Tracker) XmlRpcProxy.createProxy("t", new Class[]{Tracker.class}, client);
    }

}
