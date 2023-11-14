package com.ruoyi.project.flow.task.util.vo;

import java.util.Map;

import com.ruoyi.project.flow.chart.area.domain.FlowTaskArea;
import com.ruoyi.project.flow.chart.line.domain.FlowTaskLine;
import com.ruoyi.project.flow.chart.node.domain.FlowTaskNode;

public class FlowTaskView {
	
	private String title;
	private Map<String,FlowTaskNode> nodes;
	private Map<String,FlowTaskLine> lines;
	private Map<String,FlowTaskArea> areas;
	private int initNum;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Map<String, FlowTaskNode> getNodes() {
		return nodes;
	}
	public void setNodes(Map<String, FlowTaskNode> nodes) {
		this.nodes = nodes;
	}
	public Map<String, FlowTaskLine> getLines() {
		return lines;
	}
	public void setLines(Map<String, FlowTaskLine> lines) {
		this.lines = lines;
	}
	public Map<String, FlowTaskArea> getAreas() {
		return areas;
	}
	public void setAreas(Map<String, FlowTaskArea> areas) {
		this.areas = areas;
	}
	public int getInitNum() {
		return initNum;
	}
	public void setInitNum(int initNum) {
		this.initNum = initNum;
	}

	
	
}
