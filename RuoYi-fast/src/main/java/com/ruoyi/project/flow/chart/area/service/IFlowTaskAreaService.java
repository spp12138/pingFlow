package com.ruoyi.project.flow.chart.area.service;

import java.util.List;

import com.ruoyi.project.flow.chart.area.domain.FlowTaskArea;

/**
 * 【流程任务Area】Service接口
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
public interface IFlowTaskAreaService 
{
    /**
     * 查询【流程任务Area】
     * 
     * @param flowid 【流程任务Area】ID
     * @return 【流程任务Area】
     */
    public FlowTaskArea selectFlowTaskAreaById(String flowid);

    /**
     * 查询【流程任务Area】列表
     * 
     * @param flowTaskArea 【流程任务Area】
     * @return 【流程任务Area】集合
     */
    public List<FlowTaskArea> selectFlowTaskAreaList(FlowTaskArea flowTaskArea);

    /**
     * 新增【流程任务Area】
     * 
     * @param flowTaskArea 【流程任务Area】
     * @return 结果
     */
    public int insertFlowTaskArea(FlowTaskArea flowTaskArea);

    /**
     * 修改【流程任务Area】
     * 
     * @param flowTaskArea 【流程任务Area】
     * @return 结果
     */
    public int updateFlowTaskArea(FlowTaskArea flowTaskArea);

    /**
     * 批量删除【流程任务Area】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowTaskAreaByIds(String ids);

    /**
     * 删除【流程任务Area】信息
     * 
     * @param flowid 【流程任务Area】ID
     * @return 结果
     */
    public int deleteFlowTaskAreaById(String flowid);
}
