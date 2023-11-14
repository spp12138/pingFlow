package com.ruoyi.project.flow.chart.line.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.chart.line.domain.FlowTaskLine;
import com.ruoyi.project.flow.chart.line.mapper.FlowTaskLineMapper;
import com.ruoyi.project.flow.chart.line.service.IFlowTaskLineService;

/**
 * 【流程任务线】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
@Service
public class FlowTaskLineServiceImpl implements IFlowTaskLineService 
{
    @Autowired
    private FlowTaskLineMapper flowTaskLineMapper;

    /**
     * 查询【流程任务线】
     * 
     * @param flowid 【流程任务线】ID
     * @return 【流程任务线】
     */
    @Override
    public FlowTaskLine selectFlowTaskLineById(String flowid)
    {
        return flowTaskLineMapper.selectFlowTaskLineById(flowid);
    }

    /**
     * 查询【流程任务线】列表
     * 
     * @param flowTaskLine 【流程任务线】
     * @return 【流程任务线】
     */
    @Override
    public List<FlowTaskLine> selectFlowTaskLineList(FlowTaskLine flowTaskLine)
    {
        return flowTaskLineMapper.selectFlowTaskLineList(flowTaskLine);
    }

    /**
     * 新增【流程任务线】
     * 
     * @param flowTaskLine 【流程任务线】
     * @return 结果
     */
    @Override
    public int insertFlowTaskLine(FlowTaskLine flowTaskLine)
    {
        flowTaskLine.setCreateTime(DateUtils.getNowDate());
        return flowTaskLineMapper.insertFlowTaskLine(flowTaskLine);
    }

    /**
     * 修改【流程任务线】
     * 
     * @param flowTaskLine 【流程任务线】
     * @return 结果
     */
    @Override
    public int updateFlowTaskLine(FlowTaskLine flowTaskLine)
    {
        flowTaskLine.setUpdateTime(DateUtils.getNowDate());
        return flowTaskLineMapper.updateFlowTaskLine(flowTaskLine);
    }

    /**
     * 删除【流程任务线】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowTaskLineByIds(String ids)
    {
        return flowTaskLineMapper.deleteFlowTaskLineByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【流程任务线】信息
     * 
     * @param flowid 【流程任务线】ID
     * @return 结果
     */
    public int deleteFlowTaskLineById(String flowid)
    {
        return flowTaskLineMapper.deleteFlowTaskLineById(flowid);
    }
}
