package com.ruoyi.project.flow.exeFlowTask.service.impl;

import java.util.HashMap;
import java.util.Map;



public class thMap {
	private volatile static Map<String, Map<String, Object>> m = null; // 加上volatile关键字，线程每次使用到被volatile关键字修饰的变量时，都会去堆里拿最新的数据
	private thMap() {}
 
    public static Map<String, Map<String, Object>> getInstance() {
        if (m == null) { // 在懒汉式同步锁的基础上加上了一个判断，如果单例对象不为空，就不需要执行获得对象同步锁的代码，从而提高效率
            synchronized (thMap.class) { // 只有当单例对象为空时才会执行
                if (m == null) {
                	m = new HashMap<String, Map<String, Object>>();
                }
            }
        }
        return m;
    }
    
    public static void putFlowInfo(String flowId,String key,Object value){
    	Map<String, Object> map = thMap.getInstance().get(flowId);
    	if(map == null){
    		synchronized (thMap.class){
    			if(map == null){
    				map = new HashMap<String, Object>();
    			}
    		}
    	}
    	map.put(key, value);
    	thMap.getInstance().put(flowId, map);
    }
    public static Object getFlowInfo(String flowId,String key){
    	return thMap.getInstance().get(flowId).get(key);
    }
}
