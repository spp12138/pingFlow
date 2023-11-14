package com.ruoyi.project.flow.chart.line.mapper;

import java.util.List;

import com.ruoyi.project.flow.chart.line.domain.FlowTaskLine;

/**
 * 【流程任务线】Mapper接口
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
public interface FlowTaskLineMapper 
{
    /**
     * 查询【流程任务线】
     * 
     * @param flowid 【流程任务线】ID
     * @return 【流程任务线】
     */
    public FlowTaskLine selectFlowTaskLineById(String flowid);

    /**
     * 查询【流程任务线】列表
     * 
     * @param flowTaskLine 【流程任务线】
     * @return 【流程任务线】集合
     */
    public List<FlowTaskLine> selectFlowTaskLineList(FlowTaskLine flowTaskLine);

    /**
     * 新增【流程任务线】
     * 
     * @param flowTaskLine 【流程任务线】
     * @return 结果
     */
    public int insertFlowTaskLine(FlowTaskLine flowTaskLine);

    /**
     * 修改【流程任务线】
     * 
     * @param flowTaskLine 【流程任务线】
     * @return 结果
     */
    public int updateFlowTaskLine(FlowTaskLine flowTaskLine);

    /**
     * 删除【流程任务线】
     * 
     * @param flowid 【流程任务线】ID
     * @return 结果
     */
    public int deleteFlowTaskLineById(String flowid);

    /**
     * 批量删除【流程任务线】
     * 
     * @param flowids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowTaskLineByIds(String[] flowids);
}
