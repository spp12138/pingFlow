package com.ruoyi.common.utils;


/**
 * 
 * <p>文件名: ArrayUtil.java</p>  
 * <p>描述: 数组工具类</p>  
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
public class ArrayUtil {
    /**
     * 判断数组是否为空
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array){
        return !isEmpty(array);
    }

    /**
     * 判断数组是否非空
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array){
        return array==null||array.length==0;
    }
}