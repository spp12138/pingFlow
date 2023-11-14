package com.ruoyi.project.zh.zh.mapper;

import com.ruoyi.project.zh.zh.domain.Test001;
import java.util.List;

/**
 * zhMapper接口
 * 
 * @author SangYiPing
 * @date 2022-07-01
 */
public interface Test001Mapper 
{
    /**
     * 查询zh
     * 
     * @param id zhID
     * @return zh
     */
    public Test001 selectTest001ById(String id);

    /**
     * 查询zh列表
     * 
     * @param test001 zh
     * @return zh集合
     */
    public List<Test001> selectTest001List(Test001 test001);

    /**
     * 新增zh
     * 
     * @param test001 zh
     * @return 结果
     */
    public int insertTest001(Test001 test001);

    /**
     * 修改zh
     * 
     * @param test001 zh
     * @return 结果
     */
    public int updateTest001(Test001 test001);

    /**
     * 删除zh
     * 
     * @param id zhID
     * @return 结果
     */
    public int deleteTest001ById(String id);

    /**
     * 批量删除zh
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTest001ByIds(String[] ids);
}
