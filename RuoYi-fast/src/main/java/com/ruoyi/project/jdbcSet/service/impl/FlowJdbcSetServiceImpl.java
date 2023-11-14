package com.ruoyi.project.jdbcSet.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.jdbcSet.domain.FlowJdbcSet;
import com.ruoyi.project.jdbcSet.mapper.FlowJdbcSetMapper;
import com.ruoyi.project.jdbcSet.service.IFlowJdbcSetService;

/**
 * 数据库连接管理Service业务层处理
 * 
 * @author SangYiPing
 * @date 2019-11-04
 */
@Service
public class FlowJdbcSetServiceImpl implements IFlowJdbcSetService 
{
    @Autowired
    private FlowJdbcSetMapper flowJdbcSetMapper;

    /**
     * 查询数据库连接管理
     * 
     * @param id 数据库连接管理ID
     * @return 数据库连接管理
     */
    @Override
    public FlowJdbcSet selectFlowJdbcSetById(String id)
    {
        return flowJdbcSetMapper.selectFlowJdbcSetById(id);
    }

    /**
     * 查询数据库连接管理列表
     * 
     * @param flowJdbcSet 数据库连接管理
     * @return 数据库连接管理
     */
    @Override
    public List<FlowJdbcSet> selectFlowJdbcSetList(FlowJdbcSet flowJdbcSet)
    {
        return flowJdbcSetMapper.selectFlowJdbcSetList(flowJdbcSet);
    }

    /**
     * 新增数据库连接管理
     * 
     * @param flowJdbcSet 数据库连接管理
     * @return 结果
     */
    @Override
    public int insertFlowJdbcSet(FlowJdbcSet flowJdbcSet)
    {
        flowJdbcSet.setCreateTime(DateUtils.getNowDate());
        return flowJdbcSetMapper.insertFlowJdbcSet(flowJdbcSet);
    }

    /**
     * 修改数据库连接管理
     * 
     * @param flowJdbcSet 数据库连接管理
     * @return 结果
     */
    @Override
    public int updateFlowJdbcSet(FlowJdbcSet flowJdbcSet)
    {
        flowJdbcSet.setUpdateTime(DateUtils.getNowDate());
        return flowJdbcSetMapper.updateFlowJdbcSet(flowJdbcSet);
    }

    /**
     * 删除数据库连接管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFlowJdbcSetByIds(String ids)
    {
        return flowJdbcSetMapper.deleteFlowJdbcSetByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除数据库连接管理信息
     * 
     * @param id 数据库连接管理ID
     * @return 结果
     */
    public int deleteFlowJdbcSetById(String id)
    {
        return flowJdbcSetMapper.deleteFlowJdbcSetById(id);
    }
}
