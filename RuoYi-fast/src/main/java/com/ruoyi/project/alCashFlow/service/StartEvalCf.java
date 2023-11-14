package com.ruoyi.project.alCashFlow.service;

import java.util.List;

import com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData;
import com.ruoyi.project.al.cashFlow.domain.CashFlowResData;

public class StartEvalCf extends CashFlowResData{
	
	public static List<CashFlowResData> evaluateOcf(CashFlowBaseData rcf) throws Exception{
		try {
			Class<?> task = Class.forName("com.ruoyi.project.alCashFlow.service.impl.Floatng_rt");
			AlEvalCf oc = (AlEvalCf)  task.newInstance();
//			AlEvalCf oc = (AlEvalCf) SpringUtils.getBean(rcf.getData_typ().toLowerCase());
			return oc.evaluateOcf(rcf);
		} catch (Exception e) {
			System.out.println("计算引擎报错，数据"+rcf.getData_id()+"拆分错误！");
			e.printStackTrace();
			throw e;
		}
	}
	
	
}
