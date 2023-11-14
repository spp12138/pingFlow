package com.ruoyi.project.flow.info.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.info.domain.FlowInfo;
import com.ruoyi.project.flow.info.mapper.FlowInfoMapper;
import com.ruoyi.project.flow.info.service.IFlowInfoService;

/**
 * 调度管理Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-10-26
 */
@Service
public class FlowInfoServiceImpl implements IFlowInfoService 
{
    @Autowired
    private FlowInfoMapper flowInfoMapper;

    /**
     * 查询调度管理
     * 
     * @param flowId 调度管理ID
     * @return 调度管理
     */
    @Override
    public FlowInfo selectFlowInfoById(String flowId)
    {
        return flowInfoMapper.selectFlowInfoById(flowId);
    }

    /**
     * 查询调度管理列表
     * 
     * @param flowInfo 调度管理
     * @return 调度管理
     */
    @Override
    public List<FlowInfo> selectFlowInfoList(FlowInfo flowInfo)
    {
        return flowInfoMapper.selectFlowInfoList(flowInfo);
    }

    /**
     * 新增调度管理
     * 
     * @param flowInfo 调度管理
     * @return 结果
     */
    @Override
    public int insertFlowInfo(FlowInfo flowInfo)
    {
        flowInfo.setCreateTime(DateUtils.getNowDate());
        return flowInfoMapper.insertFlowInfo(flowInfo);
    }

    /**
     * 修改调度管理
     * 
     * @param flowInfo 调度管理
     * @return 结果
     */
    @Override
    public int updateFlowInfo(FlowInfo flowInfo)
    {
        flowInfo.setUpdateTime(DateUtils.getNowDate());
        return flowInfoMapper.updateFlowInfo(flowInfo);
    }

    /**
     * 删除调度管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowInfoByIds(String ids)
    {
        return flowInfoMapper.deleteFlowInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除调度管理信息
     * 
     * @param flowId 调度管理ID
     * @return 结果
     */
    public int deleteFlowInfoById(String flowId)
    {
        return flowInfoMapper.deleteFlowInfoById(flowId);
    }

	@Override
	public Object checkFlowNameUnique(FlowInfo flowInfo) {
		FlowInfo flowName = flowInfoMapper.checkFlowNameUnique(flowInfo.getFlowName());
        if (StringUtils.isNotNull(flowName))
        {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
	}
}
