package com.ruoyi.project.flow.exeFlowTask.service;

import java.util.Map;



/**
 * 【执行作业】Service接口
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
public interface IExeFlowTaskService {

	public void startFlow(String flowId, String sl_id, Map<String, Object> configMap);
	public void start(String flowId, String paramMap) throws InterruptedException;

	/**
	 * 创建流程实例日志详情
	 * @param flowId
	 * @param sl_id
	 * @param configMap
	 * @throws InterruptedException 
	 */
	public void createFlowDetailLog(String flowId, String sl_id,Map<String, Object> configMap) throws InterruptedException;
	/**
	 * 创建流程实例日志
	 * @param flowId
	 * @param sl_id
	 * @param configMap
	 */
	public void createFlowLog(String flowId, String sl_id,Map<String, Object> configMap);
	
}
