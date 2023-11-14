package com.ruoyi.project.flow.exeFlowTask.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.flow.exeFlowTask.service.IExeFlowTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.impl.thMap;
import com.ruoyi.project.flow.log.domain.FlowLog;
import com.ruoyi.project.flow.log.domain.FlowLogDetail;
import com.ruoyi.project.flow.log.service.IFlowLogDetailService;
import com.ruoyi.project.flow.log.service.IFlowLogService;

/**
 * 【执行作业】Controller
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Controller
@RequestMapping("/flow/exeFlow")
public class ExeFlowTaskController extends BaseController {
	
	@Autowired
	public IExeFlowTaskService exeFlowTaskService;
	@Autowired
	public IFlowLogService flowLogService;
	@Autowired
	public IFlowLogDetailService flowLogDetailService;
	
	
	/**
	 * 执行作业
	 * @throws InterruptedException 
	 */
	@RequiresPermissions("subTask:exeFlow")
	@Log(title = "【执行作业】", businessType = BusinessType.OTHER)
	@PostMapping("/exe")
	@ResponseBody
	public AjaxResult exeFlow(String flowId,String paramMap) throws InterruptedException {
		exeFlowTaskService.start(flowId,paramMap);
		return success();
	}
	/**
	 * 暂停作业
	 * @throws InterruptedException 
	 */
	@RequiresPermissions("subTask:exeFlow")
	@Log(title = "【暂停作业】", businessType = BusinessType.OTHER)
	@PostMapping("/stopExe")
	@ResponseBody
	public AjaxResult stopExe(String slId) throws InterruptedException {
		
		//设置状态为暂停
		thMap.putFlowInfo(slId, "stauts", "3");

		//去日志表中将所有新建的暂停
		FlowLog flowLog = new FlowLog();
		flowLog.setFlowSlId(slId);
		flowLog.setStauts("0");
		List<FlowLog> flowLogList = flowLogService.selectFlowLogList(flowLog);
		for (FlowLog updateFlowLog : flowLogList) {
			flowLogService.updateFlowLog(updateFlowLog);
		}
		
		//去日志表中将所有新建的暂停
		FlowLogDetail flowLogDetail = new FlowLogDetail();
		flowLogDetail.setFlowSlId(slId);
		flowLogDetail.setStauts("0");
		List<FlowLogDetail> flowLogDetailList = flowLogDetailService.selectFlowLogDetailList(flowLogDetail);
		for (FlowLogDetail updateFlowLogDetail : flowLogDetailList) {
			flowLogDetailService.updateFlowLogDetail(updateFlowLogDetail);
		}
				
		return success("已暂停，正在运行的作业将继续运行至完成。");
	}

}





