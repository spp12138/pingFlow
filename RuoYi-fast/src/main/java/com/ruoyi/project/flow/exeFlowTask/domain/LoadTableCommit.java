package com.ruoyi.project.flow.exeFlowTask.domain;

import java.util.ArrayList;
import java.util.List;

public class LoadTableCommit {
	private int pageNum;
	private int pageSize;
	private int blankNum;
	private List<Integer> blankIndex;
	private int allCommit;
	private int nowCommit;
	private String sql;
	private List<String> paramList;
	private String slp;
	private List<LoadTableColunm> columnType;
	private String tableName;
	private LoadTable loadTableInfo;
	
	
	public LoadTable getLoadTableInfo() {
		return loadTableInfo;
	}
	public void setLoadTableInfo(LoadTable loadTableInfo) {
		this.loadTableInfo = loadTableInfo;
	}
	public int getNowCommit() {
		return nowCommit;
	}
	public void setNowCommit(int nowCommit) {
		this.nowCommit = nowCommit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public List<String> getParamList() {
		return paramList;
	}
	public void setParamList(List<String> paramList) {
		this.paramList = paramList;
	}
	public String getSlp() {
		return slp;
	}
	public void setSlp(String slp) {
		this.slp = slp;
	}
	public List<LoadTableColunm> getColumnType() {
		return columnType;
	}
	public void setColumnType(List<LoadTableColunm> columnType) {
		this.columnType = columnType;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getBlankNum() {
		return blankNum;
	}
	public void setBlankNum(int blankNum) {
		this.blankNum = blankNum;
	}
	public List<Integer> getBlankIndex() {
		return blankIndex == null ? new ArrayList<Integer>() : blankIndex;
	}
	public void setBlankIndex(List<Integer> blankIndex) {
		this.blankIndex = blankIndex;
	}
	public int getAllCommit() {
		return allCommit;
	}
	public void setAllCommit(int allCommit) {
		this.allCommit = allCommit;
	}
	
	
}
