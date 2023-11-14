package com.ruoyi.project.flow.exeFlowTask.domain;

public class LoadTableColunm {
	
	String name;//字段名称
	String type;//类型 number string date
	String defaultVal; //如果为空取默认值 或 sysdate
	String format; //trim lowerCase upperCase
	
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
