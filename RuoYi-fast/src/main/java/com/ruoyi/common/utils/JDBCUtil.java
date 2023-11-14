package com.ruoyi.common.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.project.flow.exeFlowTask.domain.LoadTableColunm;
import com.ruoyi.project.flow.exeFlowTask.domain.LoadTableCommit;

/**
 * 对jdbc的完整封装
 * @author SangYiPing
 */
public class JDBCUtil {

	private String driver = null;
	private String url = null;
	private String username = null;
	private String password = null;

	private CallableStatement callableStatement = null;// 创建CallableStatement对象
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rst = null;

	/*
	 * static { try { // 加载数据库驱动程序 Class.forName(driver); } catch
	 * (ClassNotFoundException e) { System.out.println("加载驱动错误");
	 * System.out.println(e.getMessage()); } }
	 */

	public JDBCUtil(String driver, String url, String username, String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * 建立数据库连接
	 * 
	 * @return 数据库连接
	 * @throws Exception 
	 */
	public Connection getConnection() throws Exception {
		// 加载数据库驱动程序
		Class.forName(driver);
		// 获取连接
		conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	/**
	 * insert update delete SQL语句的执行的统一方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 受影响的行数
	 * @throws Exception 
	 */
	public int executeUpdate(String sql, Object[] params) throws Exception {
		// 受影响的行数
		int affectedLine = 0;

		try {
			// 获得连接
			conn = this.getConnection();
			// 调用SQL
			pst = conn.prepareStatement(sql);

			// 参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}
			/*
			 * 在此 PreparedStatement 对象中执行 SQL 语句， 该语句必须是一个 SQL 数据操作语言（Data
			 * Manipulation Language，DML）语句，比如 INSERT、UPDATE 或 DELETE
			 * 语句；或者是无返回内容的 SQL 语句，比如 DDL 语句。
			 */
			// 执行
			affectedLine = pst.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 释放资源
			closeAll();
		}
		return affectedLine;
	}

	/**
	 * SQL 查询将查询结果直接放入ResultSet中
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 结果集
	 * @throws Exception 
	 */
	private ResultSet executeQueryRS(String sql, Object[] params) throws Exception {
		// 获得连接
		conn = this.getConnection();

		// 调用SQL
		pst = conn.prepareStatement(sql);

		// 参数赋值
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
		}

		// 执行
		rst = pst.executeQuery();

		return rst;
	}

	/**
	 * SQL 查询将查询结果：一行一列
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数数组，若没有参数则为null
	 * @return 结果集
	 * @throws Exception 
	 */
	public Object executeQuerySingle(String sql, Object[] params) throws Exception {
		Object object = null;
		try {
			// 获得连接
			conn = this.getConnection();

			// 调用SQL
			pst = conn.prepareStatement(sql);

			// 参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i + 1, params[i]);
				}
			}

			// 执行
			rst = pst.executeQuery();

			if (rst.next()) {
				object = rst.getObject(1);
			}

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			closeAll();
		}

		return object;
	}

	/**
	 * 获取结果集，并将结果放在List中
	 * 
	 * @param sql
	 *            SQL语句 params 参数，没有则为null
	 * @return List 结果集
	 * @throws Exception 
	 */
	public List<Object> excuteQuery(String sql, Object[] params) throws Exception {
		// 执行SQL获得结果集
		ResultSet rs = executeQueryRS(sql, params);

		// 创建ResultSetMetaData对象
		ResultSetMetaData rsmd = null;

		// 结果集列数
		int columnCount = 0;
		try {
			rsmd = rs.getMetaData();

			// 获得结果集列数
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
			throw new Exception(e1);
		}

		// 创建List
		List<Object> list = new ArrayList<Object>();

		try {
			// 将ResultSet的结果保存到List中
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				list.add(map);// 每一个map代表一条记录，把所有记录存在list中
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 关闭所有资源
			closeAll();
		}

		return list;
	}

	//不带返回值的存储过程
	public void proc1(String sql, Object[] params) throws Exception {
		try {
			Connection conn = this.getConnection();
			// 调用存储过程
			// prepareCall:创建一个 CallableStatement 对象来调用数据库存储过程。
			callableStatement = conn.prepareCall(sql); // 调用格式 {call 存储过程名(参数)}
			// 给参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					callableStatement.setObject(i + 1, params[i]);
				}
			}
			callableStatement.execute();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 释放资源
			closeAll();
		}
		return;
	}
	
	// 带返回值的存储过程
	public Object proc2(String sql, Object[] params, int outParamPos, int SqlType) throws Exception {
		Object object = null;
		try {
			Connection conn = this.getConnection();
			// 调用存储过程
			// prepareCall:创建一个 CallableStatement 对象来调用数据库存储过程。
			callableStatement = conn.prepareCall(sql); // 调用格式 {call 存储过程名(参数)}
			// 给参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					callableStatement.setObject(i + 1, params[i]);
				}
			}

			callableStatement.registerOutParameter(outParamPos, SqlType); // 注册返回类型(sql类型)
			callableStatement.execute();

			object = callableStatement.getObject(outParamPos); // 得到返回值

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 释放资源
			closeAll();
		}

		return object;
	}

	/**
	 * 存储过程带有一个输出参数的方法
	 * 
	 * @param sql
	 *            存储过程语句
	 * @param params
	 *            参数数组
	 * @param outParamPos
	 *            输出参数位置
	 * @param SqlType
	 *            输出参数类型
	 * @return 输出参数的值
	 * @throws Exception 
	 */
	public Object excuteQuery(String sql, Object[] params, int outParamPos, int SqlType) throws Exception {
		Object object = null;
		conn = this.getConnection();
		try {
			// 调用存储过程
			// prepareCall:创建一个 CallableStatement 对象来调用数据库存储过程。
			callableStatement = conn.prepareCall(sql);

			// 给参数赋值
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					callableStatement.setObject(i + 1, params[i]);
				}
			}

			// 注册输出参数 SqlType = java.sql.Types
			callableStatement.registerOutParameter(outParamPos, SqlType);

			// 执行
			callableStatement.execute();

			// 得到输出参数
			object = callableStatement.getObject(outParamPos);

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 释放资源
			closeAll();
		}

		return object;
	}
	/**
	 * Sql 批处理
	 * @param sql
	 * @param paramList
	 * @return 
	 * @throws Exception
	 */
	public LoadTableCommit excuteBatch(LoadTableCommit ltc) throws Exception {
		int index = 0;
		int commitTotalNum = 0;
		int nowTotal = 0;
		int nowCommitTotalTotalNum = 0;
		int blannkTotalNum = 0;
		String [] param = {};
		String dateParam = "";
		LoadTableColunm loadTableColunm = new LoadTableColunm();
		Long startTime = System.currentTimeMillis();
		Long commitTime = System.currentTimeMillis();
		try {
			conn = this.getConnection();
			pst = conn.prepareStatement(ltc.getSql());
			conn.setAutoCommit(false);
			// 参数赋值
			for (String params : ltc.getParamList()) {
				nowTotal ++;
				//当前位置,用于记录空行位置或报错位置
				index = ((ltc.getPageNum()-1)*ltc.getPageSize())+nowTotal;
				//空行记录并跳过
				if(StringUtils.isBlank(params)){
					blannkTotalNum++;
					//共有多少条空行
					ltc.setBlankNum(ltc.getBlankNum()+blannkTotalNum);
					//增加将空行的索引位置记录
					List<Integer> blankIndex = ltc.getBlankIndex();
					blankIndex.add(index);
					ltc.setBlankIndex(blankIndex);
					continue;
				}
				param = params.split(String.valueOf(ltc.getSlp()));
				for (int i = 0; i < param.length; i++) {
					loadTableColunm = ltc.getColumnType().get(i);
					String value = "";
					//如果值为空则并默认值不为空则给默认值
					if(StringUtils.isEmpty(param[i]) && StringUtils.isNotEmpty(loadTableColunm.getDefaultVal())){
						if(loadTableColunm.getDefaultVal().equals("sysdate")){
							value = DateUtils.getTime();
						}else{
							if(StringUtils.isEmpty(param[i]) && StringUtils.isNotBlank(loadTableColunm.getFormat())){
								if(loadTableColunm.getFormat().indexOf("trim") > -1){
									value =  param[i].trim();
								}
								if(loadTableColunm.getFormat().indexOf("lowerCase") > -1){
									value =  param[i].toLowerCase();
								}
								if(loadTableColunm.getFormat().indexOf("upperCase") > -1){
									value =  param[i].toUpperCase();
								}
							}else{
								value =  param[i];
							}
						}
					}else{
						value =  param[i];
					}
					
					switch (loadTableColunm.getType()) {
					case "string":
						pst.setString(i + 1, value);
						break;
					case "number":
						pst.setDouble(i + 1, Double.valueOf(StringUtils.isBlank(value) ? "0.00":value));
						break;
					case "date":
						if(StringUtils.isNotBlank(value) && value.length() == 8){ //如果是8位则为 yyyyMMdd 需要转为 yyyy-MM-dd
							dateParam = value.substring(0,4)+"-"+value.substring(4,6)+"-"+value.substring(6);
						}else if(StringUtils.isNotBlank(value) && value.length() == 14){ //如果是14位则为 yyyyMMddHHmmss 需要转为 yyyy-MM-dd HH:mm:ss
							dateParam = value.substring(0,4)+"-"+value.substring(4,6)+"-"+value.substring(6,8)
								   +" "+value.substring(8,10)+":"+value.substring(10,12)+":"+value.substring(12,14);
						}else{
							dateParam = value;
						}
						pst.setDate(i + 1, Date.valueOf(dateParam));
						break;
					default:
						pst.setObject(i + 1, value);
						break;
					}
				}

				nowCommitTotalTotalNum ++ ;
				commitTotalNum ++ ;
				pst.addBatch();
				if(commitTotalNum % 100000 == 0){
					Long endTime = System.currentTimeMillis();
					pst.executeBatch();
					pst.clearBatch();
					conn.commit();
					System.out.println(
							 "["+ltc.getTableName()+"]"
							+"["+ltc.getLoadTableInfo().getFileName()+"]"
							+"成功提交["+commitTotalNum+"]条"
							+(blannkTotalNum != 0 ? ",空行["+blannkTotalNum+"]":"")
							+",用时：[" + (endTime - commitTime)+"]毫秒"
							+",本次总用时：[" + (endTime - startTime)+"]毫秒"
							+",本次总提交数：[" + nowCommitTotalTotalNum +"]"
							+",总提交数：[" + ltc.getAllCommit() +"]"
					);
					commitTotalNum = 0;
					commitTime = System.currentTimeMillis();
				}
			}
			pst.executeBatch();
			pst.clearBatch();
			conn.commit();
			Long endTime = System.currentTimeMillis();
			if(commitTotalNum != 0){
				System.out.println(
						 "["+ltc.getTableName()+"]"
						+"["+ltc.getLoadTableInfo().getFileName()+"]"
						+"成功提交["+commitTotalNum+"]条"
						+(blannkTotalNum != 0 ? ",空行["+blannkTotalNum+"]":"")
						+",用时：[" + (endTime - commitTime)+"]毫秒"
						+",本次总用时：[" + (endTime - startTime)+"]毫秒"
						+",本次总提交数：[" + nowCommitTotalTotalNum +"]"
						+",总提交数：[" + (ltc.getAllCommit()+nowCommitTotalTotalNum) +"]"
						);
			}
			ltc.setNowCommit(nowCommitTotalTotalNum);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw new Exception(
					 "["+ltc.getTableName()+"]"
					+"["+ltc.getLoadTableInfo().getFileName()+"]"+"报错位置:["+index+"]"+e.getMessage()+e);
		}finally{
			closeAll();
		}
		return ltc;
	}
	
	/**
	 * 关闭所有资源
	 * @throws Exception 
	 */
	private void closeAll() throws Exception {
		// 关闭结果集对象
		if (rst != null) {
			try {
				rst.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				throw new Exception(e);
			}
		}

		// 关闭PreparedStatement对象
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				throw new Exception(e);
			}
		}

		// 关闭CallableStatement 对象
		if (callableStatement != null) {
			try {
				callableStatement.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				throw new Exception(e);
			}
		}

		// 关闭Connection 对象
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				throw new Exception(e);
			}
		}
	}
}
