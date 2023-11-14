package com.ruoyi.project.flow.javaTask;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ruoyi.common.utils.JDBCUtil;
import com.ruoyi.project.al.cashFlow.controller.CashFlow;
import com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData;
import com.ruoyi.project.al.cashFlow.domain.CashFlowResData;
import com.ruoyi.project.flow.exeFlowTask.mapper.ExeBean;

@Component
@SuppressWarnings("unchecked")
public class TestJavaBean extends JavaTaskBean{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@192.168.43.14/orcl";
	String username = "al";
	String password = "al";
	Integer PageSize = 3;

	@Override
	public String executeSubTask(ExeBean exeBean ,Object exeStr, Object params) throws Exception {
		JDBCUtil jc = new JDBCUtil(driver , url, username, password);
		String getReadyDataCountSql = "select count(1) con from AL_READY_CF partition(P1) t "; 
		String getReadyDataSql = "select t.data_id,t.data_dt,t.data_typ,t.org_cd,t.corp_cd,t.subject_cd,t.acct_num,t.crncy_cd,t.cust_nm,t.cust_num,t.ending_bal,t.bas_rt,t.actl_rt,t.rt_mk,t.orig_dt,t.mtrty_dt,t.rprc_freq,t.accrue_basis,t.accrue_freq,t.pmt_typ,t.pmt_freq,t.ovrdue_flg,t.load_dt from AL_READY_CF partition(P1) t "; 
		Double readyDataCount = Double.valueOf(String.valueOf(((HashMap<String,Object>) jc.excuteQuery(getReadyDataCountSql, null).get(0)).get("CON")));
		Integer pageNum = (int) Math.ceil(readyDataCount/PageSize);
		System.out.println(pageNum);
		List<Object> getReadyData = jc.excuteQuery(getReadyDataSql, null);
		long startTimeAll = System.currentTimeMillis();
		for (Object o : getReadyData) {
			CashFlowBaseData rcf = new CashFlowBaseData().setReadyCashFlowBean(o);
			System.out.println(rcf);
			/**
			 * 根据还本方式、还本频率、起息日、到期日计算出未来的还本日期
			 */
			
			long startTime = System.currentTimeMillis();
			List<CashFlowResData> evaluateOcf = CashFlow.evaluateOcf(rcf);
			long endTime = System.currentTimeMillis();	
			System.out.println("单笔拆分时间:"+(endTime-startTime));
		}
		long endTimeAll = System.currentTimeMillis();
		System.out.println("总计拆分时间:"+(endTimeAll-startTimeAll));
		
	
		
		
		return "1234";
	}
	
	public static void main(String[] args) throws Exception {
		TestJavaBean t= new TestJavaBean();
		t.execute(null,null,null);
	}
	
	

}
