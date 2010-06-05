package rtorrent.test;

import junit.framework.TestCase;
import rtorrent.config.Config;
import rtorrent.config.ConfigManagerImpl;

import java.io.File;

/**
 * User: welvet
 * Date: 05.06.2010
 * Time: 13:03:38
 */
public class ConfigManagerImplTest extends TestCase {
    private static final String TEST = "Test";
    private static final String TEST_FIELD = "TestField";
    private static final String TEST_VALUE = "TestValue";
    private static final File DIR = new File(System.getProperty("java.io.tmpdir"));

    public void testConfig() throws Exception {
        Config config = new Config();
        config.setName(TEST);
        config.setFieldValue(TEST_FIELD, TEST_VALUE);

        ConfigManagerImpl configManager = new ConfigManagerImpl(DIR);
        configManager.saveConfig(config);

        configManager = new ConfigManagerImpl(DIR);

        Config readConfig = configManager.getConfig(config.getName());

        assertEquals(readConfig.getFieldValue(TEST_FIELD), config.getFieldValue(TEST_FIELD));
    }
}
