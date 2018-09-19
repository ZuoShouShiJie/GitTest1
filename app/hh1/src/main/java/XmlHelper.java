/*
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

public final class XmlHelper {
    private XmlHelper() {
    }

    public static Map<String, String> convertPrototype(String messagePrototype) throws DocumentException {
        List<Element> fields = getFields(messagePrototype, "map");
        Map<String, String> paramMap = new LinkedHashMap();
        Iterator i$ = fields.iterator();

        while(i$.hasNext()) {
            Element field = (Element)i$.next();
            String fieldName = field.attributeValue("name");
            String fieldValue = field.getText();
            paramMap.put(fieldName, fieldValue);
        }

        return paramMap;
    }

    public static Map<String, String> getParams(Element paramsElement) {
        LinkedHashMap<String, String> params = new LinkedHashMap();
        List<Element> paramElements = children(paramsElement, "param");
        Iterator i$ = paramElements.iterator();

        while(i$.hasNext()) {
            Element param = (Element)i$.next();
            String text = param.getText();
            params.put(param.attributeValue("name"), text);
        }

        return params;
    }

    public static List<Element> getFields(String xml, String node) throws DocumentException {
        Element root = getField(xml);
        return children(root, node);
    }

    public static List<Element> getFields(String xml, String node, String subNode) throws DocumentException {
        Element root = getField(xml);
        Element nodeElement = child(root, node);
        return children(nodeElement, subNode);
    }

    public static Element getField(String xml) throws DocumentException {
        StringReader stringReader = null;

        Element var4;
        try {
            stringReader = new StringReader(xml);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(stringReader);
            var4 = doc.getRootElement();
        } finally {
            IOUtils.closeQuietly(stringReader);
        }

        return var4;
    }

    public static Element child(Element element, String name) {
        return element.element(new QName(name, element.getNamespace()));
    }

    public static Element descendant(Element element, String xPath) {
        if(element != null && xPath != null && !xPath.trim().isEmpty()) {
            String[] paths = xPath.split("/");
            Element tempElement = element;
            String[] arr$ = paths;
            int len$ = paths.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String nodeName = arr$[i$];
                tempElement = child(tempElement, nodeName);
            }

            return tempElement;
        } else {
            return null;
        }
    }

    public static List<Element> children(Element element, String name) {
        return element.elements(new QName(name, element.getNamespace()));
    }

    public static String elementAsString(Element element, String name) {
        return element.elementTextTrim(new QName(name, element.getNamespace()));
    }

    public static int elementAsInteger(Element element, String name) {
        String text = elementAsString(element, name);
        return text == null?0:Integer.parseInt(text);
    }

    public static boolean elementAsBoolean(Element element, String name) {
        String text = elementAsString(element, name);
        return text == null?false:Boolean.valueOf(text).booleanValue();
    }
}
*/
