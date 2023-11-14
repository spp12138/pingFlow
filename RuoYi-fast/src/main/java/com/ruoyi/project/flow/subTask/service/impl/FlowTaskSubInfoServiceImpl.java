package com.ruoyi.project.flow.subTask.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.subTask.mapper.FlowTaskSubInfoMapper;
import com.ruoyi.project.flow.subTask.service.IFlowTaskSubInfoService;
import com.ruoyi.project.flow.task.util.FlowUtil;

/**
 * 【任务详情】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Service
public class FlowTaskSubInfoServiceImpl implements IFlowTaskSubInfoService 
{
    @Autowired
    private FlowTaskSubInfoMapper flowTaskSubInfoMapper;

    /**
     * 查询【任务详情】
     * 
     * @param flowId 【任务详情】ID
     * @return 【任务详情】
     */
    @Override
    public FlowTaskSubInfo selectFlowTaskSubInfoById(String flowId)
    {
        return flowTaskSubInfoMapper.selectFlowTaskSubInfoById(flowId);
    }

    /**
     * 查询【任务详情】列表
     * 
     * @param flowTaskSubInfo 【任务详情】
     * @return 【任务详情】
     */
    @Override
    public List<FlowTaskSubInfo> selectFlowTaskSubInfoList(FlowTaskSubInfo flowTaskSubInfo)
    {
        return flowTaskSubInfoMapper.selectFlowTaskSubInfoList(flowTaskSubInfo);
    }

    /**
     * 新增【任务详情】
     * 
     * @param flowTaskSubInfo 【任务详情】
     * @return 结果
     */
    @Override
    public int insertFlowTaskSubInfo(FlowTaskSubInfo flowTaskSubInfo)
    {
        flowTaskSubInfo.setCreateTime(DateUtils.getNowDate());
        deleteFlowTaskSubInfoBySubTaskId(flowTaskSubInfo.getSubTaskId());
        flowTaskSubInfo = FlowUtil.setFlowTaskSubSrcIndex(flowTaskSubInfo);
        return flowTaskSubInfoMapper.insertFlowTaskSubInfo(flowTaskSubInfo);
    }

    /**
     * 修改【任务详情】
     * 
     * @param flowTaskSubInfo 【任务详情】
     * @return 结果
     */
    @Override
    public int updateFlowTaskSubInfo(FlowTaskSubInfo flowTaskSubInfo)
    {
        flowTaskSubInfo.setUpdateTime(DateUtils.getNowDate());
        return flowTaskSubInfoMapper.updateFlowTaskSubInfo(flowTaskSubInfo);
    }

    /**
     * 删除【任务详情】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowTaskSubInfoByIds(String ids)
    {
        return flowTaskSubInfoMapper.deleteFlowTaskSubInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【任务详情】信息
     * 
     * @param flowId 【任务详情】ID
     * @return 结果
     */
    public int deleteFlowTaskSubInfoById(String flowId)
    {
        return flowTaskSubInfoMapper.deleteFlowTaskSubInfoById(flowId);
    }
    /**
     * 删除【任务详情】信息
     * 
     * @param subTaskId 【任务详情】ID
     * @return 结果
     */
    public int deleteFlowTaskSubInfoBySubTaskId(String subTaskId)
    {
    	return flowTaskSubInfoMapper.deleteFlowTaskSubInfoBySubTaskId(subTaskId);
    }
}
