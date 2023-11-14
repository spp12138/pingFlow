package com.ruoyi.project.flow.log.mapper;

import java.util.List;

import com.ruoyi.project.flow.log.domain.FlowLog;

/**
 * 调度日志Mapper接口
 * 
 * @author SangYiPing
 * @date 2020-01-16
 */
public interface FlowLogMapper 
{
    /**
     * 查询调度日志
     * 
     * @param id 调度日志ID
     * @return 调度日志
     */
    public FlowLog selectFlowLogById(String id);

    /**
     * 查询调度日志列表
     * 
     * @param flowLog 调度日志
     * @return 调度日志集合
     */
    public List<FlowLog> selectFlowLogList(FlowLog flowLog);

    /**
     * 新增调度日志
     * 
     * @param flowLog 调度日志
     * @return 结果
     */
    public int insertFlowLog(FlowLog flowLog);

    /**
     * 修改调度日志
     * 
     * @param flowLog 调度日志
     * @return 结果
     */
    public int updateFlowLog(FlowLog flowLog);

    /**
     * 删除调度日志
     * 
     * @param id 调度日志ID
     * @return 结果
     */
    public int deleteFlowLogById(String id);

    /**
     * 批量删除调度日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowLogByIds(String[] ids);
}
