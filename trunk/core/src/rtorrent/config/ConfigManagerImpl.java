package rtorrent.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * User: welvet
 * Date: 05.06.2010
 * Time: 12:10:19
 */
public class ConfigManagerImpl implements ConfigManager {
    private Set<Config> configs = new HashSet<Config>();
    private File file;
    private static final String EXT = ".cnf";
    private static final String DIR = "configs";

    public ConfigManagerImpl(File dir) {
        //создаем директорию с конфигами
        dir = new File (dir.getAbsolutePath()+ "\\" + DIR);
        dir.mkdir();

        this.file = dir;
    }

    private void save(Config config) throws IOException {
        Properties properties = new Properties();
        properties.putAll(config.getFields());
        //сохраняем конфиг по его имени
        String path = file.getAbsolutePath() + "\\" + config.getName() + EXT;
        FileOutputStream stream = new FileOutputStream(path);
        properties.store(stream, null);
    }

    private Config load(String name) throws IOException {
        Properties properties = new Properties();
        //загружаем конфиг по имени файла
        String path = file.getAbsolutePath() + "\\" + name + EXT;
        FileInputStream stream = new FileInputStream(path);
        properties.load(stream);
        //заполняем поля, настройками
        Map map = new HashMap();
        for (Object s : properties.keySet()) {
            map.put(s, properties.get(s));
        }
        Config config = new Config();
        config.setName(name);
        config.setFields(map);
        //добавляем конфиг
        configs.add(config);
        return config;
    }


    public Config getConfig(String name) {
        for (Config config : configs)
            if (config.getName().equals(name))
                return config;
        //если конфиг найти не удалось, пробуем найти его в файлах
        try {
            return load(name);
        } catch (IOException e) {
            //todo переделать на свое исключение
            throw new RuntimeException();
        }
    }

    public void saveConfig(Config config) {
        configs.add(config);
        try {
            save(config);
        } catch (IOException e) {
            //todo переделать на свое исклчение
            throw new RuntimeException();
        }
    }
}
