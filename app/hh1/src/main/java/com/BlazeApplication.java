package com;


//import com.risk.common.utils.XMLtoJSON;
//import j.m.XMap;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by wxn on 2018/5/26
 */
public class BlazeApplication {

    //定义blaze 参数模型 xml 节点名称
    public static final String APPLICATION = "Application";
    public static final String INPUTOBJECT = "InputObject";
//
    public static final  String DECISION_AREA = "@DecisionArea";
    public static final String SOURCE_CODE = "@SourceCode";

    public static final String CALL_NODE = "@CallNode";
    public static final String NODE_STATUS = "@NodeStatus";
    public static final String REQUEST_UUID = "@RequestUUID";
    public static final String IS_AUTH = "@IS_AUTH";


    public static final  String DECISION_AREAVALUE = "RISK";
    public static final  String MZ_DB = "MZ_DB";

    public static final String NODE_STATUS_PARAM = "0";
    public static final String NODE_STATUS_GATHERPARAM = "1";
    public static final String NODE_STATUS_RESULT = "2";


   public static final String BLAZE_PARAM_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
           "\n" +
           "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:pac=\"http://package.name.in.reverse.order\">  \n" +
           "  <soapenv:Header/>  \n" +
           "  <soapenv:Body> \n" +
           "    <pac:invokeExternalMain> \n" +
           "      <arg0> <![CDATA[";


    public static final String BLAZE_PARAM_TAIL = "]]> </arg0> \n" +
            "    </pac:invokeExternalMain> \n" +
            "  </soapenv:Body> \n" +
            "</soapenv:Envelope>";


    public static final String BLAZE_DCIN_HEAD= "<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:invokeExternalMainResponse xmlns:ns2=\"http://package.name.in.reverse.order\"><return>";
    public static final String BLAZE_DCIN_TAIL= "</return></ns2:invokeExternalMainResponse></S:Body></S:Envelope>";



    public static void main(String[] args) {
        Map application = new HashMap();
        Map map2 = new HashMap();
        map2.put(BlazeApplication.DECISION_AREA,BlazeApplication.DECISION_AREAVALUE);
        map2.put(BlazeApplication.SOURCE_CODE,"MZJK");
        map2.put(BlazeApplication.CALL_NODE,"1");
        map2.put(BlazeApplication.NODE_STATUS,"0");
        application.put(BlazeApplication.APPLICATION,map2);
//        System.out.println(application.toPrettyXML());
        String param = XMLtoJSON.json2Xml(JSONObject.fromObject(application),"root");
        System.out.println(param);
        param = param.substring(6,param.length()-7);
        StringBuilder blazeParam = new StringBuilder();
        blazeParam
                .append(BlazeApplication.BLAZE_PARAM_HEAD)
                .append(param)
                .append(BlazeApplication.BLAZE_PARAM_TAIL);
//        System.out.println(blazeParam.toString());
    }


//    public static void main(String[] args) {
//        String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                "\n" +
//                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:pac=\"http://package.name.in.reverse.order\">  \n" +
//                "  <soapenv:Header/>  \n" +
//                "  <soapenv:Body> \n" +
//                "    <pac:invokeExternalMain> \n" +
//                "      <!--Optional:-->  \n" +
//                "      <arg0> <![CDATA[<Application DecisionArea=\"RISK\" SourceCode=\"QD0200A001\" SPID=\"\" ApplicationID=\"\" CallNode=\"\" StrategyRandomDigit1=\"-99999\" StrategyRandomDigit2=\"-99999\" StrategyRandomDigit3=\"-99999\" StrategyRandomDigit4=\"-99999\">\n" +
//                "     \t\n" +
//                "<InputObject>\n" +
//                "        <MZ_DB DB_BAS_NAME=\"5000\" DB_BAS_ID_NUM=\"220591198802021234\"></MZ_DB>\n" +
//                "        <AF_TD EX_AF_TD_SCORE=\"50\" EX_AF_TD_7D_PLATEFORM_CNT=\"1\"></AF_TD>\n" +
//                "    </InputObject>  \n" +
//                " </Application>]]> </arg0> \n" +
//                "    </pac:invokeExternalMain> \n" +
//                "  </soapenv:Body> \n" +
//                "</soapenv:Envelope>";
//        int i = result.indexOf("<![CDATA[")+9;
//        int num = result.indexOf("]]>");
//        String applcationResult = result.substring(i,num);
//        StringBuilder str = new StringBuilder();
//        str.append("<root>").append(applcationResult).append("</root>");
//        JSONObject jsonObject = JSONObject.fromObject(XMLtoJSON.xml2JSON(str.toString()));
//        Set<Map.Entry> set = jsonObject.getJSONObject(APPLICATION).getJSONObject("InputObject").entrySet();
////         while (set.stream().iterator().hasNext()) {
////             Map.Entry s = set.stream().iterator().next();
////             System.out.println("key:"+s.getKey());
////             System.out.println("value:"+s.getValue());
////         }
//
//        Iterator<Map.Entry> it = set.iterator();
//        while (it.hasNext()) {
//            Map.Entry s = it.next();
//            System.out.println("key:"+s.getKey());
//            System.out.println("value:"+s.getValue());
//        }
//        System.out.println(jsonObject);
//    }


//    public static void main(String[] args) {
//        Map map = new HashMap();
//        Map map2 = new HashMap();
//        map2.put(DECISION_AREA,"Risk");
//        map2.put(SOURCE_CODE,"MYFQ");
//        map2.put(CALL_NODE,"1");
//        map2.put(NODE_STATUS,"1");
//
//        Map map4 = new HashMap();
//        map4.put("@EX_AF_TD_7D_PLATEFORM_CNT","1");
//        map4.put("@EX_AF_TD_SCORE","5");
//        Map map5 = new HashMap();
//        map5.put("@DB_BAS_ID_NUM","220591198802021234");
//        map5.put("@DB_BAS_NAME","5000");
//        Map map3 = new HashMap();
//        map3.put("AF_TD",map4);
//        map3.put("MZ_DB",map4);
//        map2.put("InputObject",map3);
//        map.put(APPLICATION,map2);
//
//        System.out.println(XMLtoJSON.json2Xml(JSONObject.fromObject(map),"root"));
//
//        String st = "{\"Application\": {\n" +
//                "          \"@CallNode\": \"1\",\n" +
//                "          \"@DecisionArea\": \"Risk\",\n" +
//                "          \"@NodeStatus\": \"1\",\n" +
//                "          \"@SourceCode\": \"MYFQ\",\n" +
//                "          \"InputObject\":           {\n" +
//                "                    \"AF_TD\":                     {\n" +
//                "                              \"@EX_AF_TD_7D_PLATEFORM_CNT\": \"1\",\n" +
//                "                              \"@EX_AF_TD_SCORE\": \"5\"\n" +
//                "                    },\n" +
//                "                    \"MZ_DB\":                     {\n" +
//                "                              \"@EX_AF_TD_7D_PLATEFORM_CNT\": \"1\",\n" +
//                "                              \"@EX_AF_TD_SCORE\": \"5\"\n" +
//                "                    }\n" +
//                "          }\n" +
//                "}}";
//        JSONObject jsonObject = JSONObject.fromObject(st);
//        System.out.println(jsonObject);
//
////        String st = "<Application CallNode=\"1\" DecisionArea=\"Risk\" NodeStatus=\"1\" SourceCode=\"MYFQ\"/>";
//
////        System.out.println(XMLtoJSON.xml2JSON("<root><Application CallNode=\"1\" DecisionArea=\"Risk\" NodeStatus=\"1\" SourceCode=\"MYFQ\"/></root>"));
//    }


}
