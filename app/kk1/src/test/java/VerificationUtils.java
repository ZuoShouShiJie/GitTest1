

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VerificationUtils {/*
	public static Jedis jedis = new Jedis("r-zm0cb150cb4e4314.redis.rds.aliyuncs.com", 6379);//定义jedis对象，用来操作redis

	*//**
	 * 用于登录接口redis存储
	 * @param user
	 * @return 返回一个token给客户端
	 *//*
	public static Map<String, String> setUserToken(User user) {
		jedis.auth("maiziJF2017");
		Map<String, String> resultMap = new HashMap<>();//定义一个HashMap，放入传回给客户端的数据和一个token
		Map<String, String> jedisMap = new HashMap<>();//定义一个HashMap，放入我们想存储的数据
		Date date = new Date();//获取当前时间,用于第二次登录时候进行时间比对
		String token = UuidUtils.compressedUuid();//定义一个token字符串
		jedisMap.put("accountPwd", user.getPwd().toString());	//记录登录密码
		jedisMap.put("accountName", user.getUsername().toString());//记录登录用户名,把需要的信息存储在jedisMap中
		jedisMap.put("signTime", DateUtils.dateToString("yyyy-MM-dd HH:mm:ss",date));//记录登录时间
		jedis.hmset(token, jedisMap);
		jedis.expire(token, 6000);//定义token的失效时间10分钟
		resultMap.put("token", token);//把token放入到resultMap（resultMap可能还有其他客户端想要的数据，token只是其中一个）中，返回给客户端。
		return resultMap;
	}

	*//**
	 * 用于接口访问token验证
	 * @param token  3.token验证不通过,跳转到登录页面 4.token验证通过
	 * @return
	 *//*
	public static String verificationToken(String token) {
		Date date = new Date();//获取当前时间,用于第二次登录时候进行时间比对,如果超过10分钟,则从新生成token
		//根据token键获取用户上一次登录时间,进行时间比对(redis失效时间为10分钟)
		if(null != token || !"".equals(token)){
			Map<String, String> userMap = jedis.hgetAll(token);
			String lastTime = userMap.get("signTime");//获取上一次登录时间
			//上次登录时间和当前时间进行比对,超过10分钟为,跳转到登录页面
//			String lastTime = "2018-01-02 15:15:15";	//测试用例
			if(null == lastTime || "".equals(lastTime)){
				return "3";
			}
			long sec = 0;
			try {
				sec = DateUtils.diffSec(lastTime);	//日期与当前时间相比，获取相差秒数
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (sec>6000){	//超过10分钟为,跳转到登录页面
				return "3";
			}
		}else {	 //如果token为null或者空,跳转到登录页面
			return "3";
		}
		return "4";
	}
*/
}