package com.ruoyi.project.zh.zh.mapper;

import java.util.List;

import com.ruoyi.project.zh.zh.domain.Test001;

/**
 * zhMapper接口
 * 
 * @author SangYiPing
 * @date 2022-07-01
 */
public interface Test002Mapper 
{
    /**
     * 查询zh
     * 
     * @param id zhID
     * @return zh
     */
    public Test001 selectTest002ById(String id);

    /**
     * 查询zh列表
     * 
     * @param test001 zh
     * @return zh集合
     */
    public List<Test001> selectTest002List(Test001 test001);
}
