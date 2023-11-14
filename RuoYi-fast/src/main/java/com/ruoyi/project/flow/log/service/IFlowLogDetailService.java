package com.ruoyi.project.flow.log.service;

import java.util.List;

import com.ruoyi.project.flow.log.domain.FlowLogDetail;

/**
 * 调度日志详情Service接口
 * 
 * @author SangYiPing
 * @date 2020-01-16
 */
public interface IFlowLogDetailService 
{
    /**
     * 查询调度日志详情
     * 
     * @param id 调度日志详情ID
     * @return 调度日志详情
     */
    public FlowLogDetail selectFlowLogDetailById(String id);

    /**
     * 查询调度日志详情列表
     * 
     * @param flowLogDetail 调度日志详情
     * @return 调度日志详情集合
     */
    public List<FlowLogDetail> selectFlowLogDetailList(FlowLogDetail flowLogDetail);

    /**
     * 新增调度日志详情
     * 
     * @param flowLogDetail 调度日志详情
     * @return 结果
     */
    public int insertFlowLogDetail(FlowLogDetail flowLogDetail);

    /**
     * 修改调度日志详情
     * 
     * @param flowLogDetail 调度日志详情
     * @return 结果
     */
    public int updateFlowLogDetail(FlowLogDetail flowLogDetail);

    /**
     * 批量删除调度日志详情
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowLogDetailByIds(String ids);

    /**
     * 删除调度日志详情信息
     * 
     * @param id 调度日志详情ID
     * @return 结果
     */
    public int deleteFlowLogDetailById(String id);
    

	/**
	 * 更新日志详情
	 * @param logDetailById
	 * @param stauts  状态 0 新建 , 1 开始, 2 完成, 3 暂停  , 4 异常
	 * @param remark
	 */
    public void updateDetailStauts(FlowLogDetail logDetailById,String stauts,String ... remark) ;
}
