package rtorrent.test;

import rtorrent.dialog.DialogParserImpl;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 22:14:16
 */
public class DialogParserTest extends RtorrentTestCase{
    public void testParse() throws Exception {
        DialogParserImpl parser = new DialogParserImpl();
        parser.parse("test");
    }
}
