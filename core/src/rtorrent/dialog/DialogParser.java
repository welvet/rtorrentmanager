package rtorrent.dialog;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import dialog.CheckField;
import dialog.Dialog;
import dialog.SelectField;
import dialog.TextField;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

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
public class DialogParser {

    public Dialog parse(String name) {
        try {
        InputStream stream = DialogParser.class.getResourceAsStream(name + ".xml");
        DOMParser parser = new DOMParser();

        InputSource inputSource = new InputSource(stream);

        parser.parse(inputSource);
        Document document = parser.getDocument();

        Dialog dialog = new Dialog();

        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        //выставляем глобальные свойства
        dialog.setName(xPath.evaluate("/config/@name", document));
        dialog.setPath(xPath.evaluate("/config/@path", document));

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

        return dialog;
        } catch (Exception e) {
            throw new RuntimeException();
            //todo realize me
        }
    }
}
