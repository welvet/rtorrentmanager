package redstone.xmlrpc;

import redstone.xmlrpc.util.Scgi;

import java.io.*;
import java.net.Socket;


/**
 * nTorrent - A GUI client to administer a rtorrent process
 * over a network connection.
 * <p/>
 * Copyright (C) 2007  Kim Eik
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class XmlRpcSocketClient extends XmlRpcClient {

    private final String host;
    private final int port;
    private Socket connection;
    private long timeOut;
    private static final int CYCLES = 100;

    public XmlRpcSocketClient(String host, int port, long timeOut) throws XmlRpcException {
        this.host = host;
        this.port = port;
        this.timeOut = timeOut;
    }

    public Socket getConnection() {
        return connection;
    }

    @Override
    protected void beginCall(String methodName) throws XmlRpcException {
        try {
            //if(connection == null) each request needs a new socket?
            connection = new Socket(host, port);
        } catch (Exception e) {
            throw new XmlRpcException(e.getMessage(), e);
        }
        super.beginCall(methodName);
    }

    @Override
    protected void endCall() throws XmlRpcException, XmlRpcFault {
        super.endCall();
        try {
            StringBuffer buffer = new StringBuffer(Scgi.make(null, writer.toString()));
            OutputStream output = new BufferedOutputStream(connection.getOutputStream());

            output.write(buffer.toString().getBytes());
            output.flush();
            InputStream input = new BufferedInputStream(connection.getInputStream());

            int c = 0;
            while (c != -1) {
                c = safeRead(input);
                if (c == '\r' && safeRead(input) == '\n')
                    if (safeRead(input) == '\r' && safeRead(input) == '\n')
                        break;
            }

            handleResponse(input);

        } catch (IOException e) {
            throw new XmlRpcException(e.getMessage(), e);
        }
    }

    /**
     * Безопасно читаем сокет
     * Возможно использование Busy spin TODO посмотреть
     *
     * @param input
     * @return
     * @throws java.io.IOException
     */
    private int safeRead(InputStream input) throws IOException {
        for (int i = 0; i < CYCLES; i++) {
            if (input.available() > 0)
                return input.read();
            try {
                Thread.sleep(timeOut / CYCLES);
            } catch (InterruptedException e) {
                throw new IOException();
            }
        }
        throw new IOException();
    }

}
