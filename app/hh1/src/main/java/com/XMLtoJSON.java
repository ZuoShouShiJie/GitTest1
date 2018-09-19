package com;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * Created by wxn on 2018/5/27
 */
public class    XMLtoJSON {


    public static String json2Xml(JSONObject json, String rootName){
        String sXml = "";
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled(false);
        xmlSerializer.setRootName(rootName);
        String sContent = xmlSerializer.write(json);
        try {
            Document docCon = DocumentHelper.parseText(sContent);
            sXml = docCon.getRootElement().asXML();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return sXml;
    }


    @SuppressWarnings("unused")
    public static String xml2JSON(String xml){
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.read(xml).toString();
    }

}
