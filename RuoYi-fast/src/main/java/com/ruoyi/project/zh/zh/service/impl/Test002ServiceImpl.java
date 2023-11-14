package com.ruoyi.project.zh.zh.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.project.zh.zh.domain.Test001;
import com.ruoyi.project.zh.zh.mapper.Test002Mapper;
import com.ruoyi.project.zh.zh.service.ITest002Service;

/**
 * zhService业务层处理
 * 
 * @author SangYiPing
 * @date 2022-07-01
 */
@Service
public class Test002ServiceImpl implements ITest002Service 
{
    @Autowired
    private Test002Mapper test002Mapper;

    /**
     * 查询zh
     * 
     * @param id zhID
     * @return zh
     */
    @Override
    public Test001 selectTest002ById(String id)
    {
        return test002Mapper.selectTest002ById(id);
    }

    /**
     * 查询zh列表
     * 
     * @param test001 zh
     * @return zh
     */
    @Override
    public List<Test001> selectTest002List(Test001 test001)
    {
    	 List<Test001> selectTest002List = test002Mapper.selectTest002List(test001);
    	 for (Test001 t : selectTest002List) {
			t.setZhl(new BigDecimal(t.getSfzh()).divide(new BigDecimal(t.getXy()),4,RoundingMode.HALF_UP).doubleValue()*100+"%");
		}
        return selectTest002List;
    }
}
