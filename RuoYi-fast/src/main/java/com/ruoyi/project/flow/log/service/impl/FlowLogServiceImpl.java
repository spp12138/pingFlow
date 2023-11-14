package com.ruoyi.project.flow.log.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.flow.log.domain.FlowLog;
import com.ruoyi.project.flow.log.mapper.FlowLogMapper;
import com.ruoyi.project.flow.log.service.IFlowLogService;

/**
 * 调度日志Service业务层处理
 * 
 * @author SangYiPing
 * @date 2020-01-16
 */
@Service
public class FlowLogServiceImpl implements IFlowLogService {
	@Autowired
	private FlowLogMapper flowLogMapper;

	/**
	 * 查询调度日志
	 * 
	 * @param id
	 *            调度日志ID
	 * @return 调度日志
	 */
	@Override
	public FlowLog selectFlowLogById(String id) {
		return flowLogMapper.selectFlowLogById(id);
	}

	/**
	 * 查询调度日志列表
	 * 
	 * @param flowLog
	 *            调度日志
	 * @return 调度日志
	 */
	@Override
	public List<FlowLog> selectFlowLogList(FlowLog flowLog) {
		return flowLogMapper.selectFlowLogList(flowLog);
	}

	/**
	 * 新增调度日志
	 * 
	 * @param flowLog
	 *            调度日志
	 * @return 结果
	 */
	@Override
	public int insertFlowLog(FlowLog flowLog) {
		flowLog.setCreateTime(DateUtils.getNowDate());
		return flowLogMapper.insertFlowLog(flowLog);
	}

	/**
	 * 修改调度日志
	 * 
	 * @param flowLog
	 *            调度日志
	 * @return 结果
	 */
	@Override
	public int updateFlowLog(FlowLog flowLog) {
		flowLog.setUpdateTime(DateUtils.getNowDate());
		return flowLogMapper.updateFlowLog(flowLog);
	}

	/**
	 * 删除调度日志对象
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteFlowLogByIds(String ids) {
		return flowLogMapper.deleteFlowLogByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除调度日志信息
	 * 
	 * @param id
	 *            调度日志ID
	 * @return 结果
	 */
	public int deleteFlowLogById(String id) {
		return flowLogMapper.deleteFlowLogById(id);
	}
	
	/**
	 * 更新日志详情
	 * @param logDetailById
	 * @param stauts  状态 0 新建 , 1 开始, 2 完成, 3 暂停  , 4 异常
	 * @param remark
	 */
	public void updateStauts(String sl_id,String stauts,String ... remark) {
		FlowLog log = new FlowLog();
		log.setFlowSlId(sl_id);
		List<FlowLog> selectFlowLogList = selectFlowLogList(log);
		if(selectFlowLogList == null || selectFlowLogList.isEmpty()) return ;
		FlowLog logById = selectFlowLogList.get(0);
		switch (stauts) {
		case "0":
			break;
		case "1":
			logById.setStartTime(new Date());
			break;
		case "2":
			logById.setEndTime(new Date());
			break;
		case "3":
			break;
		case "4":
			logById.setEndTime(new Date());
			break;
		}
		logById.setStauts(stauts);
		logById.setRemark(remark != null && remark.length > 0 ? remark[0] : null );
		updateFlowLog(logById);
	}
	
	
}
