package com.ruoyi.project.flow.task.util.po;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.flow.chart.area.domain.FlowTaskArea;
import com.ruoyi.project.flow.chart.line.domain.FlowTaskLine;

/**
 * 【任务实体类】
 * 其中包含
 * title名称
 * nodes节点信息
 * lines线段信息
 * areas区域信息
 * initNum数量
 * @author SangYiPing
 * @date 2019-10-27
 */
public class FlowTaskBean {
	
	private String flowId;
	private String title;
	private Map<String,FlowTaskNodeView> nodes;
	private Map<String,FlowTaskLine> lines;
	private Map<String,FlowTaskArea> areas;
	private List<FlowTaskNodeView> nodesList;
	private List<FlowTaskLine> linesList;
	private List<FlowTaskArea> areasList;
	private int initNum;
	private String flowJson;
	
	
	
	
	public String getFlowId() {
		return flowId;
	}




	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public Map<String, FlowTaskNodeView> getNodes() {
		return nodes;
	}




	public void setNodes(Map<String, FlowTaskNodeView> nodes) {
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




	public List<FlowTaskNodeView> getNodesList() {
		return nodesList;
	}




	public void setNodesList(List<FlowTaskNodeView> nodesList) {
		this.nodesList = nodesList;
	}




	public List<FlowTaskLine> getLinesList() {
		return linesList;
	}




	public void setLinesList(List<FlowTaskLine> linesList) {
		this.linesList = linesList;
	}




	public List<FlowTaskArea> getAreasList() {
		return areasList;
	}




	public void setAreasList(List<FlowTaskArea> areasList) {
		this.areasList = areasList;
	}




	public int getInitNum() {
		return initNum;
	}




	public void setInitNum(int initNum) {
		this.initNum = initNum;
	}




	public String getFlowJson() {
		return flowJson;
	}




	public void setFlowJson(String flowJson) {
		this.flowJson = flowJson;
	}




	@Override
	public String toString() {
		return "FlowTaskBean [flowId=" + flowId + ", title=" + title
				+ ", nodes=" + nodes + ", lines=" + lines + ", areas=" + areas
				+ ", nodesList=" + nodesList + ", linesList=" + linesList
				+ ", areasList=" + areasList + ", initNum=" + initNum
				+ ", flowJson=" + flowJson + "]";
	}
	 
	
	
	
}
