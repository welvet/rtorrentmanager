package rtorrent.test;

import rtorrent.config.Config;
import rtorrent.config.ConfigManagerImpl;

import javax.naming.InitialContext;

/**
 * User: welvet
 * Date: 05.06.2010
 * Time: 13:03:38
 */
public class ConfigManagerImplTest extends RtorrentTestCase {
    private static final String TEST = "Test";
    private static final String TEST_FIELD = "TestField";
    private static final String TEST_VALUE = "TestValue";


    public void testConfig() throws Exception {
        Config config = new Config();
        config.setName(TEST);
        config.setFieldValue(TEST_FIELD, TEST_VALUE);

        configManager.saveConfig(config);

        configManager = new ConfigManagerImpl(dir);

        Config readConfig = configManager.getConfig(config.getName());

        assertEquals(readConfig.getFieldValue(TEST_FIELD), config.getFieldValue(TEST_FIELD));
    }

    public void testContext() throws Exception {
        ConfigManagerImpl configManager = new ConfigManagerImpl(dir);
        configManager.bindContext();

        InitialContext context = new InitialContext();

        ConfigManagerImpl manager = (ConfigManagerImpl) context.lookup("rconfig");
        assertNotNull(manager);
    }
}
