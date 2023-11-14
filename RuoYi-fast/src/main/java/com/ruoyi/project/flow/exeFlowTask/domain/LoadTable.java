package com.ruoyi.project.flow.exeFlowTask.domain;

import java.util.List;

public class LoadTable {
	private String tableName;
	private String splitSign;
	private int pageSize;
	private String filePath;
	private String fileName;
	private List<LoadTableColunm> tableColunm;
	private String path;
	private String loadMode;//truncate append
	
	
	public String getLoadMode() {
		return loadMode;
	}
	public void setLoadMode(String loadMode) {
		this.loadMode = loadMode;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSplitSign() {
		return splitSign;
	}
	public void setSplitSign(String splitSign) {
		this.splitSign = splitSign;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<LoadTableColunm> getTableColunm() {
		return tableColunm;
	}
	public void setTableColunm(List<LoadTableColunm> tableColunm) {
		this.tableColunm = tableColunm;
	}
	
	
}
