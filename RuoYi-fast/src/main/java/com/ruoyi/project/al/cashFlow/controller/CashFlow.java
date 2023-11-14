package com.ruoyi.project.al.cashFlow.controller;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData;
import com.ruoyi.project.al.cashFlow.domain.CashFlowResData;
import com.ruoyi.project.al.cashFlow.service.EvalCashFlow;

public class CashFlow {
	
	public static List<CashFlowResData> evaluateOcf(CashFlowBaseData cb) throws Exception{
		if(cb==null){
			System.out.println("传入数据为空");
			return new ArrayList<CashFlowResData>();
		}
		String checkDataInfo = cb.checkData();
		if(StringUtils.isNotEmpty(checkDataInfo)){
			System.out.println(cb.getData_typ()+cb.getData_id()+":"+checkDataInfo);
			return new ArrayList<CashFlowResData>();
		}
		try {
			Class<?> task = Class.forName("com.ruoyi.project.al.cashFlow.service.impl.Floatng_rt");//+cb.getData_typ()
			EvalCashFlow oc = (EvalCashFlow) task.newInstance();
			oc.setBaseData(cb);
			return oc.eval();
		} catch (Exception e) {
			System.out.println("现金流引擎报错，数据"+cb.getData_id()+"拆分错误！");
			e.printStackTrace();
			throw e;
		}
	}
	
}
