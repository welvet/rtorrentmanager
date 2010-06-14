package rtorrent.tracker.rutracker;

import org.apache.log4j.Logger;
import rtorrent.utils.LoggerSingleton;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 19:15:29
 */
public class RuTrackerSaver {
    private File file;
    private Logger log = LoggerSingleton.getLogger();

    public RuTrackerSaver(File file) {
        this.file = file;
    }

    public void save(Map<String, String> strings) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(strings);
            outputStream.flush();
            outputStream.close();
            fileOutputStream.close();
            log.debug(file + " сохранен");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public Map<String, String> load() {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Map<String, String> stringStringMap = (Map<String, String>) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
            return stringStringMap;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new HashMap<String, String>();
        }
    }


}
