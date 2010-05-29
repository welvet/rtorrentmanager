package rtorrent.web;

import rtorrent.init.Initialize;
import rtorrent.utils.UtilException;
import winstone.Launcher;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * User: welvet
 * Date: 30.05.2010
 * Time: 0:41:03
 */
public class WebServerBuilder {
    Map<String, String> properties = new HashMap<String, String>();
    private String path;
    private String entryName;
    private String tempPathName;
    private Boolean needUnJar;

    public WebServerBuilder() {
        properties.put("accessLoggerClassName", "rtorrent.web.WebServLogger");
        properties.put("ajp13Port", "-1");
        properties.put("httpPort", "8080");
        properties.put("httpsListenAddress", "0.0.0.0");
        path = Initialize.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        entryName = "web.war";
        tempPathName = System.getProperty("java.io.tmpdir") + "/rtorrentmanager.war";
        needUnJar = true;
    }

    /**
     * Порт сервера
     *
     * @param port
     */
    public void setPort(String port) {
        properties.put("httpPort", port);
    }

    /**
     * Слушаемый адрес
     *
     * @param host
     */
    public void setHost(String host) {
        properties.put("httpsListenAddress", host);
    }

    /**
     * Путь к jar файлу
     *
     * @param jar
     */
    public void setJar(String jar) {
        path = jar;
    }

    /**
     * Относительный путь к war файлу, если он находится в архиве jar
     * Либо абсолютный, если он распакован
     *
     * @param war
     */
    public void setWar(String war) {
        entryName = war;
    }

    /**
     * Нужно ли извлекать war из jar архива
     *
     * @param b
     */
    public void setNeedUnJar(Boolean b) {
        needUnJar = b;
    }

    private void unJarWar() throws IOException {
        File tempWar = new File(tempPathName);
        File file = new File(path);

        JarFile jarFile = new JarFile(file);

        ZipEntry entry = jarFile.getEntry(entryName);
        InputStream in =
                new BufferedInputStream(jarFile.getInputStream(entry));
        OutputStream out =
                new BufferedOutputStream(new FileOutputStream(tempWar));
        byte[] buffer = new byte[2048];
        for (; ;) {
            int nBytes = in.read(buffer);
            if (nBytes <= 0) break;
            out.write(buffer, 0, nBytes);
        }
        out.flush();
        out.close();
        in.close();

        properties.put("warfile", tempWar.getAbsolutePath());
    }

    public Launcher build() throws UtilException {
        try {
            if (needUnJar)
                unJarWar();
            else
                properties.put("warfile", entryName);
            Launcher.initLogger(properties);
            return new Launcher(properties);
        } catch (IOException e) {
            throw new UtilException(e);
        }
    }
}
