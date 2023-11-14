package com.ruoyi.project.flow.info.service;

import java.util.List;

import com.ruoyi.project.flow.info.domain.FlowInfo;

/**
 * 调度管理Service接口
 * 
 * @author SangYiPing
 * @date 2019-10-26
 */
public interface IFlowInfoService 
{
    /**
     * 查询调度管理
     * 
     * @param flowId 调度管理ID
     * @return 调度管理
     */
    public FlowInfo selectFlowInfoById(String flowId);

    /**
     * 查询调度管理列表
     * 
     * @param flowInfo 调度管理
     * @return 调度管理集合
     */
    public List<FlowInfo> selectFlowInfoList(FlowInfo flowInfo);

    /**
     * 新增调度管理
     * 
     * @param flowInfo 调度管理
     * @return 结果
     */
    public int insertFlowInfo(FlowInfo flowInfo);

    /**
     * 修改调度管理
     * 
     * @param flowInfo 调度管理
     * @return 结果
     */
    public int updateFlowInfo(FlowInfo flowInfo);

    /**
     * 批量删除调度管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowInfoByIds(String ids);

    /**
     * 删除调度管理信息
     * 
     * @param flowId 调度管理ID
     * @return 结果
     */
    public int deleteFlowInfoById(String flowId);

	public Object checkFlowNameUnique(FlowInfo flowInfo);
}
