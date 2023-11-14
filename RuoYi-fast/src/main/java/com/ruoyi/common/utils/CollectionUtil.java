package com.ruoyi.common.utils;


import java.util.Collection;
import java.util.Map;

/**
 * 
 * <p>文件名: CollectionUtil.java</p>  
 * <p>描述: 集合工具类</p>  
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
public class CollectionUtil {
    /**
     * 判断collection是否为空
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection){
        //return CollectionUtils.isEmpty(collection);
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断Collection是否非空
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

    /**
     * 判断map是否为空
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?,?> map){
        //return MapUtils.isEmpty(map);
        return map == null || map.isEmpty();
    }

    /**
     * 判断map是否非
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }
}