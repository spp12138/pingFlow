package com.ruoyi.project.al.cashFlow.service;

import java.util.List;

import com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData;
import com.ruoyi.project.al.cashFlow.domain.CashFlowResData;

public abstract class EvalCashFlow {

	public CashFlowBaseData cb;

	/**
	 * 现金流基础数据 ，对数据进行部分默认值设置等
	 * 
	 * @param 拆分现金流基础数据  com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData 
	 */
	public abstract void setBaseData(CashFlowBaseData cb);

	/**
	 * 计算现金流的入口
	 * 各子类都需要实现该接口
	 * @return  List<CashFlowResData> 现金流集合
	 */
	public abstract List<CashFlowResData> evalCf();
	
	/**
	 * 计算现金流对外入口,外部执行调用该接口
	 * @return  List<CashFlowResData> 现金流集合
	 */
	public List<CashFlowResData> eval(){
		return evalCf();
	}
	
	
	
}
