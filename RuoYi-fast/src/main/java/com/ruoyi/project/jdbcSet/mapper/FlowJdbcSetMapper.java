package com.ruoyi.project.jdbcSet.mapper;

import java.util.List;

import com.ruoyi.project.jdbcSet.domain.FlowJdbcSet;

/**
 * 数据库连接管理Mapper接口
 * 
 * @author SangYiPing
 * @date 2019-11-04
 */
public interface FlowJdbcSetMapper 
{
    /**
     * 查询数据库连接管理
     * 
     * @param id 数据库连接管理ID
     * @return 数据库连接管理
     */
    public FlowJdbcSet selectFlowJdbcSetById(String id);

    /**
     * 查询数据库连接管理列表
     * 
     * @param flowJdbcSet 数据库连接管理
     * @return 数据库连接管理集合
     */
    public List<FlowJdbcSet> selectFlowJdbcSetList(FlowJdbcSet flowJdbcSet);

    /**
     * 新增数据库连接管理
     * 
     * @param flowJdbcSet 数据库连接管理
     * @return 结果
     */
    public int insertFlowJdbcSet(FlowJdbcSet flowJdbcSet);

    /**
     * 修改数据库连接管理
     * 
     * @param flowJdbcSet 数据库连接管理
     * @return 结果
     */
    public int updateFlowJdbcSet(FlowJdbcSet flowJdbcSet);

    /**
     * 删除数据库连接管理
     * 
     * @param id 数据库连接管理ID
     * @return 结果
     */
    public int deleteFlowJdbcSetById(String id);

    /**
     * 批量删除数据库连接管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFlowJdbcSetByIds(String[] ids);
}
