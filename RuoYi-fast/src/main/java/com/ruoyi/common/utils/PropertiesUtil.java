package com.ruoyi.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 
 * <p>文件名: PropertiesUtil.java</p>  
 * <p>描述: 参数文件工具类.该方法读取esb.properties文件,加载内存,修改取重启服务重新读取</p>  
 * <p>版权: Copyright (c) 2018</p>  
 * <p>公司: 信雅达 Sunyard</p>  
 * @author 桑一平  
 * @date 2019年8月20日
 * @version 1.0  
 * 创建时间:  2019年8月20日14:43:40
 *   
 * 修改历史:   
 * 时    间                          作    者                版    本             描    述   
 * ----------- -------- -------- -------------------------------------- 
 * 2019年8月20日      桑一平                1.0     新建
 *
 */
public class PropertiesUtil {

	private static Properties p = null;

	static{
		p = new PropertiesUtil().ReadESBProperties();
	}

	protected Properties ReadESBProperties() {
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("esb.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static String getValue(String key) {
		try {
			Object properties = p.get(key);
			if (null != properties) {
				return new String(properties.toString().getBytes("ISO-8859-1"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

}
