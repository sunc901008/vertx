package openscanner.cc.utils;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;

import java.util.regex.Pattern;

/**
 * @author sunc
 * @date 2016年3月23日
 * @description : 常量和通用判断功能
 */
public class Commons {

	//重置密码
	public static final String PWDRESET = "passwordReset";
	//激活账号
	public static final String REGISTACTIVE = "registActive";
	//域名
	public static final String DOMAIN = "192.168.0.244:8088";
	
	//过期时间(分钟)
	public static final Long expired = 20L;
	
	public static boolean isEmpty(Object o){
		if(o == null){
			return true;
		}else if(o.toString().isEmpty()){
			return true;
		}
		return false;
	}
	
	private static final String phonePattern = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	public static boolean isPhoneNumber(String mobile){
		if(Commons.isEmpty(mobile)){
			 return false;
		 }
		 Pattern p = Pattern.compile(phonePattern);
		 return p.matcher(mobile).matches();
	}
	
	public static boolean isNumeric(Object object){
		String str = object.toString();
		if(isEmpty(str)){
			return false;
		}
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches();
	 }

	public static JsonObject mapToJson(MultiMap map){
		JsonObject json = new JsonObject();
		map.names().forEach(key -> json.put(key, map.get(key)));
		return json;
	}
}
