package com.ruoyi.project.flow.chart.area.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.chart.area.domain.FlowTaskArea;
import com.ruoyi.project.flow.chart.area.mapper.FlowTaskAreaMapper;
import com.ruoyi.project.flow.chart.area.service.IFlowTaskAreaService;

/**
 * 【流程任务Area】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
@Service
public class FlowTaskAreaServiceImpl implements IFlowTaskAreaService 
{
    @Autowired
    private FlowTaskAreaMapper flowTaskAreaMapper;

    /**
     * 查询【流程任务Area】
     * 
     * @param flowid 【流程任务Area】ID
     * @return 【流程任务Area】
     */
    @Override
    public FlowTaskArea selectFlowTaskAreaById(String flowid)
    {
        return flowTaskAreaMapper.selectFlowTaskAreaById(flowid);
    }

    /**
     * 查询【流程任务Area】列表
     * 
     * @param flowTaskArea 【流程任务Area】
     * @return 【流程任务Area】
     */
    @Override
    public List<FlowTaskArea> selectFlowTaskAreaList(FlowTaskArea flowTaskArea)
    {
        return flowTaskAreaMapper.selectFlowTaskAreaList(flowTaskArea);
    }

    /**
     * 新增【流程任务Area】
     * 
     * @param flowTaskArea 【流程任务Area】
     * @return 结果
     */
    @Override
    public int insertFlowTaskArea(FlowTaskArea flowTaskArea)
    {
        flowTaskArea.setCreateTime(DateUtils.getNowDate());
        return flowTaskAreaMapper.insertFlowTaskArea(flowTaskArea);
    }

    /**
     * 修改【流程任务Area】
     * 
     * @param flowTaskArea 【流程任务Area】
     * @return 结果
     */
    @Override
    public int updateFlowTaskArea(FlowTaskArea flowTaskArea)
    {
        flowTaskArea.setUpdateTime(DateUtils.getNowDate());
        return flowTaskAreaMapper.updateFlowTaskArea(flowTaskArea);
    }

    /**
     * 删除【流程任务Area】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowTaskAreaByIds(String ids)
    {
        return flowTaskAreaMapper.deleteFlowTaskAreaByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【流程任务Area】信息
     * 
     * @param flowid 【流程任务Area】ID
     * @return 结果
     */
    public int deleteFlowTaskAreaById(String flowid)
    {
        return flowTaskAreaMapper.deleteFlowTaskAreaById(flowid);
    }
}
