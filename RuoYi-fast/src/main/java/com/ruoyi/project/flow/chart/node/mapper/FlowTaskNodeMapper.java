package com.ruoyi.project.flow.chart.node.mapper;

import java.util.List;

import com.ruoyi.project.flow.chart.node.domain.FlowTaskNode;

/**
 * 【流程任务节点】Mapper接口
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
public interface FlowTaskNodeMapper 
{
    /**
     * 查询【流程任务节点】
     * 
     * @param flowid 【流程任务节点】ID
     * @return 【流程任务节点】
     */
    public FlowTaskNode selectFlowTaskNodeById(String flowid);

    /**
     * 查询【流程任务节点】列表
     * 
     * @param flowTaskNode 【流程任务节点】
     * @return 【流程任务节点】集合
     */
    public List<FlowTaskNode> selectFlowTaskNodeList(FlowTaskNode flowTaskNode);

    /**
     * 新增【流程任务节点】
     * 
     * @param flowTaskNode 【流程任务节点】
     * @return 结果
     */
    public int insertFlowTaskNode(FlowTaskNode flowTaskNode);

    /**
     * 修改【流程任务节点】
     * 
     * @param flowTaskNode 【流程任务节点】
     * @return 结果
     */
    public int updateFlowTaskNode(FlowTaskNode flowTaskNode);

    /**
     * 删除【流程任务节点】
     * 
     * @param flowid 【流程任务节点】ID
     * @return 结果
     */
    public int deleteFlowTaskNodeById(String flowid);

    /**
     * 批量删除【流程任务节点】
     * 
     * @param flowids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowTaskNodeByIds(String[] flowids);
}
