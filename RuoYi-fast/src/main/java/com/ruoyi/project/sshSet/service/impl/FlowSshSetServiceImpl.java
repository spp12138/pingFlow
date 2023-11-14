package com.ruoyi.project.sshSet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.sshSet.domain.FlowSshSet;
import com.ruoyi.project.sshSet.mapper.FlowSshSetMapper;
import com.ruoyi.project.sshSet.service.IFlowSshSetService;

/**
 * 【SSH连接管理】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-11-07
 */
@Service
public class FlowSshSetServiceImpl implements IFlowSshSetService 
{
    @Autowired
    private FlowSshSetMapper flowSshSetMapper;

    /**
     * 查询【SSH连接管理】
     * 
     * @param id 【SSH连接管理】ID
     * @return 【SSH连接管理】
     */
    @Override
    public FlowSshSet selectFlowSshSetById(String id)
    {
        return flowSshSetMapper.selectFlowSshSetById(id);
    }

    /**
     * 查询【SSH连接管理】列表
     * 
     * @param flowSshSet 【SSH连接管理】
     * @return 【SSH连接管理】
     */
    @Override
    public List<FlowSshSet> selectFlowSshSetList(FlowSshSet flowSshSet)
    {
        return flowSshSetMapper.selectFlowSshSetList(flowSshSet);
    }

    /**
     * 新增【SSH连接管理】
     * 
     * @param flowSshSet 【SSH连接管理】
     * @return 结果
     */
    @Override
    public int insertFlowSshSet(FlowSshSet flowSshSet)
    {
        flowSshSet.setCreateTime(DateUtils.getNowDate());
        return flowSshSetMapper.insertFlowSshSet(flowSshSet);
    }

    /**
     * 修改【SSH连接管理】
     * 
     * @param flowSshSet 【SSH连接管理】
     * @return 结果
     */
    @Override
    public int updateFlowSshSet(FlowSshSet flowSshSet)
    {
        flowSshSet.setUpdateTime(DateUtils.getNowDate());
        return flowSshSetMapper.updateFlowSshSet(flowSshSet);
    }

    /**
     * 删除【SSH连接管理】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowSshSetByIds(String ids)
    {
        return flowSshSetMapper.deleteFlowSshSetByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【SSH连接管理】信息
     * 
     * @param id 【SSH连接管理】ID
     * @return 结果
     */
    public int deleteFlowSshSetById(String id)
    {
        return flowSshSetMapper.deleteFlowSshSetById(id);
    }
}
