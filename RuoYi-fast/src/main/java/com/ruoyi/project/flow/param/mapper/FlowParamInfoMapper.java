package com.ruoyi.project.flow.param.mapper;

import java.util.List;

import com.ruoyi.project.flow.param.domain.FlowParamInfo;

/**
 * 【流程参数】Mapper接口
 * 
 * @author SangYiPing
 * @date 2019-11-05
 */
public interface FlowParamInfoMapper {
	/**
	 * 查询【流程参数】
	 * 
	 * @param flowId
	 *            【流程参数】ID
	 * @return 【流程参数】
	 */
	public FlowParamInfo selectFlowParamInfoById(String flowId);

	/**
	 * 查询【流程参数】列表
	 * 
	 * @param flowParamInfo
	 *            【流程参数】
	 * @return 【流程参数】集合
	 */
	public List<FlowParamInfo> selectFlowParamInfoList(
			FlowParamInfo flowParamInfo);

	/**
	 * 新增【流程参数】
	 * 
	 * @param flowParamInfo
	 *            【流程参数】
	 * @return 结果
	 */
	public int insertFlowParamInfo(FlowParamInfo flowParamInfo);

	/**
	 * 修改【流程参数】
	 * 
	 * @param flowParamInfo
	 *            【流程参数】
	 * @return 结果
	 */
	public int updateFlowParamInfo(FlowParamInfo flowParamInfo);

	/**
	 * 删除【流程参数】
	 * 
	 * @param flowId
	 *            【流程参数】ID
	 * @return 结果
	 */
	public int deleteFlowParamInfoById(String flowId);

	/**
	 * 批量删除【流程参数】
	 * 
	 * @param flowIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int deleteFlowParamInfoByIds(String[] flowIds);
}
