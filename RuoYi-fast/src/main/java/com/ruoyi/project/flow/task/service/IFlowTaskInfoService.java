package com.ruoyi.project.flow.task.service;

import java.util.List;

import com.ruoyi.project.flow.task.domain.FlowTaskInfo;

/**
 * 【流程任务】Service接口
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
public interface IFlowTaskInfoService 
{
    /**
     * 查询【流程任务】
     * 
     * @param flowId 【流程任务】ID
     * @return 【流程任务】
     */
    public FlowTaskInfo selectFlowTaskInfoById(String flowId);

    /**
     * 查询【流程任务】列表
     * 
     * @param flowTaskInfo 【流程任务】
     * @return 【流程任务】集合
     */
    public List<FlowTaskInfo> selectFlowTaskInfoList(FlowTaskInfo flowTaskInfo);

    /**
     * 新增【流程任务】
     * 
     * @param flowTaskInfo 【流程任务】
     * @return 结果
     */
    public int insertFlowTaskInfo(FlowTaskInfo flowTaskInfo);

    /**
     * 修改【流程任务】
     * 
     * @param flowTaskInfo 【流程任务】
     * @return 结果
     */
    public int updateFlowTaskInfo(FlowTaskInfo flowTaskInfo);

    /**
     * 批量删除【流程任务】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowTaskInfoByIds(String ids);

    /**
     * 删除【流程任务】信息
     * 
     * @param flowId 【流程任务】ID
     * @return 结果
     */
    public int deleteFlowTaskInfoById(String flowId);
}
