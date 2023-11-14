package com.ruoyi.project.flow.exeFlowTask.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.flow.exeFlowTask.service.IExeFileLoadTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeHttpTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeJavaTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeLinuxTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeSQLTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.impl.ExeFlowTaskServiceImpl;
import com.ruoyi.project.flow.log.service.IFlowLogDetailService;
import com.ruoyi.project.flow.log.service.IFlowLogService;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.subTask.service.IFlowTaskSubInfoService;
import com.ruoyi.project.flow.task.util.po.FlowTaskBean;
import com.ruoyi.project.flow.task.util.po.FlowTaskNodeView;
import com.ruoyi.project.jdbcSet.service.IFlowJdbcSetService;
import com.ruoyi.project.sshSet.service.IFlowSshSetService;

public class ExeBean {
	
	private String id;//实例ID
	private FlowTaskNodeView flowTaskNodeView;
	private FlowTaskBean flowTaskBean;
	private List<FlowTaskSubInfo> flowTaskSubInfoList;
	private Map<String, Object> conf;
	private String sl_id;//实例ID
	
	private ExeFlowTaskServiceImpl exeFlowTaskService;
	private IFlowTaskSubInfoService flowTaskSubInfoService;
	private IExeSQLTaskService exeSQLTaskService;
	private IFlowJdbcSetService flowJdbcSetService;
	private IExeLinuxTaskService exeLinuxTaskService;
	private IFlowSshSetService flowSshSetService;
	private IExeHttpTaskService exeHttpTaskService;
	private IExeFileLoadTaskService exeFileLoadTaskService ;
	private IExeJavaTaskService exeJavaTaskService ;
	
	private IFlowLogDetailService flowLogDetailService;
	private IFlowLogService flowLogService;
	
	
	public IExeFileLoadTaskService getExeFileLoadTaskService() {
		return exeFileLoadTaskService;
	}
	public void setExeFileLoadTaskService(
			IExeFileLoadTaskService exeFileLoadTaskService) {
		this.exeFileLoadTaskService = exeFileLoadTaskService;
	}
	public IFlowLogService getFlowLogService() {
		return flowLogService;
	}
	public void setFlowLogService(IFlowLogService flowLogService) {
		this.flowLogService = flowLogService;
	}
	public IFlowLogDetailService getFlowLogDetailService() {
		return flowLogDetailService;
	}
	public void setFlowLogDetailService(IFlowLogDetailService flowLogDetailService) {
		this.flowLogDetailService = flowLogDetailService;
	}
	public IExeHttpTaskService getExeHttpTaskService() {
		return exeHttpTaskService;
	}
	public void setExeHttpTaskService(IExeHttpTaskService exeHttpTaskService) {
		this.exeHttpTaskService = exeHttpTaskService;
	}
	public IFlowSshSetService getFlowSshSetService() {
		return flowSshSetService;
	}
	public void setFlowSshSetService(IFlowSshSetService flowSshSetService) {
		this.flowSshSetService = flowSshSetService;
	}
	public IExeLinuxTaskService getExeLinuxTaskService() {
		return exeLinuxTaskService;
	}
	public void setExeLinuxTaskService(IExeLinuxTaskService exeLinuxTaskService) {
		this.exeLinuxTaskService = exeLinuxTaskService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSl_id() {
		return sl_id;
	}
	public void setSl_id(String sl_id) {
		this.sl_id = sl_id;
	}
	public Map<String, Object> getConf() {
		return conf;
	}
	public void setConf(Map<String, Object> conf) {
		this.conf = conf;
	}
	public IFlowJdbcSetService getFlowJdbcSetService() {
		return flowJdbcSetService;
	}
	public void setFlowJdbcSetService(IFlowJdbcSetService flowJdbcSetService) {
		this.flowJdbcSetService = flowJdbcSetService;
	}
	public List<FlowTaskSubInfo> getFlowTaskSubInfoList() {
		return flowTaskSubInfoList;
	}
	public void setFlowTaskSubInfoList(List<FlowTaskSubInfo> flowTaskSubInfoList) {
		this.flowTaskSubInfoList = flowTaskSubInfoList;
	}
	public FlowTaskNodeView getFlowTaskNodeView() {
		return flowTaskNodeView;
	}
	public void setFlowTaskNodeView(FlowTaskNodeView flowTaskNodeView) {
		this.flowTaskNodeView = flowTaskNodeView;
	}
	public FlowTaskBean getFlowTaskBean() {
		return flowTaskBean;
	}
	public void setFlowTaskBean(FlowTaskBean flowTaskBean) {
		this.flowTaskBean = flowTaskBean;
	}
	public ExeFlowTaskServiceImpl getExeFlowTaskService() {
		return exeFlowTaskService;
	}
	public void setExeFlowTaskService(ExeFlowTaskServiceImpl exeFlowTaskService) {
		this.exeFlowTaskService = exeFlowTaskService;
	}
	public IFlowTaskSubInfoService getFlowTaskSubInfoService() {
		return flowTaskSubInfoService;
	}
	public void setFlowTaskSubInfoService(
			IFlowTaskSubInfoService flowTaskSubInfoService) {
		this.flowTaskSubInfoService = flowTaskSubInfoService;
	}
	public IExeSQLTaskService getExeSQLTaskService() {
		return exeSQLTaskService;
	}
	public void setExeSQLTaskService(IExeSQLTaskService exeSQLTaskService) {
		this.exeSQLTaskService = exeSQLTaskService;
	}
	public IExeJavaTaskService getExeJavaTaskService() {
		return exeJavaTaskService;
	}
	public void setExeJavaTaskService(IExeJavaTaskService exeJavaTaskService) {
		this.exeJavaTaskService = exeJavaTaskService;
	}
	
	
	
}	
