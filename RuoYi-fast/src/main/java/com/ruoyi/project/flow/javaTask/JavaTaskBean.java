package com.ruoyi.project.flow.javaTask;

import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;



/**
 * 所有Java执行的作业类必须继承此类
 * @author SangYiPing 
 * @date 2021年1月27日
 */
public abstract class JavaTaskBean {
	
	public abstract String executeSubTask(ExeBean exeBean ,Object exeStr, Object params) throws Exception;

	public String execute(ExeBean exeBean ,Object exeStr, Object params) throws Exception{
		return executeSubTask(exeBean,exeStr,params);
	}
	
}
