package com.ruoyi.project.flow.log.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.log.domain.FlowLogDetail;
import com.ruoyi.project.flow.log.mapper.FlowLogDetailMapper;
import com.ruoyi.project.flow.log.service.IFlowLogDetailService;

/**
 * 调度日志详情Service业务层处理
 * 
 * @author SangYiPing
 * @date 2020-01-16
 */
@Service
public class FlowLogDetailServiceImpl implements IFlowLogDetailService 
{
    @Autowired
    private FlowLogDetailMapper flowLogDetailMapper;

    /**
     * 查询调度日志详情
     * 
     * @param id 调度日志详情ID
     * @return 调度日志详情
     */
    @Override
    public FlowLogDetail selectFlowLogDetailById(String id)
    {
        return flowLogDetailMapper.selectFlowLogDetailById(id);
    }

    /**
     * 查询调度日志详情列表
     * 
     * @param flowLogDetail 调度日志详情
     * @return 调度日志详情
     */
    @Override
    public List<FlowLogDetail> selectFlowLogDetailList(FlowLogDetail flowLogDetail)
    {
        return flowLogDetailMapper.selectFlowLogDetailList(flowLogDetail);
    }

    /**
     * 新增调度日志详情
     * 
     * @param flowLogDetail 调度日志详情
     * @return 结果
     */
    @Override
    public int insertFlowLogDetail(FlowLogDetail flowLogDetail)
    {
        flowLogDetail.setCreateTime(DateUtils.getNowDate());
        return flowLogDetailMapper.insertFlowLogDetail(flowLogDetail);
    }

    /**
     * 修改调度日志详情
     * 
     * @param flowLogDetail 调度日志详情
     * @return 结果
     */
    @Override
    public int updateFlowLogDetail(FlowLogDetail flowLogDetail)
    {
        flowLogDetail.setUpdateTime(DateUtils.getNowDate());
        return flowLogDetailMapper.updateFlowLogDetail(flowLogDetail);
    }

    /**
     * 删除调度日志详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowLogDetailByIds(String ids)
    {
        return flowLogDetailMapper.deleteFlowLogDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除调度日志详情信息
     * 
     * @param id 调度日志详情ID
     * @return 结果
     */
    public int deleteFlowLogDetailById(String id)
    {
        return flowLogDetailMapper.deleteFlowLogDetailById(id);
    }
    
	/**
	 * 更新日志详情
	 * @param logDetailById
	 * @param stauts  状态 0 新建 , 1 开始, 2 完成, 3 暂停  , 4 异常
	 * @param remark
	 */
	public void updateDetailStauts(FlowLogDetail logDetailById,String stauts,String ... remark) {
    	switch (stauts) {
		case "0":
			break;
		case "1":
			logDetailById.setStartTime(new Date());
			break;
		case "2":
			logDetailById.setEndTime(new Date());
			break;
		case "3":
			break;
		case "4":
			logDetailById.setEndTime(new Date());
			break;
		}
		logDetailById.setStauts(stauts);
		logDetailById.setRemark(remark != null && remark.length > 0 ? remark[0] : null );
		updateFlowLogDetail(logDetailById);
	}
    
}
