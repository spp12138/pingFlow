package com.ruoyi.project.flow.exeFlowTask.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;
import com.ruoyi.project.flow.exeFlowTask.service.IExeLinuxTaskService;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.task.util.FlowUtil;
import com.ruoyi.project.sshSet.domain.FlowSshSet;
import com.ruoyi.project.sshSet.service.IFlowSshSetService;

/**
 * 【执行Linux Shell作业】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Service
public class ExeLinuxTaskServiceImpl implements IExeLinuxTaskService {


	@Override
	public Object exeLinuxTask(ExeBean exeBean) throws Exception {
		List<FlowTaskSubInfo> flowTaskSubInfoList = exeBean.getFlowTaskSubInfoList();
		if(flowTaskSubInfoList == null || flowTaskSubInfoList.isEmpty()){
			//执行空任务
			return null;
		}
		FlowTaskSubInfo flowTaskSubInfo = flowTaskSubInfoList.get(0);
		String exeStr = flowTaskSubInfo.getExeStr();//执行内容
		//替换变量
		exeStr = FlowUtil.repParamStr(exeStr, exeBean);
		Set<Entry<String,Object>> confEntrySet = exeBean.getConf().entrySet();
		for (Entry<String, Object> entry : confEntrySet) {
			exeStr = exeStr.replace("${"+entry.getKey()+"}", String.valueOf(entry.getValue()));
		}
		//如果执行内容为多行的则替换为单行,分号分隔
		exeStr = exeStr.replace("\r\n", ";").replace("\r", ";");
		String subTaskType = flowTaskSubInfo.getSubTaskType();//类型
		String res = "";
		switch (subTaskType) {
		case "LinuxSSH":
			//调用远程Ssh
			res = exeSsh(exeBean, flowTaskSubInfo, exeStr);
			break;
		case "LinuxLocal":
			//调用本地Shell
			res = exeShell(exeStr, res);
			break;
		}
		return res;
	}



	private String exeShell(String exeStr, String res) throws IOException,
			UnsupportedEncodingException {
		Process process =  Runtime.getRuntime().exec(exeStr);
		BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
		BufferedReader errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream(),"GBK"));
		
		String err = null;
		String line = null;
		while ((line = inputStreamReader.readLine()) != null) {
			res += line;
		}
		while ((line = errorStreamReader.readLine()) != null) {
			err += line;
		}
		if(StringUtils.isNoneBlank(err)){
			throw new RuntimeException(err);
		}
		return res;
	}

	
	
	@SuppressWarnings("resource")
	private String exeSsh(ExeBean exeBean, FlowTaskSubInfo flowTaskSubInfo,String exeStr) throws IOException {
		String subSsh = flowTaskSubInfo.getSubSsh();//根据Ssh ID 去找到对应配置的JDBC信息
		IFlowSshSetService flowSshSetService = exeBean.getFlowSshSetService();
		FlowSshSet flowSshSet = flowSshSetService.selectFlowSshSetById(subSsh);
		//连接SSH
		String hostname = flowSshSet.getSshHostname();
		String username = flowSshSet.getSshUsername();
		String password = flowSshSet.getSshPassword();
		
		String res = "";
		String err = "";
		
		Connection conn = new Connection(hostname);
		conn.connect();
		// 进行身份认证
		boolean isAuthenticated = conn.authenticateWithPassword(username,password);
		if (isAuthenticated == false)
			throw new IOException("登录认证失败!"+hostname+"/"+username+":"+password);
		// 开启一个Session
		Session sess = conn.openSession();
		// 执行具体命令
		sess.execCommand(exeStr);
		// 获取返回输出
		InputStream stdout = new StreamGobbler(sess.getStdout());
		// 返回错误输出
		InputStream stderr = new StreamGobbler(sess.getStderr());
		BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdout));
		BufferedReader stderrReader = new BufferedReader(new InputStreamReader(stderr));

		while (true) {
			String line = stdoutReader.readLine();
			if (line == null)
				break;
			res += line + "\r\n";
		}

		while (true) {
			String line = stderrReader.readLine();
			if (line == null)
				break;
			err += line + "\r\n";
		}
		// 关闭Session
		sess.close();
		// 关闭Connection
		conn.close();
		if(StringUtils.isNoneBlank(err)){
			throw new RuntimeException(err);
		}
		return res;
	}
}
