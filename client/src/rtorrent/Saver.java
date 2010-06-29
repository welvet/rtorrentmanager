package rtorrent;

import java.io.*;
import java.util.List;

/**
 * User: welvet
 * Date: 26.06.2010
 * Time: 19:11:28
 */
public class Saver {
    private File file;

    public Saver(String file) {
        this.file = new File(file);
    }

    public List load() {
        List list = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            list = (List) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
        } catch (Exception ignored) {

        }
        return list;
    }

    public synchronized void save(List<? extends Serializable> list) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(list);
            outputStream.flush();
            outputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}