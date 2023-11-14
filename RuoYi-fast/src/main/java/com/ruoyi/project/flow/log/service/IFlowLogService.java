package com.ruoyi.project.flow.log.service;

import java.util.List;

import com.ruoyi.project.flow.log.domain.FlowLog;

/**
 * 调度日志Service接口
 * 
 * @author SangYiPing
 * @date 2020-01-16
 */
public interface IFlowLogService 
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
     * 批量删除调度日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowLogByIds(String ids);

    /**
     * 删除调度日志信息
     * 
     * @param id 调度日志ID
     * @return 结果
     */
    public int deleteFlowLogById(String id);

	/**
	 * 更新日志详情
	 * @param logDetailById
	 * @param stauts  状态 0 新建 , 1 开始, 2 完成, 3 暂停  , 4 异常
	 * @param remark
	 */
	public void updateStauts(String sl_id,String stauts,String ... remark) ;
}
