package com.ruoyi.project.flow.exeFlowTask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.JSONUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;
import com.ruoyi.project.flow.exeFlowTask.service.IExeJavaTaskService;
import com.ruoyi.project.flow.javaTask.JavaTaskBean;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;

/**
 * 【执行JAVA作业】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Service
public class ExeJavaTaskServiceImpl implements IExeJavaTaskService {

	@Override
	public Object exeJavaTask(ExeBean exeBean) throws Exception {
		List<FlowTaskSubInfo> flowTaskSubInfoList = exeBean.getFlowTaskSubInfoList();
		if(flowTaskSubInfoList == null || flowTaskSubInfoList.isEmpty()){
			//执行空任务
			return null;
		}
		FlowTaskSubInfo flowTaskSubInfo = flowTaskSubInfoList.get(0);
		String exeStr = flowTaskSubInfo.getExeStr();//执行内容
		//java类名   java_name
		//参数{1=1,2=2,3=3} params
		Map<String, Object> jsonToMap = JSONUtils.jsonToMap(exeStr);
		JavaTaskBean bean = SpringUtils.getBean(String.valueOf(jsonToMap.get("java_name")));
		String executeMsg = bean.execute(exeBean,exeStr,jsonToMap.get("params"));
		return executeMsg;
	}

}
