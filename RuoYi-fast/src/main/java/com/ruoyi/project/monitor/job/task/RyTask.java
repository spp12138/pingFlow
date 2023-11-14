package com.ruoyi.project.monitor.job.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.flow.exeFlowTask.service.impl.ExeFlowTaskServiceImpl;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {
	
	@Autowired
	ExeFlowTaskServiceImpl exeFlowTaskServiceImpl;
	
	public void ryMultipleParams(String s, Boolean b, Long l, Double d,Integer i) {
		System.out.println(StringUtils.format(
				"执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
	}

	public void ryParams(String params) {
		System.out.println("执行有参方法：" + params);
	}

	public void exeJob(String flowId,String paramMap) throws InterruptedException {
		exeFlowTaskServiceImpl.start(flowId, paramMap);
		System.out.println("定時任務開始执行调度任务：" +flowId + paramMap);
	}

	public void ryNoParams() {
		System.out.println("执行无参方法");
	}
}
