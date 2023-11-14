package com.ruoyi.common.utils;


/**
 * 
 * <p>文件名: CastUtil.java</p>  
 * <p>描述: 数据转型工具类</p>  
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
public class CastUtil {
    /** 
    * @Description: 转为String类型
    * @Param: [obj] 
    * @return: java.lang.String 如果参数为null则转为空字符串
    */ 
    public static String castString(Object obj){
        return CastUtil.castString(obj,"");
    }

    /** 
    * @Description: 转为String类型（提供默认值）
    * @Param: [obj, defaultValue] 将obj转为string，如果obj为null则返回default
    * @return: String
    */ 
    public static String castString(Object obj,String defaultValue){
        return obj!=null?String.valueOf(obj):defaultValue;
    }

    /** 
    * @Description: 转为double类型，如果为null或者空字符串或者格式不对则返回0
    * @Param: [obj] 
    * @return: String
    */ 
    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj,0);
    }

    /** 
    * @Description: 转为double类型 ，如果obj为null或者空字符串或者格式不对则返回defaultValue
    * @Param: [obj, defaultValue] 
    * @return: String obj为null或者空字符串或者格式不对返回defaultValue
    */ 
    public static double castDouble(Object obj,double defaultValue){
        double value = defaultValue;  //声明结果，把默认值赋给结果
        if (obj!=null){   //判断是否为null
            String strValue = castString(obj);  //转换为String
            if (StringUtils.isNotEmpty(strValue)){   //判断字符串是否为空（是否为空只能判断字符串，不能判断Object）
                try{
                    value = Double.parseDouble(strValue);  //不为空则把值赋给value
                }catch (NumberFormatException e){
                    value = defaultValue;  //格式不对把默认值赋给value
                }

            }
        }
        return value;
    }

    /**
     * 转为long型，如果obj为null或者空字符串或者格式不对则返回0
     * @param obj
     * @return
     */
    public static long castLong(Object obj){
        return CastUtil.castLong(obj,0);
    }

    /**
     * 转为long型（提供默认数值），如果obj为null或者空字符串或者格式不对则返回defaultValue
     * @param obj
     * @param defaultValue
     * @return obj为null或者空字符串或者格式不对返回defaultValue
     */
    public static long castLong(Object obj,long defaultValue){
        long value = defaultValue;  //声明结果，把默认值赋给结果
        if (obj!=null){   //判断是否为null
            String strValue = castString(obj);  //转换为String
            if (StringUtils.isNotEmpty(strValue)){   //判断字符串是否为空（是否为空只能判断字符串，不能判断Object）
                try{
                    value = Long.parseLong(strValue);  //不为空则把值赋给value
                }catch (NumberFormatException e){
                    value = defaultValue;  //格式不对把默认值赋给value
                }

            }
        }
        return value;
    }

    /**
     * 转为int型
     * @param obj
     * @return 如果obj为null或者空字符串或者格式不对则返回0
     */
    public static int castInt(Object obj){
        return CastUtil.castInt(obj,0);
    }

    /**
     * 转为int型(提供默认值)
     * @param obj
     * @param defaultValue
     * @return 如果obj为null或者空字符串或者格式不对则返回defaultValue
     */
    public static int castInt(Object obj,int defaultValue){
        int value = defaultValue;  //声明结果，把默认值赋给结果
        if (obj!=null){   //判断是否为null
            String strValue = castString(obj);  //转换为String
            if (StringUtils.isNotEmpty(strValue)){   //判断字符串是否为空（是否为空只能判断字符串，不能判断Object）
                try{
                    value = Integer.parseInt(strValue);  //不为空则把值赋给value
                }catch (NumberFormatException e){
                    value = defaultValue;  //格式不对把默认值赋给value
                }

            }
        }
        return value;
    }

    /**
     * 转为boolean型，不是true的返回为false
     * @param obj
     * @return
     */
    public static boolean castBoolean(Object obj){
        return CastUtil.castBoolean(obj,false);
    }


    /**
     * 转为boolean型（提供默认值）
     * @param obj
     * @param defaultValue
     * @return
     */
    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean value = defaultValue;
        if (obj!=null){  //为null则返回默认值
            value = Boolean.parseBoolean(castString(obj));  //底层会把字符串和true对比，所以不用判断是否为空字符串
        }
        return value;
    }
}