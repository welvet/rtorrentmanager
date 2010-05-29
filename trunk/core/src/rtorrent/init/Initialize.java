package rtorrent.init;

import winstone.Launcher;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * User: welvet
 * Date: 29.05.2010
 * Time: 19:31:36
 */
public class Initialize {
    public static void main(String[] args) throws IOException, InterruptedException {
        //        todo REAZIE ME
        
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("accessLoggerClassName", "rtorrent.utils.WebServLogger");
        properties.put("ajp13Port", "-1");
        properties.put("httpPort", "8080");
        properties.put("httpsListenAddress", "127.0.0.1");

        File tempWar = new File(System.getProperty("java.io.tmpdir")+"/rtorrentmanager.war");
        File file = new File(Initialize.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        JarFile jarFile = new JarFile(file);
        ZipEntry entry = jarFile.getEntry("web.war");
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
        System.out.println("s = " + tempWar.getAbsolutePath());
        Launcher.initLogger(properties);
        Launcher winstone = new Launcher(properties);
        while (true)

        {
            Thread.sleep(1000);
        }
    }
}
