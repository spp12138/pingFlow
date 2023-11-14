package com.ruoyi.project.flow.subTask.service;

import java.util.List;

import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;

/**
 * 【任务详情】Service接口
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
public interface IFlowTaskSubInfoService 
{
    /**
     * 查询【任务详情】
     * 
     * @param flowId 【任务详情】ID
     * @return 【任务详情】
     */
    public FlowTaskSubInfo selectFlowTaskSubInfoById(String flowId);

    /**
     * 查询【任务详情】列表
     * 
     * @param flowTaskSubInfo 【任务详情】
     * @return 【任务详情】集合
     */
    public List<FlowTaskSubInfo> selectFlowTaskSubInfoList(FlowTaskSubInfo flowTaskSubInfo);

    /**
     * 新增【任务详情】
     * 
     * @param flowTaskSubInfo 【任务详情】
     * @return 结果
     */
    public int insertFlowTaskSubInfo(FlowTaskSubInfo flowTaskSubInfo);

    /**
     * 修改【任务详情】
     * 
     * @param flowTaskSubInfo 【任务详情】
     * @return 结果
     */
    public int updateFlowTaskSubInfo(FlowTaskSubInfo flowTaskSubInfo);

    /**
     * 批量删除【任务详情】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowTaskSubInfoByIds(String ids);

    /**
     * 删除【任务详情】信息
     * 
     * @param flowId 【任务详情】ID
     * @return 结果
     */
    public int deleteFlowTaskSubInfoById(String flowId);
}
