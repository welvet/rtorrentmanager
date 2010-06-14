package rtorrent.utils;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.*;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 2:14:19
 */
public class XpathUtils {
    private Document document;
    private XPath xPath;
    private File temp;

    public XpathUtils(InputStream stream) throws Exception {
        DOMParser parser = new DOMParser();
        XPathFactory factory = XPathFactory.newInstance();
        xPath = factory.newXPath();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        temp = File.createTempFile("parser", ".tmp");
        BufferedWriter out = new BufferedWriter(new FileWriter(temp));

        temp.deleteOnExit();
        while (true) {
            String ln = reader.readLine();
            if (ln == null)
                break;
            out.write(ln);
        }
        out.close();
        parser.parse(temp.getAbsolutePath());
        document = parser.getDocument();
    }

    public String doXPath(String expression) throws Exception {
        return xPath.evaluate(expression, document);
    }

    public String getFile() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(temp));
        StringBuilder builder = new StringBuilder();
        while (true) {
            String ln = reader.readLine();
            if (ln == null)
                break;
            builder.append(ln);
        }
        return builder.toString();
    }
}
