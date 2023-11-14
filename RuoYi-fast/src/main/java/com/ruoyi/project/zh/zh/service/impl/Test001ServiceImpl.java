package com.ruoyi.project.zh.zh.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.project.zh.zh.mapper.Test001Mapper;
import com.ruoyi.project.zh.zh.domain.Test001;
import com.ruoyi.project.zh.zh.service.ITest001Service;
import com.ruoyi.common.utils.text.Convert;

/**
 * zhService业务层处理
 * 
 * @author SangYiPing
 * @date 2022-07-01
 */
@Service
public class Test001ServiceImpl implements ITest001Service 
{
    @Autowired
    private Test001Mapper test001Mapper;

    /**
     * 查询zh
     * 
     * @param id zhID
     * @return zh
     */
    @Override
    public Test001 selectTest001ById(String id)
    {
        return test001Mapper.selectTest001ById(id);
    }

    /**
     * 查询zh列表
     * 
     * @param test001 zh
     * @return zh
     */
    @Override
    public List<Test001> selectTest001List(Test001 test001)
    {
        return test001Mapper.selectTest001List(test001);
    }

    /**
     * 新增zh
     * 
     * @param test001 zh
     * @return 结果
     */
    @Override
    public int insertTest001(Test001 test001)
    {
    	test001.setId(UUID.randomUUID().toString().replace("-", ""));
        return test001Mapper.insertTest001(test001);
    }

    /**
     * 修改zh
     * 
     * @param test001 zh
     * @return 结果
     */
    @Override
    public int updateTest001(Test001 test001)
    {
        return test001Mapper.updateTest001(test001);
    }

    /**
     * 删除zh对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTest001ByIds(String ids)
    {
        return test001Mapper.deleteTest001ByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除zh信息
     * 
     * @param id zhID
     * @return 结果
     */
    public int deleteTest001ById(String id)
    {
        return test001Mapper.deleteTest001ById(id);
    }
}
