import com.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Created by daixiaohu on 2018/3/4.
 */
public class Test {
    protected static PoolingHttpClientConnectionManager connectionManager;
    //    static String   ADD_URL = "http://localhost:8090/account/prePrpayTry?userId=849d23a7cb094e039e4dfb50e158611a&orgId=1000&boId=333334&applyDate=2018-03-27&applyTime=2018-03-27";//提前还款试算
//    static String   ADD_URL = "http://139.224.145.135:8090/account/createAccount?userName=dxh&idType=1&idNo=420621199109211877&boId=3333334&mobile=17621621991&productCode=MC1000&orgId=1001&repayPeriod=4&lendAmount=1100.8&loanAmount=1000&lendDate=2017-03-01&lendTime='2018-03-20 15:15:56'&ApplyDate=2017-01-01&applyTime='2018-03-20 15:15:56'";//建账
//static String   ADD_URL = "http://localhost:8090/account/queryPlatEarnings?userId=849d23a7cb094e039e4dfb50e1586cfa&pageNo=1&pageRec=30&startDate=2017-03-22&endDate=2018-03-25&orgId=1000";//平台盈利
//static String   ADD_URL = "http://139.224.145.135:8090/account/accountTrial?userName=lucia陈&idType=1&idNo=310101199002029879&orgId=1002&loanAmount=1000.32&productCode=MC1021&applyDate=2018-01-29&repayPeriod=4&applyTime=2018-01-29";//借款前账务试算
//static String   ADD_URL = "http://139.224.145.135:8090/acc    ount/manualRepay?userName=sunny3&idType=1&idNo=110101198001019971&boId=19870632&orgId=1000&repayType=0&periodNum=1&serialNumber=111&preRepayAmount=1160&applyDate=2018-04-29&applyTime=2018-04-30";//还款
//static String   ADD_URL = "http://localhost:8090/account/queryAccountStatus?userName=sunny&idType=1&idNo=19970701&boId=19870613&orgId=1000";//建账情况查询
//static String   ADD_URL = "http://localhost:8090/account/queryAccountStatus?userName=sunny&idType=1&idNo=19970701&boId=19870613&orgId=1000";//建账情况查询
//static String   ADD_URL = "http://localhost:8090/account/accountUpdate?boId=333334&periodNum=1&orgId=1000&applyUpdateType=7&retCode=1";//账务变更
//    static String ADD_URL = "https://stg.dazhijinke.com/dzjk/request";
    static String ADD_URL = "http://139.224.147.248:8099/controller/invoke";
//    static String ADD_URL = "https://stg.dazhijinke.com/controller/invoke";
    public static void main(String[] args) throws Exception {

//        BigDecimal a = new BigDecimal(100);
//        BigDecimal b = new BigDecimal(0);
//        BigDecimal c= new BigDecimal("33.33");
//        System.out.print(Double.valueOf("0.35005"));
//        BigDecimal perInterest = (a.subtract(c.multiply(b))).multiply(new BigDecimal(Double.valueOf("0.35005"))).setScale(2,BigDecimal.ROUND_HALF_UP);
//        Date date=new Date();
//        //获取月份
//        System.out.println(perInterest);
//        //获取日
////        form.setFirstRepayDate(DateUtils.dateToString(borrowDO.getFirstRepayDate(), DateUtils.SIMPLE_DATE_PATTERN));
//
//
////        BigDecimal invest = new BigDecimal("1.2222222");
////        int totalmonth=3;
//        System.out.print(new BigDecimal("0.35005"));
//       System.out.print("金额"+ invest.divide(new BigDecimal(totalmonth),2,BigDecimal.ROUND_HALF_UP));
       CommonRequest form = new CommonRequest();
        Header header = new Header();
        header.setOrgCode("MAIZI1000");
        header.setOrgPwd(MD5Util.md5("123456"));
        header.setOrgUser("maizi");
        header.setOrgReqno("111123232");
        header.setServiceCode("CapitalCostTrial");
        header.setAgencyCode("nyb_mc001");

        header.setChannelSystemId("AMS");//账户系统
//        header.setTransTypeId("2000");
        header.setKey("ebsSWcldXJRmOMXBLh6M1QLBZ0FEie/jVyWDk2Wd58+MWTnfN/M7vEOPRAOLgWYGsYk+4ZTWh/5OiXPc4Jx/aEg56XdA4lypGJ4sMZP/2GiEU2TIWvXvYcai5hG+0ZAZwkZcjUtkPLD/+DChGza3JvTbLk+ClbQbzfeJtoZKc5c=");
        form.setHeader(header);
        Body body = new Body();
        body.setEncryData("4726749EBE503A696BCAB10760E89D46973BE71B6CE3E1CC41F5B785E3E7716D39406BDE0C7EDF871866646978C95299CB226794F7CEA67AB8C5E7D20A2AF55A0BCE751BEA827AEE3DF1AD82CA9FF568");
        body.setSignData("75628f4b618e1764aff4e5ee657ac3a4");
        form.setBody(body);

        HttpPost httpPost = new HttpPost(ADD_URL);


        HttpClient httpClient = new DefaultHttpClient();
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
        String jsonstr = JSON.toJSONString(form);
//        String jsonstr = "{\"Application\":{\"DecisionArea\":\"RISK\",\"cusInfo\":{\"age\":\"70\",\"loanInner\":\"6000\"},\"SourceCode\":\"A02\"}}";
//        String jsonstr =  "{\"Application\":{\"DecisionArea\":\"RISK\",\"cusInfo\":{\"age\":\"75\",\"sex\":\"0\",\"loanInner\":\"4000\",\"overdueLoanInner\":\"0\",\"overdueMaxPeriodInner\":\"0\",\"idCertification\":\"1\",\"organization\":\"0\",\"job\":\"1\",\"applyLoan\":\"5000\",\"applyPeriod\":\"6\",\"score\":\"680\",\"baiduScore\":\"366\",\"education\":\"1\",\"zxoverdueLoan\":\"0\",\"zxoverdueMaxPeriod\":\"0\",\"zxoverdueMaxLoan\":\"0\",\"mutiLend1\":\"1\",\"mutiLend3\":\"3\",\"mutiLend6\":\"4\"},\"SourceCode\":\"A02\"}}";
        StringEntity se = new StringEntity(jsonstr);
        se.setContentType("application/json");
        se.setContentEncoding("UTF-8");
        httpPost.setEntity(se);

        HttpResponse response = httpClient.execute(httpPost);

        //输出调用结果
        if (response != null && response.getStatusLine().getStatusCode() == 200) {
            String result = EntityUtils.toString(response.getEntity());
            // 生成 JSON 对象
            JSONObject obj = JSONObject.parseObject(result);
            System.out.print(result);
        }

    }

    public static CloseableHttpClient getHttpClient() throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(getConnectionManager()).build();
        return httpClient;
    }

    public static synchronized PoolingHttpClientConnectionManager getConnectionManager() throws Exception {
        if (connectionManager != null) {
            return connectionManager;
        }
        RegistryBuilder<ConnectionSocketFactory> builder = RegistryBuilder.create();
        //注册http的connectionSocketFactory
        ConnectionSocketFactory connectionSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        builder.register("http", connectionSocketFactory);
        Registry<ConnectionSocketFactory> registry = builder.build();
        connectionManager = new PoolingHttpClientConnectionManager(registry);
//        connectionManager = new PoolingHttpClientConnectionManager();
        //最大连接数
        connectionManager.setMaxTotal(10);
        return connectionManager;
    }
}