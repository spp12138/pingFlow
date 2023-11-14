package com.ruoyi.project.flow.param.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.param.domain.FlowParamInfo;
import com.ruoyi.project.flow.param.mapper.FlowParamInfoMapper;
import com.ruoyi.project.flow.param.service.IFlowParamInfoService;

/**
 * 【流程参数】Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-11-05
 */
@Service
public class FlowParamInfoServiceImpl implements IFlowParamInfoService {
	@Autowired
	private FlowParamInfoMapper flowParamInfoMapper;

	/**
	 * 查询【流程参数】
	 * 
	 * @param flowId
	 *            【流程参数】ID
	 * @return 【流程参数】
	 */
	@Override
	public FlowParamInfo selectFlowParamInfoById(String flowId) {
		return flowParamInfoMapper.selectFlowParamInfoById(flowId);
	}

	/**
	 * 查询【流程参数】列表
	 * 
	 * @param flowParamInfo
	 *            【流程参数】
	 * @return 【流程参数】
	 */
	@Override
	public List<FlowParamInfo> selectFlowParamInfoList(FlowParamInfo flowParamInfo) {
		return flowParamInfoMapper.selectFlowParamInfoList(flowParamInfo);
	}

	/**
	 * 新增【流程参数】
	 * 
	 * @param flowParamInfo
	 *            【流程参数】
	 * @return 结果
	 */
	@Override
	public int insertFlowParamInfo(FlowParamInfo flowParamInfo) {
		flowParamInfo.setId(UUID.randomUUID().toString().replace("-", ""));
		flowParamInfo.setCreateTime(DateUtils.getNowDate());
		return flowParamInfoMapper.insertFlowParamInfo(flowParamInfo);
	}

	/**
	 * 修改【流程参数】
	 * 
	 * @param flowParamInfo
	 *            【流程参数】
	 * @return 结果
	 */
	@Override
	public int updateFlowParamInfo(FlowParamInfo flowParamInfo) {
		flowParamInfo.setUpdateTime(DateUtils.getNowDate());
		return flowParamInfoMapper.updateFlowParamInfo(flowParamInfo);
	}

	/**
	 * 删除【流程参数】对象
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteFlowParamInfoByIds(String ids) {
		return flowParamInfoMapper.deleteFlowParamInfoByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除【流程参数】信息
	 * 
	 * @param flowId
	 *            【流程参数】ID
	 * @return 结果
	 */
	public int deleteFlowParamInfoById(String flowId) {
		return flowParamInfoMapper.deleteFlowParamInfoById(flowId);
	}
}
