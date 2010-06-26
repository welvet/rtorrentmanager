package rtorrent.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 26.06.2010
 * Time: 19:11:28
 */
public class Saver {
    private File file;
    private Logger log = LoggerSingleton.getLogger();

    public Saver(String file) {
        File dir = (File) ContextUtils.lookup("workdir");
        this.file = new File(dir.getAbsolutePath() + "/" + file);
    }

    public List load() {
        List list = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            list = (List) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list != null ? list : new ArrayList();
    }

    public synchronized void save(List<? extends Serializable> list) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(list);
            outputStream.flush();
            outputStream.close();
            fileOutputStream.close();
            log.debug(file + " сохранен");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
