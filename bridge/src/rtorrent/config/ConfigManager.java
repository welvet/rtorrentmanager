package rtorrent.config;

/**
 * User: welvet
 * Date: 05.06.2010
 * Time: 10:52:10
 */
public interface ConfigManager {
    /**
     * @param name уникальное имя конфига
     * @return конфиг
     */
    public Config getConfig(String name);

    /**
     * Обновить существующий конфиг
      * @param config
     */
    public void saveConfig(Config config);
}
