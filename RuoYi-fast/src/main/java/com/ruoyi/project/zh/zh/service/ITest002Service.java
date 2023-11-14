package com.ruoyi.project.zh.zh.service;

import java.util.List;

import com.ruoyi.project.zh.zh.domain.Test001;

/**
 * zhService接口
 * 
 * @author SangYiPing
 * @date 2022-07-01
 */
public interface ITest002Service 
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
     * @param Test002 zh
     * @return zh集合
     */
    public List<Test001> selectTest002List(Test001 Test002);

}
