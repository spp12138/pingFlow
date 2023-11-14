package com.ruoyi.project.flow.info.mapper;

import java.util.List;

import com.ruoyi.project.flow.info.domain.FlowInfo;

/**
 * 调度管理Mapper接口
 * 
 * @author SangYiPing
 * @date 2019-10-26
 */
public interface FlowInfoMapper 
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
     * 删除调度管理
     * 
     * @param flowId 调度管理ID
     * @return 结果
     */
    public int deleteFlowInfoById(String flowId);

    /**
     * 批量删除调度管理
     * 
     * @param flowIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowInfoByIds(String[] flowIds);

	public FlowInfo checkFlowNameUnique(String flowName);
}
