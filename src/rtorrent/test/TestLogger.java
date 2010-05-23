package rtorrent.test;

import junit.framework.TestCase;
import rtorrent.service.RtorrentServiceException;
import rtorrent.utils.LoggerSingleton;

/**
 * User: welvet
 * Date: 19.05.2010
 * Time: 14:56:32
 */
public class TestLogger extends TestCase {
    private org.apache.log4j.Logger logger;

    public void testLogger() throws Exception {
        logger = LoggerSingleton.getLogger();
        logger.trace("Trace");
        logger.debug("Debug");
        logger.info("Info");
        logger.warn("Warn");
        logger.error("Error");
    }

    public void testLogException() throws Exception {
        try {
            throw new RtorrentServiceException("Test exception");
        }
        catch (Exception e) {
        }
    }
}
