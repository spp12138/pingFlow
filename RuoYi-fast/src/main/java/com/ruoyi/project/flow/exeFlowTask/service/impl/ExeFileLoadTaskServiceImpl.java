package com.ruoyi.project.flow.exeFlowTask.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.FileUtil;
import com.ruoyi.common.utils.JDBCUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.flow.exeFlowTask.domain.LoadTable;
import com.ruoyi.project.flow.exeFlowTask.domain.LoadTableColunm;
import com.ruoyi.project.flow.exeFlowTask.domain.LoadTableCommit;
import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;
import com.ruoyi.project.flow.exeFlowTask.service.IExeFileLoadTaskService;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.task.util.FlowUtil;
import com.ruoyi.project.jdbcSet.domain.FlowJdbcSet;
import com.ruoyi.project.jdbcSet.service.IFlowJdbcSetService;

/**
 * 【执行数据文件加载作业】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Service
public class ExeFileLoadTaskServiceImpl implements IExeFileLoadTaskService {

	@Override
	public Object exeFileLoadTask(ExeBean exeBean) throws Exception {
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
		case "fileLoad":
			res = fileLoad(exeStr,jc);
			break;
		}
		return res;
	}

	public static String fileLoad(String exeSrc,JDBCUtil jc) throws Exception {
		Long startTime = System.currentTimeMillis();//开始时间
		String src = "";
		//如果是响应文件的形式, 则去读取文件
		if(exeSrc.startsWith("responseFile")){
			exeSrc = exeSrc.replace(" ", "");//去掉所有空格
			src = FileUtil.readLogByString(exeSrc.substring(exeSrc.indexOf("responseFile:")+"responseFile:".length()));
			System.out.println("读取响应文件完成["+exeSrc.substring(exeSrc.indexOf("responseFile:")+"responseFile:".length())+"]");
		}else{
			src = exeSrc;
		}
		/**
		 * 初始化表信息
		 */
		LoadTable loadTableInfo = getLoadTableInfo(src);
		String sql = createSqlByTableInfo(loadTableInfo);
		String splitToo = loadTableInfo.getSplitSign();//分隔符
		String path = loadTableInfo.getPath();
		int pageSize = loadTableInfo.getPageSize(); //每页条数
		System.out.println("["+loadTableInfo.getFileName()+"]初始化配置信息完成 pageSize["+pageSize+"],path[" + path + "],split["+loadTableInfo.getSplitSign()+"],sql["+sql+"]");
		/**
		 * 初始化文件
		 */
		Long getTotalNumTime = System.currentTimeMillis();//结束时间
		int totalLines = FileUtil.getTotalLines(path) + 1; //总条数 , 因为该方法返回从0开始,所以+1
		Long getTotalNumEndTime = System.currentTimeMillis();//结束时间
		double totalPageNum = Math.ceil(Double.valueOf(totalLines)/Double.valueOf(pageSize)); //总页数
		System.out.println("["+loadTableInfo.getFileName()+"]初始化文件完成,用时["+(getTotalNumEndTime-getTotalNumTime)+"],总条数["+totalLines+"],每页条数["+pageSize+"],总页数["+Integer.valueOf(String.valueOf(totalPageNum))+"]");
		/**
		 * 入库前清理表 truncate:清空 , append 追加
		 */
		Long getTruncateNumTime = System.currentTimeMillis();//结束时间
		if(loadTableInfo.getLoadMode().equals("truncate")){
			jc.executeUpdate("truncate table "+loadTableInfo.getTableName(), null);
		}
		Long getTruncateNumEndTime = System.currentTimeMillis();//结束时间
		System.out.println("["+loadTableInfo.getTableName()+"]初始化完成,loadMode["+loadTableInfo.getLoadMode()+"],用时["+(getTruncateNumEndTime-getTruncateNumTime)+"]");
		
		/**
		 * 开始加载文件入库
		 */
		LoadTableCommit ltc = new LoadTableCommit();
		ltc.setSql(sql);
		ltc.setSlp(splitToo);
		ltc.setColumnType(loadTableInfo.getTableColunm());
		ltc.setTableName(loadTableInfo.getTableName());
		ltc.setPageSize(pageSize);
		ltc.setLoadTableInfo(loadTableInfo);
		for (int i = 1; i <= totalPageNum; i++) {
			List<String> readForPage = FileUtil.readForPage(path, i, pageSize);
			System.out.println("["+loadTableInfo.getFileName()+"]第"+i+"页,已取到["+readForPage.size()+"]条数据,开始提交...");
			ltc.setPageNum(i);
			ltc.setParamList(readForPage);
			ltc = jc.excuteBatch(ltc);
			ltc.setAllCommit(ltc.getAllCommit()+ltc.getNowCommit());
			ltc.setNowCommit(0);
			ltc.setParamList(null);
			readForPage.clear();
		}
		Long endTime = System.currentTimeMillis();//结束时间
		
		String resMsg = "["+loadTableInfo.getFileName()+"]["+loadTableInfo.getTableName()+"]加载完成,文件总行数["+totalLines+"],提交总条数["+ltc.getAllCommit()+"],空行条数["+ltc.getBlankNum()+"],空行位置["+ltc.getBlankIndex()+"],总耗时["+((endTime-startTime)/1000)+"秒]";
		return resMsg;
	}
	
	@SuppressWarnings("unchecked")
	public static LoadTable getLoadTableInfo(String jsonSet) {
		Map<String, Object>  jsonMap = (Map<String, Object>) JSONObject.parse(jsonSet);
		LoadTable lt = new LoadTable();
		lt.setSplitSign(StringUtils.nvl(String.valueOf(jsonMap.get("splitSign")),"|"));
		lt.setFileName(String.valueOf(jsonMap.get("fileName")));
		lt.setFilePath(String.valueOf(jsonMap.get("filePath")));
		lt.setPath(String.valueOf(jsonMap.get("filePath")) + String.valueOf(jsonMap.get("fileName")));
		lt.setTableName(String.valueOf(jsonMap.get("tableName")));
		lt.setPageSize(StringUtils.nvl(Integer.valueOf(String.valueOf(jsonMap.get("pageSize"))),1000000));
		lt.setLoadMode(StringUtils.nvl(String.valueOf(jsonMap.get("loadMode")),"append"));

		List<Map<String, Object>> jsonarray = (List<Map<String, Object>>) JSONArray.parse(String.valueOf(jsonMap.get("tableColunm")));
		List<LoadTableColunm> ltcList = new ArrayList<LoadTableColunm>();
		for (Map<String, Object> map : jsonarray) {
			LoadTableColunm ltc = new LoadTableColunm();
			ltc.setName(String.valueOf(map.get("name")));
			ltc.setType(StringUtils.nvl(String.valueOf(map.get("type")),"string"));
			ltc.setDefaultVal(String.valueOf(map.get("defaultVal")));
			ltc.setFormat(String.valueOf(map.get("format")));
			ltcList.add(ltc);
		}
		lt.setTableColunm(ltcList);
		return lt;
	}
	/**
	 * 根据配置信息生产insert语句
	 * @param lt
	 * @return
	 */
	private static String createSqlByTableInfo(LoadTable lt) {
		String insertSql = "INSERT INTO " + lt.getTableName() + " (";
		String values = "values (";
		for (LoadTableColunm loadTableColunm : lt.getTableColunm()) {
			insertSql += loadTableColunm.getName() + ",";
			values += "?,";
		}
		return  insertSql.substring(0,insertSql.length()-1) + ")" + values.substring(0,values.length()-1) + ")";
	}
	
	/**
	 * 该功能可以根据建表语句生成加载数据文件配置信息
	 * @param args
	 * @return
	 */
	public static String createTableInfoBySql() {
		String readLogByString = FileUtil.readLogByString("C:\\Users\\桑平平\\Desktop\\test111");
		readLogByString = readLogByString.replace("\r", " ").replace("\n", " ").replace("-", " ");
		for (int i = 0; i < 200; i++) {
			readLogByString = readLogByString.replace("  ", " ");
		}
		String resJson = "{"+ "\r\n";
		/**
		 * 解析表名
		 */
		String tableNameIndexS = readLogByString.substring(readLogByString.indexOf("create table") + "create table".length() + 1);//此处+1为去掉空格
		String tableName = tableNameIndexS.substring(0,tableNameIndexS.indexOf(" "));
		/**
		 * 解析表字段
		 */
		int ColumnIndexS = readLogByString.indexOf("(") + 1 + 1 ;//此处+1为去掉左括号  +1为去掉空格
		int ColumnIndexE = readLogByString.lastIndexOf(")");
		String substring = readLogByString.substring(ColumnIndexS, ColumnIndexE).replaceAll("\\(.*?\\)", "");
		String[] split = substring.split(",");
		String columnJson = "["+ "\r\n ";
		for (String string : split) {
			String column = string.startsWith(" ")?string.substring(1):string;
			String columnName = column.substring(0,column.indexOf(" "));
			String columnType = column.substring(column.indexOf(" "));
			columnType = columnType.startsWith(" ")?columnType.substring(1):columnType;
			if(columnType.indexOf(" ") > -1){
				columnType = columnType.substring(0,columnType.indexOf(" "));
			}
			switch (columnType.toUpperCase()) {
			case "VARCHAR2":
				columnType = "string";
				break;
			case "INTEGER":
				columnType = "number";
				break;
			case "NUMBER":
				columnType = "number";
				break;
			case "DATE":
				columnType = "date";
				break;
			default:
				columnType = "string";
				break;
			}
			columnJson += "{\"name\":\""+columnName+"\",\"type\":\""+columnType+"\",\"defaultVal\":\"\",\"format\":\"\"},\r\n";
		}
		columnJson = columnJson.substring(0,columnJson.length() -3 ) + "\r\n]";
		resJson += "\"loadMode\":\"truncate\","+ "\r\n ";
		resJson += "\"splitSign\":\"\","+ "\r\n ";
		resJson += "\"pageSize\":\"1000000\","+ "\r\n ";
		resJson += "\"filePath\":\"\","+ "\r\n ";
		resJson += "\"fileName\":\"\","+ "\r\n ";
		resJson += "\"tableName\":\""+tableName+"\","+ "\r\n ";
		resJson += "\"tableColunm\":"+ "\r\n "+columnJson+""+ "\r\n ";
		resJson += "}";
		return resJson;
	}
	public static void main(String[] args) throws Exception {
//		String path = "D:\\辽阳银行\\20190319\\etl\\myetl_in_linux\\20190317\\V17_T01_S07_TBCLIENT.dat";
//		int totalLines = FileUtil.getTotalLines(path); //总条数
//		System.out.println(totalLines);
		String createTableInfoBySql = createTableInfoBySql();
		System.out.println(createTableInfoBySql);
	}
}
