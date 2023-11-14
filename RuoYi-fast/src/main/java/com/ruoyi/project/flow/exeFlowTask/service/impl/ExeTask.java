package com.ruoyi.project.flow.exeFlowTask.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;
import com.ruoyi.project.flow.exeFlowTask.service.IExeFileLoadTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeHttpTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeJavaTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeLinuxTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeSQLTaskService;
import com.ruoyi.project.flow.log.domain.FlowLogDetail;
import com.ruoyi.project.flow.log.service.IFlowLogDetailService;
import com.ruoyi.project.flow.log.service.IFlowLogService;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.subTask.service.IFlowTaskSubInfoService;
import com.ruoyi.project.flow.task.util.po.FlowTaskBean;
import com.ruoyi.project.flow.task.util.po.FlowTaskNodeView;
import com.ruoyi.project.jdbcSet.service.IFlowJdbcSetService;
import com.ruoyi.project.sshSet.service.IFlowSshSetService;

public class ExeTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(ExeTask.class);
	final FlowTaskNodeView flowTaskNodeView;
	final FlowTaskBean flowBean;
	final ExeFlowTaskServiceImpl exeFlowTaskServiceImpl;
	final IFlowTaskSubInfoService flowTaskSubInfoService;
	final IExeSQLTaskService exeSQLTaskService;
	final IExeLinuxTaskService exeLinuxTaskService;
	final IExeHttpTaskService exeHttpTaskService;
	final IExeFileLoadTaskService exeFileLoadTaskService;
	final IExeJavaTaskService exeJavaTaskService;
	final IFlowSshSetService flowSshSetService;
	final IFlowJdbcSetService flowJdbcSetService;
	final List<FlowTaskSubInfo> selectFlowTaskSubInfoList;
	final ExeBean exeBean;
	final Map<String,Object> configMap;
	final IFlowLogDetailService flowLogDetailService;
	final IFlowLogService flowLogService;
	
	public ExeTask(ExeBean exeBean) {
		this.flowTaskNodeView = exeBean.getFlowTaskNodeView();
		this.flowBean = exeBean.getFlowTaskBean();
		this.flowTaskSubInfoService = exeBean.getFlowTaskSubInfoService();
		this.selectFlowTaskSubInfoList = exeBean.getFlowTaskSubInfoList();
		this.exeFlowTaskServiceImpl = exeBean.getExeFlowTaskService();
		this.exeFileLoadTaskService = exeBean.getExeFileLoadTaskService();
		this.exeJavaTaskService = exeBean.getExeJavaTaskService();
		this.exeBean = exeBean;
		this.configMap = exeBean.getConf();
		
		this.flowJdbcSetService = exeBean.getFlowJdbcSetService();
		this.flowSshSetService = exeBean.getFlowSshSetService();

		this.exeSQLTaskService = exeBean.getExeSQLTaskService();
		this.exeLinuxTaskService = exeBean.getExeLinuxTaskService();
		this.exeHttpTaskService = exeBean.getExeHttpTaskService();
	
		
		this.flowLogDetailService = exeBean.getFlowLogDetailService();
		this.flowLogService = exeBean.getFlowLogService();

	}

	@Override
	public void run() {
		String name = flowTaskNodeView.getName();
		Object exeTaskResStr = "";
		thMap.putFlowInfo(exeBean.getSl_id(),flowTaskNodeView.getId(), "0"); 
		logger.debug(name + " 开始执行 ... ");
		//实例ID + 节点ID
		FlowLogDetail logDetailById = flowLogDetailService.selectFlowLogDetailById(exeBean.getSl_id()+"_"+flowTaskNodeView.getId());//日志详情的ID为,实例ID + 节点ID  sl_id+"_"+taskBean.getId()
		flowLogDetailService.updateDetailStauts(logDetailById,"1",String.valueOf(exeTaskResStr));
		try {
			if(selectFlowTaskSubInfoList == null || selectFlowTaskSubInfoList.isEmpty()){
				logger.debug(name + " 为空 ... ");
			}else{
				FlowTaskSubInfo flowTaskSubInfo = selectFlowTaskSubInfoList.get(0);
				//开始执行任务
				exeTaskResStr = exeTask(exeTaskResStr, flowTaskSubInfo);
			}
			logger.debug(name+" >>> 执行完成，执行结果：【"+exeTaskResStr+"】");
			thMap.putFlowInfo(exeBean.getSl_id(),flowTaskNodeView.getId(), "1");
			String exeRes = String.valueOf(exeTaskResStr);
			flowLogDetailService.updateDetailStauts(logDetailById,"2",exeRes.length() > 3000 ? exeRes.substring(0,3000):exeRes);
			
			if(flowTaskNodeView.getType().equals("end round")){//到最后一个节点 则更新整体日志状态
				flowLogService.updateStauts(exeBean.getSl_id(), "2",String.valueOf(exeTaskResStr));
			}
			
			//执行完成后调起下级
			exeNext();
		} catch (Exception e) {
			String errMsg = name+" >>> 执行失败，执行结果：【"+exeTaskResStr+"】【"+e.getMessage()+"】";
			flowLogDetailService.updateDetailStauts(logDetailById,"4",errMsg);
			flowLogService.updateStauts(exeBean.getSl_id(), "4",errMsg);
			logger.debug(errMsg);
			e.printStackTrace();
			thMap.putFlowInfo(exeBean.getSl_id(),flowTaskNodeView.getId(), "9");
		} catch (Error e) {
			String errMsg = name+" >>> 执行失败，严重异常，执行结果：【"+exeTaskResStr+"】【"+e.getMessage()+"】";
			flowLogDetailService.updateDetailStauts(logDetailById,"4",errMsg);
			flowLogService.updateStauts(exeBean.getSl_id(), "4",errMsg);
			logger.debug(errMsg);
			e.printStackTrace();
			thMap.putFlowInfo(exeBean.getSl_id(),flowTaskNodeView.getId(), "9");
		}
		
	}

	/**
	 * 开始执行任务
	 * @param exeTaskResStr
	 * @param flowTaskSubInfo
	 * @return
	 * @throws Exception
	 */
	private Object exeTask(Object exeTaskResStr, FlowTaskSubInfo flowTaskSubInfo)throws Exception {
		String taskType = flowTaskSubInfo.getSubTaskType();
		if(taskType.equals("SqlProcRes") || taskType.equals("SqlProcNoRes")|| taskType.equals("SQL")){
			exeTaskResStr = exeSQLTaskService.exeSqlTask(exeBean);
		}else if(taskType.equals("LinuxLocal") || taskType.equals("LinuxSSH")){
			exeTaskResStr = exeLinuxTaskService.exeLinuxTask(exeBean);
		}else if(taskType.equals("Http")){
			exeTaskResStr = exeHttpTaskService.exeHttpTask(exeBean);
		}else if(taskType.equals("fileLoad")){
			exeTaskResStr = exeFileLoadTaskService.exeFileLoadTask(exeBean);
		}else if(taskType.equals("Java")){
			exeTaskResStr = exeJavaTaskService.exeJavaTask(exeBean);
		}
		return exeTaskResStr;
	}


	/**
	 * 调起下级
	 */
	private void exeNext() {
		
		//整体状态如为暂停， 则不继续调起下级
		String stauts = String.valueOf(thMap.getFlowInfo(exeBean.getSl_id(),"stauts"));
		if("3".equals(stauts)){
			return ;
		}		
		
		List<String> toNodeId = flowTaskNodeView.getToNodeId();
		for (String nodeId : toNodeId) {
			//1.检查下级是否还有别的依赖
			List<FlowTaskNodeView> nodesList = flowBean.getNodesList();
			for (FlowTaskNodeView flowTaskNodeView : nodesList) {
				if(flowTaskNodeView.getId().equals(nodeId)){
					//本次是否调起
					boolean exeTaskFlag = true;
					
					//节点ID
					String NodeId = flowTaskNodeView.getId();
					//只有节点状态为 0 新建 的 才会被调起 , 不是0新建状态的 将被跳过
					FlowLogDetail flowLogDetailList = flowLogDetailService.selectFlowLogDetailById(exeBean.getSl_id()+"_"+NodeId);
					if(!"0".equals(flowLogDetailList.getStauts())){
						continue;
					}
					
					List<String> fromNodeIds = flowTaskNodeView.getFromNodeId();
					//2.如果下级还有别的依赖则判断其他依赖是否完成,如存在未完成的上级依赖则放弃
					for (String fromNodeId : fromNodeIds) {
						String thStauts = String.valueOf(thMap.getFlowInfo(exeBean.getSl_id(),fromNodeId));
						if(!(StringUtils.isNoneBlank(thStauts) && thStauts.equals("1"))){
							exeTaskFlag = false;
						}
					}
					//上级依赖均已完成则开始调起下级
					if(exeTaskFlag){
						exeFlowTaskServiceImpl.exeSingleTask(flowTaskNodeView, flowBean,flowTaskSubInfoService, exeFlowTaskServiceImpl,exeBean.getSl_id(),configMap);
					}
					
				}
			}
		}
	}
}
