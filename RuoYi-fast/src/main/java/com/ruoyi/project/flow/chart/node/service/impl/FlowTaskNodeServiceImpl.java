package com.ruoyi.project.flow.chart.node.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.chart.node.domain.FlowTaskNode;
import com.ruoyi.project.flow.chart.node.mapper.FlowTaskNodeMapper;
import com.ruoyi.project.flow.chart.node.service.IFlowTaskNodeService;

/**
 * 【流程任务节点】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
@Service
public class FlowTaskNodeServiceImpl implements IFlowTaskNodeService 
{
    @Autowired
    private FlowTaskNodeMapper flowTaskNodeMapper;

    /**
     * 查询【流程任务节点】
     * 
     * @param flowid 【流程任务节点】ID
     * @return 【流程任务节点】
     */
    @Override
    public FlowTaskNode selectFlowTaskNodeById(String flowid)
    {
        return flowTaskNodeMapper.selectFlowTaskNodeById(flowid);
    }

    /**
     * 查询【流程任务节点】列表
     * 
     * @param flowTaskNode 【流程任务节点】
     * @return 【流程任务节点】
     */
    @Override
    public List<FlowTaskNode> selectFlowTaskNodeList(FlowTaskNode flowTaskNode)
    {
        return flowTaskNodeMapper.selectFlowTaskNodeList(flowTaskNode);
    }

    /**
     * 新增【流程任务节点】
     * 
     * @param flowTaskNode 【流程任务节点】
     * @return 结果
     */
    @Override
    public int insertFlowTaskNode(FlowTaskNode flowTaskNode)
    {
        flowTaskNode.setCreateTime(DateUtils.getNowDate());
        return flowTaskNodeMapper.insertFlowTaskNode(flowTaskNode);
    }

    /**
     * 修改【流程任务节点】
     * 
     * @param flowTaskNode 【流程任务节点】
     * @return 结果
     */
    @Override
    public int updateFlowTaskNode(FlowTaskNode flowTaskNode)
    {
        flowTaskNode.setUpdateTime(DateUtils.getNowDate());
        return flowTaskNodeMapper.updateFlowTaskNode(flowTaskNode);
    }

    /**
     * 删除【流程任务节点】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowTaskNodeByIds(String ids)
    {
        return flowTaskNodeMapper.deleteFlowTaskNodeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【流程任务节点】信息
     * 
     * @param flowid 【流程任务节点】ID
     * @return 结果
     */
    public int deleteFlowTaskNodeById(String flowid)
    {
        return flowTaskNodeMapper.deleteFlowTaskNodeById(flowid);
    }
}
