package com.ruoyi.project.al.cashFlow.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.DateUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData;
import com.ruoyi.project.al.cashFlow.domain.CashFlowResData;
import com.ruoyi.project.al.cashFlow.service.EvalCashFlow;
import com.ruoyi.project.al.cashFlow.util.CheckCashFlow;
import com.ruoyi.project.al.cashFlow.util.RateUtil;

public class Floatng_rt extends EvalCashFlow {

	@Override
	public void setBaseData(CashFlowBaseData cb) {
		if(cb.getIntDate()==null || cb.getIntDate().after(cb.getMtrty_dt())){
			cb.setIntDate(cb.getMtrty_dt());
		}
		this.cb = cb;
		System.out.println(cb.toString());
	}

	@Override
	public List<CashFlowResData> evalCf() {
		/**
		 * P=等额本息计算思路
		 * 1.根据还款计划先计算都需要还多少期及每期的日子
		 * 2.计算计结息规则是按实际年多少天/实际月多少天计算日息还是按12月计算月息等
		 * 3.计算下次重定价日
		 */
		//计算还款日(现金流发生日)
		List<Date> bjhkr = getBjDateList();
		Double leftMoney = cb.getEnding_bal();
		
		RateUtil rt = new RateUtil(cb.getAccrue_basis(), cb.getActl_rt());
		for (int i = 1 ; i < bjhkr.size() ; i ++ ) {//遍历所有还款日
			double firstLx = 0;
			//现金流发生日
			Date xjlfsDt = bjhkr.get(i);
			rt.parse(bjhkr.get(i));
			//上次还款日
			Date lastHkDt = null;
			if(i == 1 ){
				lastHkDt = cb.getLastHkDt();
				//如果计算出来的上次还款日小于起息日则把起息日到首次开始计息日期间的利息算进去(按日利率)
				if(lastHkDt.compareTo(cb.getOrig_dt())<0){
					RateUtil firstRt = new RateUtil("30/ACTUAL", cb.getActl_rt());
					firstRt.parse(cb.getOrig_dt());
					firstLx = DateUtil.diffDay(bjhkr.get(0), cb.getOrig_dt())*firstRt.getdRt()*cb.getEnding_bal();
				}
			}else{
				lastHkDt = xjlfsDt;
			}
			//下次还款日
			Date nextHkDt = bjhkr.get(i);
			int diffDay = DateUtil.diffDay(nextHkDt,lastHkDt);
			double nextRtVal = 0; //下次还款利率
			if(rt.isUsemRt()){
				nextRtVal = rt.getmRt();
			}else{
				nextRtVal = rt.getdRt() * diffDay;
			}
			//每月应还利息	贷款本金×月利率×((1+月利率)^还款月数-(1+月利率)^(还款月序号-1))÷((1+月利率)^还款月数-1)					
			double monthRtBal = leftMoney * nextRtVal * (Math.pow((1 + nextRtVal),bjhkr.size()-1)-Math.pow((1+nextRtVal),i))/(Math.pow((1+nextRtVal),bjhkr.size()-1)-1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			System.out.println(sdf.format(xjlfsDt)+" "+sdf.format(lastHkDt)+" "+sdf.format(nextHkDt)+" "+monthRtBal);
			//每月应还本金	贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕					
			double monthBal = leftMoney * nextRtVal * Math.pow((1 + nextRtVal),i)/(Math.pow((1+nextRtVal),bjhkr.size()-1)-1);
			System.out.println(sdf.format(xjlfsDt)+" "+monthBal+" "+monthRtBal+" "+(monthBal+monthRtBal+firstLx));
			
			
			
			/**
				等额本息	
				每月月供额	〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕					
				每月应还利息	贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕					
				每月应还本金	贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕					
			 */
			
		}
		return null;
	}

	/**
	 * 根据还款计划先计算都需要还多少期及每期的日子
	 * @return 本金还款日,本金现金流发生日
	 */
	private List<Date> getBjDateList() {
		List<Date> bjhkr = new ArrayList<Date>();//本金还款日,本金现金流发生日
		String pmt_freq = cb.getPmt_freq();//先判断传入的还款计划是什么样的xMyD/xM/1Q/1QyD
		if(checkPmtFreq(pmt_freq,"xMyD")){
			int month = Integer.parseInt(pmt_freq.substring(0, pmt_freq.indexOf("M")));
			int day = Integer.parseInt(pmt_freq.substring(pmt_freq.indexOf("M") + 1,pmt_freq.indexOf("D")));
			int index = 0;
			Date lastHkTime = DateUtil.addMonth(cb.getData_dt(),month * -1);
			if(DateUtil.getMaxDayOfMonth(lastHkTime) < day){
				lastHkTime = DateUtil.getMaxDateOfMonth(lastHkTime);
			}else {
				lastHkTime = DateUtil.parseDate(DateUtil.format(lastHkTime,"yyyy-MM")+"-"+day,"yyyy-MM-dd");
			}
			cb.setLastHkDt(lastHkTime);
			while (true) {
				
				//处理xM
				Date currWhileTime = DateUtil.addMonth(cb.getData_dt(),month * index);
				//处理yD,如果大于当前月最大日子,给赋值为当前月最大日
				if(DateUtil.getMaxDayOfMonth(currWhileTime) < day){
					currWhileTime = DateUtil.getMaxDateOfMonth(currWhileTime);
				}else {
					currWhileTime = DateUtil.parseDate(DateUtil.format(currWhileTime,"yyyy-MM")+"-"+day,"yyyy-MM-dd");
				}
				if (currWhileTime.compareTo(cb.getMtrty_dt()) < 0) {
					//如果当前循环日期小于数据到期日
    				bjhkr.add(currWhileTime);
    				index++;
					continue;
				} else if (currWhileTime.compareTo(cb.getMtrty_dt()) == 0) {
					//如果当前循环日期等于数据到期日
					bjhkr.add(currWhileTime);
					break;
				} else {
					bjhkr.add(cb.getMtrty_dt());
					break;
				}
			}
		}else if(checkPmtFreq(pmt_freq,"xM")){
			int month = Integer.parseInt(pmt_freq.substring(0, pmt_freq.indexOf("M")));
			int index = 0;
			Date lastHkTime = DateUtil.addMonth(cb.getData_dt(),month * -1);
			cb.setLastHkDt(lastHkTime);
			while (true) {
				//处理xM
				Date currWhileTime = DateUtil.addMonth(cb.getData_dt(),month * index);
				if (currWhileTime.compareTo(cb.getMtrty_dt()) < 0) {
					//如果当前循环日期小于数据到期日
					bjhkr.add(currWhileTime);
    				index++;
					continue;
				} else if (currWhileTime.compareTo(cb.getMtrty_dt()) == 0) {
					//如果当前循环日期等于数据到期日
					bjhkr.add(currWhileTime);
					break;
				} else {
					bjhkr.add(cb.getMtrty_dt());
					break;
				}
			}
		}else if(checkPmtFreq(pmt_freq,"1Q")){
			//处理每季度对日
			//先取到当前数据起息日是哪月,用来计算下次起息日是哪月
			SimpleDateFormat sdf = new SimpleDateFormat("M");
			int month_orig = Integer.parseInt(sdf.format(cb.getOrig_dt()));
			int month = 3-month_orig%3;//每季度是3个月
			int index = 0;
			Date lastHkTime = DateUtil.addMonth(cb.getData_dt(),month * -1);
			cb.setLastHkDt(lastHkTime);
			while (true) {
				//处理xM
				Date currWhileTime = DateUtil.addMonth(cb.getData_dt(),3 * (index-1)+month);
				if (currWhileTime.compareTo(cb.getMtrty_dt()) < 0) {
					//如果当前循环日期小于数据到期日
					bjhkr.add(currWhileTime);
    				index++;
					continue;
				} else if (currWhileTime.compareTo(cb.getMtrty_dt()) == 0) {
					//如果当前循环日期等于数据到期日
					bjhkr.add(currWhileTime);
					break;
				} else {
					bjhkr.add(cb.getMtrty_dt());
					break;
				}
			}
		}else if(checkPmtFreq(pmt_freq,"1QyD")){
			
			int day = Integer.parseInt(pmt_freq.substring(pmt_freq.indexOf("Q") + 1,pmt_freq.indexOf("D")));
			
			SimpleDateFormat sdf = new SimpleDateFormat("M");
			int month_orig = Integer.parseInt(sdf.format(cb.getOrig_dt()));
			int month = 3-month_orig%3;//每季度是3个月
			int index = 0;
			Date lastHkTime = DateUtil.addMonth(cb.getData_dt(),month * -1);
			cb.setLastHkDt(lastHkTime);
			while (true) {
				//处理xM
				Date currWhileTime = DateUtil.addMonth(cb.getData_dt(),3 * (index-1)+month);
				//处理yD,如果大于当前月最大日子,给赋值为当前月最大日
				if(DateUtil.getMaxDayOfMonth(currWhileTime) < day){
					currWhileTime = DateUtil.getMaxDateOfMonth(currWhileTime);
				}else {
					currWhileTime = DateUtil.parseDate(DateUtil.format(currWhileTime,"yyyy-MM")+"-"+day,"yyyy-MM-dd");
				}
				if (currWhileTime.compareTo(cb.getMtrty_dt()) < 0) {
					//如果当前循环日期小于数据到期日
					bjhkr.add(currWhileTime);
    				index++;
					continue;
				} else if (currWhileTime.compareTo(cb.getMtrty_dt()) == 0) {
					//如果当前循环日期等于数据到期日
					bjhkr.add(currWhileTime);
					break;
				} else {
					bjhkr.add(cb.getMtrty_dt());
					break;
				}
			}
		} else {
			bjhkr.add(cb.getMtrty_dt());
			return bjhkr;
		}
		return bjhkr;
	}

	/**
	 * 判断还款计划字段规则,plan取值为 xM,xMyD,1Q,1QyD 
	 * @param pmt_freq 还款计划字段
	 * @param plan 还款计划字段规则
	 * @return
	 */
	private boolean checkPmtFreq(String pmt_freq, String plan) {
		try {
			//为空直接return false;
			if(StringUtils.isBlank(pmt_freq)){
				return false;
			}
			switch (plan) {
			case "xMyD":
				if(pmt_freq.lastIndexOf("D") == pmt_freq.length()-1){
					//M的位置
					int m_index = pmt_freq.indexOf("M");
					Integer month = Integer.valueOf(pmt_freq.substring(0,m_index));
					Integer day = Integer.valueOf(pmt_freq.substring(m_index+1,pmt_freq.length()-1));
					if(month > 0 && month <= 12 && day > 0 && day <= 31){
						return true;
					}
				}
				return false;
			case "xM":
				if(pmt_freq.lastIndexOf("M") == pmt_freq.length()-1){
					Integer day = Integer.valueOf(pmt_freq.substring(2,pmt_freq.length()));
					if(day>0 && day<31){
						return true;
					}
				}
				return false;
			case "1Q":
				return true;
			case "1QyD":
				if(pmt_freq.indexOf("1Q") == 0 && pmt_freq.lastIndexOf("D") == pmt_freq.length()-1){
					Integer day = Integer.valueOf(pmt_freq.substring(2,pmt_freq.length()));
					if(day > 0 && day <= 31){
						return true;
					}
				}
				return false;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 根据计结息频率、起息日、到期日计算出全部的计息日
	 * 
	 * @param bo
	 * @return
	 * @throws ParseException
	 */
	private List<Date> evaluateIntDates(String frequency) throws ParseException {
		List<Date> ret = new ArrayList<Date>();
		SimpleDateFormat sdfM = new SimpleDateFormat("M");
		if (cb.getOrig_dt() != null) {
			// T-到期
			if ("T".equals(frequency)) {
				ret.add(cb.getOrig_dt());
				ret.add(cb.getIntDate());
				return ret;
			} else if ("M".equals(frequency)) {
				// M 每月对日
				ret.add(cb.getOrig_dt());
				Calendar ca = null;
				for (int i = 1;; i++) {
					ca = Calendar.getInstance();
					ca.setTime(cb.getOrig_dt());
					ca.add(Calendar.MONTH, i);
					if (ca.getTime().after(cb.getIntDate())) {
						break;
					}
					ret.add(ca.getTime());
				}
				if (ret.get(ret.size() - 1).before(cb.getIntDate())) {
					ret.add(cb.getIntDate());
				}
				return ret;
			} else if ("Q".equals(frequency)) {
				// Q 每季对日
				ret.add(cb.getOrig_dt());
				int month = Integer.parseInt(sdfM.format(cb.getOrig_dt()));
				Calendar ca = null;
				int dx = 3 - month % 3;
				for (int i = 0;; i++) {
					ca = Calendar.getInstance();
					ca.setTime(cb.getOrig_dt());
					ca.add(Calendar.MONTH, i * 3 + dx);
					if (ca.getTime().after(cb.getIntDate())) {
						break;
					}
					ret.add(ca.getTime());
				}
				if (ret.get(ret.size() - 1).before(cb.getIntDate())) {
					ret.add(cb.getIntDate());
				}
				return ret;
			} else if ("Y".equals(frequency)) {
				// Y 每年对日
				ret.add(cb.getOrig_dt());
				Calendar ca = null;
				int i = 0;
				for (;; i++) {
					ca = Calendar.getInstance();
					ca.setTime(cb.getOrig_dt());
					ca.add(Calendar.YEAR, i);
					if (ca.getTime().after(cb.getIntDate())) {
						break;
					}
					ret.add(ca.getTime());
				}
				if (ret.get(ret.size() - 1).before(cb.getIntDate())) {
					ret.add(cb.getIntDate());
				}
				return ret;
			} else if ("S".equals(frequency)) {
				// S 每半年对日
				ret.add(cb.getOrig_dt());
				Calendar ca = null;
				int i = 0;
				for (;; i++) {
					ca = Calendar.getInstance();
					ca.setTime(cb.getOrig_dt());
					ca.add(Calendar.MONTH, i * 6);
					if (ca.getTime().after(cb.getIntDate())) {
						break;
					}
					ret.add(ca.getTime());
				}
				if (ret.get(ret.size() - 1).before(cb.getIntDate())) {
					ret.add(cb.getIntDate());
				}
				return ret;
			} else if (CheckCashFlow.checkFrequency(frequency, "D")) {// 20D规则 每隔20天
				int date = Integer.parseInt(frequency.substring(0,frequency.indexOf("D")));
				ret.add(cb.getOrig_dt());
				Calendar ca = Calendar.getInstance();
				ca.setTime(cb.getOrig_dt());
				
				for(;;){
					ca.add(Calendar.DATE, date);
					if (ca.getTime().compareTo(cb.getIntDate()) >= 0) {
						ret.add(cb.getIntDate());
						break;
					} else {
						ret.add(ca.getTime());
					}
				}
			}
		}
		// 每季末
		if ("QE".equals(frequency)) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(cb.getData_dt());
			int month = ((ca.get(Calendar.MONTH) + 1) / 3) * 3 - 1;
			ca.set(Calendar.MONTH, month);
			ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
			Date temp = cb.getOrig_dt();
			if (temp == null) {
				temp = cb.getData_dt();
			}

			if (ca.getTime().before(temp)) {
				ret.add(temp);
			} else {
				ret.add(ca.getTime());
			}

			for (;;) {
				ca.add(Calendar.MONTH, 3);
				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
				if (ca.getTime().after(temp)
						&& ca.getTime().before(cb.getIntDate())) {
					ret.add(ca.getTime());
				}
				if (ca.getTime().compareTo(cb.getIntDate()) == 0) {
					ret.add(ca.getTime());
					break;
				}
				if (ca.getTime().after(cb.getIntDate())) {
					ret.add(cb.getIntDate());
					break;
				}
			}
		} else if ("ME".equals(frequency)) {
			// 每月末
			Calendar ca = Calendar.getInstance();
			ca.setTime(cb.getData_dt());
			ca.add(Calendar.MONTH, -1);
			Date temp = cb.getOrig_dt();
			if (temp == null) {
				temp = cb.getData_dt();
			}
			ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
			if (ca.getTime().before(temp)) {
				ret.add(temp);
			} else {
				ret.add(ca.getTime());
			}

			for (;;) {
				ca.add(Calendar.MONTH, 1);
				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
				if (ca.getTime().after(temp)
						&& ca.getTime().before(cb.getIntDate())) {
					ret.add(ca.getTime());
				}
				if (ca.getTime().compareTo(cb.getIntDate()) == 0) {
					ret.add(ca.getTime());
					break;
				}
				if (ca.getTime().after(cb.getIntDate())) {
					ret.add(cb.getIntDate());
					break;
				}
			}
		} else if (CheckCashFlow.checkFrequency(frequency, "Q")) {// Q20规则，每季20日
			int date = Integer.parseInt(frequency.substring(1));
			Calendar ca = Calendar.getInstance();
			ca.setTime(cb.getData_dt());
			int month = ((ca.get(Calendar.MONTH) + 1) / 3) * 3 - 1;
			ca.set(Calendar.MONTH, month);
			if (date > ca.getActualMaximum(Calendar.DATE)) {
				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
			} else {
				ca.set(Calendar.DATE, date);
			}
			if (ca.getTime().after(cb.getData_dt())) {
				ca.add(Calendar.MONTH, -3);
			}
			Date temp = cb.getOrig_dt();
			if (temp == null) {
				temp = cb.getData_dt();
			}

			if (ca.getTime().before(temp)) {
				ret.add(temp);
			} else {
				ret.add(ca.getTime());
			}

			for (;;) {
				ca.add(Calendar.MONTH, 3);
				if (date > ca.getActualMaximum(Calendar.DATE)) {
					ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
				} else {
					ca.set(Calendar.DATE, date);
				}
				if (ca.getTime().after(temp)
						&& ca.getTime().before(cb.getIntDate())) {
					ret.add(ca.getTime());
				}
				if (ca.getTime().compareTo(cb.getIntDate()) == 0) {
					ret.add(ca.getTime());
					break;
				}
				if (ca.getTime().after(cb.getIntDate())) {
					ret.add(cb.getIntDate());
					break;
				}
			}
		} else if (CheckCashFlow.checkFrequency(frequency, "M")) {// M20规则 每月20日
			int date = Integer.parseInt(frequency.substring(1));
			Calendar ca = Calendar.getInstance();
			ca.setTime(cb.getData_dt());
			ca.add(Calendar.MONTH, -1);
			if (date > ca.getActualMaximum(Calendar.DATE)) {
				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
			} else {
				ca.set(Calendar.DATE, date);
			}
			Date temp = cb.getOrig_dt();
			if (temp == null) {
				temp = cb.getData_dt();
			}

			if (ca.getTime().before(temp)) {
				ret.add(temp);
			} else {
				ret.add(ca.getTime());
			}

			for (;;) {
				ca.add(Calendar.MONTH, 1);
				if (date > ca.getActualMaximum(Calendar.DATE)) {
					ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
				} else {
					ca.set(Calendar.DATE, date);
				}
				if (ca.getTime().after(temp)
						&& ca.getTime().before(cb.getIntDate())) {
					ret.add(ca.getTime());
				}
				if (ca.getTime().compareTo(cb.getIntDate()) == 0) {
					ret.add(ca.getTime());
					break;
				}
				if (ca.getTime().after(cb.getIntDate())) {
					ret.add(cb.getIntDate());
					break;
				}
			}
		} else if (CheckCashFlow.checkFrequency(frequency, "MD")) {// M20规则 每月20日
			int Mnum = frequency.indexOf("M");
			int Dnum = frequency.indexOf("D");
			int month = Integer.parseInt(frequency.substring(0,Mnum));
			int date = Integer.parseInt(frequency.substring(Mnum+1,Dnum));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
			SimpleDateFormat sdfYYYY = new SimpleDateFormat("yyyy");
			Date temp = cb.getOrig_dt();
			if (temp == null) {
				temp = cb.getData_dt();
			}
			
			Date date1 = sdf.parse(sdfYYYY.format(temp)+"-"+month+"-"+date);
			if (date1.before(temp)) {
				ret.add(temp);
			} else {
				ret.add(date1);
			}
			
			
			for(int i=1;;i++){
				Calendar ca = Calendar.getInstance();
				ca.setTime(date1);
				ca.add(Calendar.YEAR, i);
				if (ca.getTime().compareTo(cb.getIntDate()) >= 0) {
					ret.add(cb.getIntDate());
					break;
				} else {
					ret.add(ca.getTime());
				}
			}
		}
		return ret;
	}
	
	/**
	 * 根据计结息频率、起息日、到期日计算出未来的计息日列表，其中i的上一起息日是i-1，即 0<=发生日期 <1
	 * 
	 * @param intdates
	 * @return
	 * @throws ParseException
	 */
	public List<Date> evaluateIntDatesFu(String frequency) {
		List<Date> intdates = null;
		try {
			intdates = this.evaluateIntDates(frequency);
		} catch (ParseException e) {
			e.printStackTrace();
		}// 计算出全部的计息日
		List<Date> temp = new ArrayList<Date>();// 然后根据业务日期截出未来有效的计息日
		for (int i = 0; i < intdates.size(); i++) {
			Date dt = intdates.get(intdates.size() - 1 - i);
			if (dt.compareTo(cb.getData_dt()) < 0) {
				temp.add(dt);
				break;
			} else {
				temp.add(dt);
			}
		}
		List<Date> ret = new ArrayList<Date>();
		for (int i = 0; i < temp.size(); i++) {
			ret.add(temp.get(temp.size() - 1 - i));
		}
		return ret;
	}
}
