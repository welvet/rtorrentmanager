package rtorrent.dialog;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import dialog.*;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import rtorrent.utils.BindContext;
import rtorrent.utils.InContext;
import rtorrent.utils.LoggerSingleton;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 09.06.2010
 * Time: 22:00:02
 */
public class DialogParserImpl implements DialogParser, InContext {
    private Logger logger = LoggerSingleton.getLogger();

    public Dialog parse(String name) {
        try {
            InputStream stream = DialogParserImpl.class.getResourceAsStream(name + ".xml");
            DOMParser parser = new DOMParser();

            InputSource inputSource = new InputSource(stream);

            parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            parser.parse(inputSource);

            Document document = parser.getDocument();

            Dialog dialog = new Dialog();

            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath = factory.newXPath();

            //выставляем глобальные свойства
            dialog.setName(xPath.evaluate("/config/@title", document));

            Document tempDocument = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();


            //парсим тег value
            NodeList list = (NodeList) xPath.evaluate("//value", document, XPathConstants.NODESET);
            for (Integer i = 0; i < list.getLength(); i++) {
                Node tempRoot = tempDocument.importNode(list.item(i), true);
                tempDocument.appendChild(tempRoot);
                TextField field = new TextField();
                field.setFieldName(xPath.evaluate("/value/@name", tempDocument));
                field.setFieldText(xPath.evaluate("/value/@text", tempDocument));
                field.setFieldValue(xPath.evaluate("/value/@default", tempDocument));
                field.setFieldDescription(xPath.evaluate("/value/@description", tempDocument));
                dialog.addField(field);
                tempDocument.removeChild(tempRoot);
            }

            //парсим тег checkbox
            list = (NodeList) xPath.evaluate("//checkbox", document, XPathConstants.NODESET);
            for (Integer i = 0; i < list.getLength(); i++) {
                Node tempRoot = tempDocument.importNode(list.item(i), true);
                tempDocument.appendChild(tempRoot);
                CheckField field = new CheckField();
                field.setFieldName(xPath.evaluate("/checkbox/@name", tempDocument));
                field.setFieldText(xPath.evaluate("/checkbox/@text", tempDocument));
                String value = xPath.evaluate("/checkbox/@default", tempDocument);
                Boolean boleanValue = value.equals("true");
                field.setFieldValue(boleanValue);
                field.setFieldDescription(xPath.evaluate("/checkbox/@description", tempDocument));
                dialog.addField(field);
                tempDocument.removeChild(tempRoot);
            }

            //парсим тег select
            list = (NodeList) xPath.evaluate("//select", document, XPathConstants.NODESET);
            for (Integer i = 0; i < list.getLength(); i++) {
                Node tempRoot = tempDocument.importNode(list.item(i), true);
                tempDocument.appendChild(tempRoot);
                SelectField field = new SelectField();
                field.setFieldName(xPath.evaluate("/select/@name", tempDocument));
                field.setFieldText(xPath.evaluate("/select/@text", tempDocument));
                field.setFieldValue(xPath.evaluate("/select/@default", tempDocument));
                field.setFieldDescription(xPath.evaluate("/select/@description", tempDocument));
                //устанавливаем значения дочерних элементов
                NodeList childNodes = (NodeList) xPath.evaluate("/select/option", tempDocument, XPathConstants.NODESET);
                List<String> optionList = new ArrayList<String>();
                for (Integer j = 0; j < childNodes.getLength(); j++) {
                    optionList.add(childNodes.item(j).getTextContent());
                }
                field.setFieldValues(optionList);

                dialog.addField(field);
                tempDocument.removeChild(tempRoot);
            }

            logger.debug("Диалог с именем " + name + " загружен");
            return dialog;
        } catch (Exception e) {
            logger.error("Диалог с именем " + name + " не существует");
            throw new RuntimeException();
        }
    }

    public void bindContext() {
        BindContext.bind("rdialog", this);
    }
}
