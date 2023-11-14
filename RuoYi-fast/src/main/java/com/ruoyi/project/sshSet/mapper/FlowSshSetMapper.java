package com.ruoyi.project.sshSet.mapper;

import java.util.List;

import com.ruoyi.project.sshSet.domain.FlowSshSet;

/**
 * 【SSH连接管理】Mapper接口
 * 
 * @author SangYiPing
 * @date 2019-11-07
 */
public interface FlowSshSetMapper 
{
    /**
     * 查询【SSH连接管理】
     * 
     * @param id 【SSH连接管理】ID
     * @return 【SSH连接管理】
     */
    public FlowSshSet selectFlowSshSetById(String id);

    /**
     * 查询【SSH连接管理】列表
     * 
     * @param flowSshSet 【SSH连接管理】
     * @return 【SSH连接管理】集合
     */
    public List<FlowSshSet> selectFlowSshSetList(FlowSshSet flowSshSet);

    /**
     * 新增【SSH连接管理】
     * 
     * @param flowSshSet 【SSH连接管理】
     * @return 结果
     */
    public int insertFlowSshSet(FlowSshSet flowSshSet);

    /**
     * 修改【SSH连接管理】
     * 
     * @param flowSshSet 【SSH连接管理】
     * @return 结果
     */
    public int updateFlowSshSet(FlowSshSet flowSshSet);

    /**
     * 删除【SSH连接管理】
     * 
     * @param id 【SSH连接管理】ID
     * @return 结果
     */
    public int deleteFlowSshSetById(String id);

    /**
     * 批量删除【SSH连接管理】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowSshSetByIds(String[] ids);
}
