package com.ruoyi.project.flow.exeFlowTask.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.JSONUtils;
import com.ruoyi.common.utils.ThreadPoolManager;
import com.ruoyi.project.flow.chart.node.service.IFlowTaskNodeService;
import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;
import com.ruoyi.project.flow.exeFlowTask.service.IExeFileLoadTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeFlowTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeHttpTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeJavaTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeLinuxTaskService;
import com.ruoyi.project.flow.exeFlowTask.service.IExeSQLTaskService;
import com.ruoyi.project.flow.info.domain.FlowInfo;
import com.ruoyi.project.flow.info.service.IFlowInfoService;
import com.ruoyi.project.flow.log.domain.FlowLog;
import com.ruoyi.project.flow.log.domain.FlowLogDetail;
import com.ruoyi.project.flow.log.service.IFlowLogDetailService;
import com.ruoyi.project.flow.log.service.IFlowLogService;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.subTask.service.IFlowTaskSubInfoService;
import com.ruoyi.project.flow.task.domain.FlowTaskInfo;
import com.ruoyi.project.flow.task.service.IFlowTaskInfoService;
import com.ruoyi.project.flow.task.util.FlowUtil;
import com.ruoyi.project.flow.task.util.po.FlowTaskBean;
import com.ruoyi.project.flow.task.util.po.FlowTaskNodeView;
import com.ruoyi.project.jdbcSet.service.IFlowJdbcSetService;
import com.ruoyi.project.sshSet.service.IFlowSshSetService;
import com.ruoyi.project.system.config.domain.Config;
import com.ruoyi.project.system.config.mapper.ConfigMapper;

/**
 * 【执行作业】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Service
public class ExeFlowTaskServiceImpl implements IExeFlowTaskService {
	private static final Logger logger = LoggerFactory.getLogger(ExeFlowTaskServiceImpl.class);
	@Autowired
	public ConfigMapper configMapper;
	@Autowired
	public IFlowTaskInfoService flowTaskInfoService;
	@Autowired
	public IExeSQLTaskService exeSQLTaskService;
	@Autowired
	public IExeLinuxTaskService exeLinuxTaskService;
	@Autowired
	public IExeHttpTaskService exeHttpTaskService;
	@Autowired
	public IFlowTaskSubInfoService flowTaskSubInfoService;
	@Autowired
	public IFlowJdbcSetService flowJdbcSetService;
	@Autowired
	public IFlowSshSetService flowSshSetService;
	@Autowired
	public IFlowInfoService flowInfoService;
	@Autowired
	public IFlowLogService flowLogService;
	@Autowired
	public IFlowLogDetailService flowLogDetailService;
	@Autowired
	public IFlowTaskNodeService flowTaskNodeService;
	@Autowired
	public IExeFileLoadTaskService exeFileLoadTaskService;
	@Autowired
	public IExeJavaTaskService exeJavaTaskService;
	
	@Override
	public void startFlow(String flowId, String sl_id,Map<String, Object> configMap) {
		//根据FlowId 查出任务及依赖关系
		FlowTaskInfo fti = flowTaskInfoService.selectFlowTaskInfoById(flowId);
		FlowTaskBean flowBean = FlowUtil.setFlowBean(fti);
		List<FlowTaskNodeView> nodesList = flowBean.getNodesList();
		for (FlowTaskNodeView taskBean : nodesList) {
			if(taskBean.getType().equals("start round")){//找到第一个节点开始
				flowLogService.updateStauts(sl_id, "1");
				exeSingleTask(taskBean,flowBean,flowTaskSubInfoService,this,sl_id,configMap);
				break;
			}
		}
	}
	
	/**
	 * 创建流程实例日志
	 * @param flowId
	 * @param sl_id
	 * @param configMap
	 */
	@Override
	public void createFlowLog(String flowId, String sl_id,Map<String, Object> configMap) {
		FlowInfo fi = flowInfoService.selectFlowInfoById(flowId);
		FlowLog flowLog = new FlowLog();
		flowLog.setId(sl_id);
		flowLog.setFlowName(fi.getFlowName());
		flowLog.setParam(JSONUtils.beanToJson(configMap.toString()));
		flowLog.setFlowSlId(sl_id);
		flowLog.setStauts("0"); //状态 0 新建, 1 进行中 , 2 完成, 3  暂停, 9 报错   
		flowLog.setStartTime(new Date());
		flowLog.setCreateTime(new Date());
		flowLog.setCreateBy("admin");
		flowLogService.insertFlowLog(flowLog);
	}
	/**
	 * 创建流程实例日志详情
	 * @param flowId
	 * @param sl_id
	 * @param configMap
	 * @throws InterruptedException 
	 */
	@Override
	public void createFlowDetailLog(String flowId, String sl_id,Map<String, Object> configMap) throws InterruptedException {
		//根据FlowId 查出任务及依赖关系
		FlowTaskInfo fti = flowTaskInfoService.selectFlowTaskInfoById(flowId);
		FlowTaskBean flowBean = FlowUtil.setFlowBean(fti);
		FlowInfo fi = flowInfoService.selectFlowInfoById(flowId);
		List<FlowTaskNodeView> nodesList = flowBean.getNodesList();
		for (FlowTaskNodeView taskBean : nodesList) {
			FlowLogDetail flowLogDetail = new FlowLogDetail();
			flowLogDetail.setId(sl_id+"_"+taskBean.getId());
			flowLogDetail.setFlowSlId(sl_id);
			flowLogDetail.setFlowName(fi.getFlowName());
			flowLogDetail.setFlowNodeId(taskBean.getId());
			flowLogDetail.setFlowNodeName(taskBean.getName());
			flowLogDetail.setStauts("0"); //状态 0 新建, 1 进行中 , 2 完成, 3  暂停, 9 报错   
			flowLogDetail.setCreateTime(new Date());
			flowLogDetail.setCreateBy("admin");
			flowLogDetailService.insertFlowLogDetail(flowLogDetail);
			Thread.sleep(10);//为保证排序,啦啦啦啦
		}
	}
	
	/**
	 * 调起单个任务
	 * @param ftnv
	 * @param flowBean 
	 * @param flowTaskSubInfoService2 
	 * @param exeFlowTaskServiceImpl 
	 * @param sl_id 
	 * @param configMap 
	 * @param exeTime 
	 */
	public void exeSingleTask(FlowTaskNodeView ftnv, FlowTaskBean flowBean, IFlowTaskSubInfoService flowTaskSubInfoService2, ExeFlowTaskServiceImpl exeFlowTaskServiceImpl, String sl_id, Map<String, Object> configMap) {
		//查询任务详情
		FlowTaskSubInfo ftsi = new FlowTaskSubInfo();
		ftsi.setSubTaskId(ftnv.getId());
		List<FlowTaskSubInfo> flowTaskSubInfoList = flowTaskSubInfoService.selectFlowTaskSubInfoList(ftsi);
		
		ExeBean exeBean = new ExeBean();
		exeBean.setExeFlowTaskService(exeFlowTaskServiceImpl);
		exeBean.setExeSQLTaskService(exeSQLTaskService);
		exeBean.setExeLinuxTaskService(exeLinuxTaskService);
		exeBean.setExeHttpTaskService(exeHttpTaskService);
		exeBean.setExeFileLoadTaskService(exeFileLoadTaskService);
		exeBean.setFlowJdbcSetService(flowJdbcSetService);
		exeBean.setFlowSshSetService(flowSshSetService);
		exeBean.setExeJavaTaskService(exeJavaTaskService);
		exeBean.setFlowTaskBean(flowBean);
		exeBean.setFlowTaskNodeView(ftnv);
		exeBean.setFlowTaskSubInfoService(flowTaskSubInfoService2);
		exeBean.setFlowTaskSubInfoList(flowTaskSubInfoList);
		exeBean.setConf(configMap);
		exeBean.setId(sl_id);
		exeBean.setSl_id(sl_id);

		exeBean.setFlowLogDetailService(flowLogDetailService);
		exeBean.setFlowLogService(flowLogService);
		
		ExeTask job = new ExeTask(exeBean);
		Thread t = new Thread(job);
		t.setName("JobNode_"+ftnv.getId());
		findFlowTaskStatus(flowBean);
		ThreadPoolExecutor ex = (ThreadPoolExecutor) thMap.getFlowInfo(sl_id, "ThreadPoolExecutor");
		ex.execute(t);
	}
	
	/**
	 * 调起单个任务
	 * @param ftnv
	 * @param flowBean 
	 * @param flowTaskSubInfoService2 
	 * @param exeFlowTaskServiceImpl 
	 * @param sl_id 
	 * @param configMap 
	 * @param exeTime 
	 */
	public void exeSingleTask(FlowTaskNodeView flowTaskNodeView, ExeBean exeBean) {
		exeSingleTask(
						flowTaskNodeView, 
						exeBean.getFlowTaskBean(), 
						exeBean.getFlowTaskSubInfoService(), 
						exeBean.getExeFlowTaskService(), 
						exeBean.getSl_id(), 
						exeBean.getConf()
					 );		
	}
	
	/**
	 * 唤起守护线程
	 * @param ftnv
	 * @param flowBean 
	 * @param flowTaskSubInfoService2 
	 * @param exeFlowTaskServiceImpl 
	 */
	public void CallDaemon(String flowId) {
		Thread shThread = new Thread(new  Runnable() {
			@Override
			public void run() {
				while(true){
					
				}
			}
		});
		shThread.setDaemon(true);  
		shThread.setName("Job_守护线程");
		shThread.start();
	}
	
	/**
	 * 根据流程ID计算当前还有多少正在跑的线程
	 */
	public void findFlowTaskStatus(FlowTaskBean flowBean) {
		int exists = 0;
		String flowId = flowBean.getFlowId();
		List<FlowTaskNodeView> nodesList = flowBean.getNodesList();//获取节点总数
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int noThreads = currentGroup.activeCount();
		Thread[] lstThreads = new Thread[noThreads];
		currentGroup.enumerate(lstThreads);
		logger.debug(Thread.currentThread().getName()+" >>> 开始运行....");
		for (int i = 0; i < noThreads; i++) {
			if(lstThreads[i].getName().indexOf(flowId) > -1){
				exists++;
//				logger.debug("线程号：" + exists + " = " + lstThreads[i].getName());
			}
		}
		logger.debug(Thread.currentThread().getName() + " >>> 检查结果 : 当前存活任务数量: " + exists + " , 总任务数量 : " + nodesList.size() + " , 已完成任务数量: " + (nodesList.size()-exists) );
		if(exists == 0){
			logger.debug(Thread.currentThread().getName() + "所有任务均已完成,作业结束." );
		}
	}
	
	
	/**
	 * 调度参数处理,一次查出所有所用到的参数,并转到Map中
	 * @return 
	 */
	public Map<String, String> getConf() {
		//查询批量调度所用到的所有参数
		Config config = new Config();
		config.setConfigKey("FLOW_");
		Map<String,String> confMap = new HashMap<String,String>();
		List<Config> flowConfigList = configMapper.selectConfigList(config);
		for (Config config2 : flowConfigList) {
			confMap.put(config2.getConfigKey(), config2.getConfigValue());
		}
		return confMap;
	}

	@Override
	public void start(String flowId, String paramMap) throws InterruptedException {

		String sl_id = UUID.randomUUID().toString().replace("-","");//实例ID
		Map<String, Object> configMap = JSONUtils.jsonToMap(paramMap);

		//并行数量,如果传了则用传入的, 如果没传读取CPU核心数+1
		int poolSize = Integer.valueOf(configMap.get("poolSize") == null ? "0" : String.valueOf(configMap.get("poolSize")));
		ThreadPoolExecutor execute = ThreadPoolManager.getExecute(poolSize);
		//启动的时候创建线程池带着走
		thMap.putFlowInfo(sl_id, "ThreadPoolExecutor", execute);
		
		//开始创建日志(并把流程详情都插入到日志表中)(流程详情日志Id=实例ID+节点ID)(流程日志Id=实例ID)
		createFlowLog(flowId, sl_id, configMap);
		createFlowDetailLog(flowId, sl_id, configMap);
		
		//开始执行
		startFlow(flowId,sl_id,configMap);
		
	}

}
