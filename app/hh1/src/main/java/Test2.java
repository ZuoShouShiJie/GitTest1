
import com.CommonUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daixiaohu on 2018/3/26.
 */
public class Test2 {
    public static void main(String[] args) throws ParseException, IOException {
        HttpMethod httpMethod = null;
        try {

            Map<String,String> map = new HashMap<String, String>();
            map.put("userId","849d23a7cb094e039e4dfb50e1586cfa");
            map.put("pageNo","1");
            Set<String> set = map.keySet();
            StringBuffer buffer = new StringBuffer();
            int size =set.size();
            int i=0;
            for (String s:set){
                i++;
                buffer.append(s);
                buffer.append("=");
                buffer.append(map.get(s));
                if (i<size){
                    buffer.append("&");
                }
            }
//            String url  ="http://localhost:8090/account/queryPlatEarnings?"+buffer;

            String url = "http://localhost:8090/account/queryPlatEarnings?userId=849d23a7cb094e039e4dfb50e1586cfa&pageNo=1&pageRec=30&startDate=2017-03-22&endDate=2018-03-25&orgId=1000";//平台盈利
            if (false) {
                PostMethod method = new PostMethod(url);
                httpMethod = method;
            } else {
                GetMethod method = new GetMethod(url);
                httpMethod = method;
            }
            HttpClient client = contructClient();
//            MessageLogUtil.printLog(httpMethod);
            int statusCode = client.executeMethod(httpMethod);
            String  response = httpMethod.getResponseBodyAsString();
            if (statusCode != 200) {
//                LoggerUtil.warn(logger, new Object[]{this.config, "状态码异常,statusCode=", Integer.valueOf(statusCode)});
//                throw new MessagegwException(CommunicationErrorCode.IO_EXCEPTION, "HTTP状态码异常,statusCode=" + statusCode + ",channelSystemId=" + this.config.getChannelSystemId());

            }
            Object result = httpMethod.getResponseBody();
            System.out.print(result);
        } finally {
            httpMethod.releaseConnection();
        }
//        return result;
    }

    static HttpClient contructClient() {
        HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
        httpConnectionManager.setParams(connectionManagerParams);
        HttpClient client = new HttpClient(httpConnectionManager);
//        client.getParams().setConnectionManagerTimeout((long)config.getConnectTimeout());
        DefaultHttpMethodRetryHandler retryhandler = new DefaultHttpMethodRetryHandler(0, false);
        client.getParams().setParameter("http.method.retry-handler", retryhandler);
        return client;
    }



}
