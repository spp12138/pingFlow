package com.ruoyi.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>文件名: PropsUtil.java</p>  
 * <p>描述: 参数文件工具类.该方法读取prop.properties文件,不加载内存,每次都重新读取</p>  
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
public class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);
    private static String defaultPropFile = "prop.properties";
    /**
     * 加载属性文件
     * @param fileName fileName一定要在class下面及java根目录或者resource跟目录下
     * @return
     */
	public static Properties loadProps(String fileName){
        Properties props = new Properties();
        InputStream is = null;
        try {
            //将资源文件加载为流
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            props.load(is);
            if(is==null){
               throw new FileNotFoundException(fileName+"file is not Found");
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("load properties file filure",e);
        } catch (Exception e) {
        	LOGGER.error("load properties file filure",e);
        }finally {
            if(is !=null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure",e);
                }
            }
        }
        return props;
    }

    /**
     * 获取字符型属性（默认值为空字符串）,默认读取 prop.properties
     * @param props
     * @param key
     * @return
     */
    public static String getString(String key){
    	return getString(loadProps(defaultPropFile),key,"");
    }
    /**
     * 获取字符型属性（默认值为空字符串）,默认读取 prop.properties
     * @param props
     * @param key
     * @return
     */
    public static String getString(String key,String defaultValue){
    	return getString(loadProps(defaultPropFile),key,defaultValue);
    }
    /**
     * 获取字符型属性（默认值为空字符串）
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props,String key){
        return getString(props,key,"");
    }

    /**
     * 获取字符型属性（可制定默认值）
     * @param props
     * @param key
     * @param defaultValue 当文件中无此key对应的则返回defaultValue
     * @return
     */
    public static String getString(Properties props,String key,String defaultValue){
        String value = defaultValue;
        if (props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取数值型属性（默认值为0）
     * @param props
     * @param key
     * @return
     */
    public static int getInt(Properties props,String key){
        return getInt(props,key,0);
    }

    /**
     * 获取数值型属性（可指定默认值）
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(String key){
    	return getInt(loadProps(defaultPropFile),key);
    }
    /**
     * 获取数值型属性（可指定默认值）,默认读取 prop.properties
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(String key,int defaultValue){
    	return getInt(loadProps(defaultPropFile),key,defaultValue);
    }
    
    /**
     * 获取数值型属性（可指定默认值）,默认读取 prop.properties
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Properties props,String key,int defaultValue){
        int value = defaultValue;
        if (props.containsKey(key)){
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * 获取布尔型属性（默认值为false）
     * @param props
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties props,String key){
        return getBoolean(props,key,false);
    }
    /**
     * 获取布尔型属性（默认值为true）,默认读取 prop.properties
     * @param props
     * @param key
     * @return
     */
    public static boolean getBoolean(String key){
    	return getBoolean(loadProps(defaultPropFile),key,true);
    }
    /**
     * 获取布尔型属性（默认值为true）,默认读取 prop.properties
     * @param props
     * @param key
     * @return
     */
    public static boolean getBoolean(String key,Boolean defaultValue){
    	return getBoolean(loadProps(defaultPropFile),key,defaultValue);
    }

    /**
     * 获取布尔型属性（可指定默认值）
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Properties props,String key,Boolean defaultValue){
        boolean value = defaultValue;
        if (props.containsKey(key)){
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}