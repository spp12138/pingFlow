package com.ruoyi.project.flow.exeFlowTask.service.impl;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.JDBCUtil;
import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;
import com.ruoyi.project.flow.exeFlowTask.service.IExeSQLTaskService;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.task.util.FlowUtil;
import com.ruoyi.project.jdbcSet.domain.FlowJdbcSet;
import com.ruoyi.project.jdbcSet.service.IFlowJdbcSetService;

/**
 * 【执行SQL作业】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Service
public class ExeSQLTaskServiceImpl implements IExeSQLTaskService {

	/**
	 * 执行SQL
	 * @param ftnv
	 * @param flowBean 
	 * @param flowTaskSubInfoService2 
	 * @param exeFlowTaskServiceImpl 
	 * @return 
	 * @throws Exception 
	 */
	public Object exeSqlTask(ExeBean exeBean) throws Exception {
		List<FlowTaskSubInfo> flowTaskSubInfoList = exeBean.getFlowTaskSubInfoList();
		if(flowTaskSubInfoList == null || flowTaskSubInfoList.isEmpty()){
			//执行空任务
			return null;
		}
		FlowTaskSubInfo flowTaskSubInfo = flowTaskSubInfoList.get(0);
		String exeStr = flowTaskSubInfo.getExeStr();//执行内容
		String subJdbc = flowTaskSubInfo.getSubJdbc();//根据JDBC ID 去找到对应配置的JDBC信息
		IFlowJdbcSetService flowJdbcSetService = exeBean.getFlowJdbcSetService();
		FlowJdbcSet flowJdbcSet = flowJdbcSetService.selectFlowJdbcSetById(subJdbc);
		String subTaskType = flowTaskSubInfo.getSubTaskType();//类型
		//连接数据库
		JDBCUtil jc = new JDBCUtil(flowJdbcSet.getJdbcDriver(),flowJdbcSet.getJdbcUrl(),flowJdbcSet.getJdbcUsername(),flowJdbcSet.getJdbcPassword());
		//替换变量
		exeStr = FlowUtil.repParamStr(exeStr, exeBean);
		Set<Entry<String,Object>> confEntrySet = exeBean.getConf().entrySet();
		for (Entry<String, Object> entry : confEntrySet) {
			exeStr = exeStr.replace("${"+entry.getKey()+"}", String.valueOf(entry.getValue()));
		}
		Object res = "";
		switch (subTaskType) {
		case "SqlProcRes":
			//调用存储过程,带返回值的
			res = jc.proc2("{"+exeStr+"}", new Object[]{} , 1 , java.sql.Types.VARCHAR );
			break;
		case "SqlProcNoRes":
			//调用存储过程,不带返回值的
			jc.proc1("{"+exeStr+"}", new Object[]{});
			break;
		case "SQL":
			//调用SQL
			res = jc.executeUpdate(exeStr, new Object[]{});
			break;
		}
		return res;
	}
	/**
	 * 获取返回值位置, (问号的位置)
	 * @param str
	 * @return
	 */
	public int getProcResIndex(String str) {
		int khz = str.indexOf("(")+1;
		int kjy = str.indexOf(")");
		String substring = str.substring(khz, kjy);
		String[] split = substring.split(",");
		for (int i = 0; i < split.length; i++) {
			if(split[i].equals("?")){
				return i;
			}
		}
		throw new RuntimeException("未定义返回值位置!");
	}
 
}
