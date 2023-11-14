package com.ruoyi.project.flow.task.util.po;

import java.util.List;

import com.ruoyi.project.flow.chart.node.domain.FlowTaskNode;


/**
 * 【任务实体类】节点信息
 * @author SangYiPing
 * @date 2019-10-27
 */
public class FlowTaskNodeView extends FlowTaskNode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> fromNodeId;
	private List<String> toNodeId;
	
	public List<String> getFromNodeId() {
		return fromNodeId;
	}
	public void setFromNodeId(List<String> fromNodeId) {
		this.fromNodeId = fromNodeId;
	}
	public List<String> getToNodeId() {
		return toNodeId;
	}
	public void setToNodeId(List<String> toNodeId) {
		this.toNodeId = toNodeId;
	}
	
}
