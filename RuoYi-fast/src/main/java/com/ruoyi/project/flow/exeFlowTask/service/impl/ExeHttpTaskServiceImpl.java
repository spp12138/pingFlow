package com.ruoyi.project.flow.exeFlowTask.service.impl;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.HttpUtil;
import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;
import com.ruoyi.project.flow.exeFlowTask.service.IExeHttpTaskService;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.task.util.FlowUtil;

/**
 * 【执行SQL作业】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Service
public class ExeHttpTaskServiceImpl implements IExeHttpTaskService {

	/**
	 * 执行SQL
	 * @param ftnv
	 * @param flowBean 
	 * @param flowTaskSubInfoService2 
	 * @param exeFlowTaskServiceImpl 
	 * @return 
	 * @throws Exception 
	 */
	public Object exeHttpTask(ExeBean exeBean) throws Exception {
		List<FlowTaskSubInfo> flowTaskSubInfoList = exeBean.getFlowTaskSubInfoList();
		if(flowTaskSubInfoList == null || flowTaskSubInfoList.isEmpty()){
			//执行空任务
			return null;
		}
		FlowTaskSubInfo flowTaskSubInfo = flowTaskSubInfoList.get(0);
		String exeStr = flowTaskSubInfo.getExeStr();//执行内容
		String subTaskType = flowTaskSubInfo.getSubTaskType();//类型
		//替换变量
		exeStr = FlowUtil.repParamStr(exeStr, exeBean);
		Set<Entry<String,Object>> confEntrySet = exeBean.getConf().entrySet();
		for (Entry<String, Object> entry : confEntrySet) {
			exeStr = exeStr.replace("${"+entry.getKey()+"}", String.valueOf(entry.getValue()));
		}
		Object res = "";
		switch (subTaskType) {
		case "Http":
			//发起Http请求
			res = HttpUtil.sendGet(exeStr, "");
			break;
		}
		return res;
	}
}
