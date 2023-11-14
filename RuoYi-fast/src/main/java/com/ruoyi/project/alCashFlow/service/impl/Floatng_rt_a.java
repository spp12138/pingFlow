package com.ruoyi.project.alCashFlow.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData;
import com.ruoyi.project.al.cashFlow.domain.CashFlowResData;
import com.ruoyi.project.alCashFlow.service.AlEvalCf;
import com.ruoyi.project.alCashFlow.util.AlCfUtil;

/**
 * 用于拆分固定利率模型数据（固定利率且未逾期的的贷款、定期、同业定期、债券投资、发行债券，理财，未逾期的信用透支，有到期日的表外数据）
 * 
 * @author Administrator
 * 
 */
@Component
public class Floatng_rt_a extends AlEvalCf {


	
	@Override
	public List<CashFlowResData> evaluateOcf(CashFlowBaseData rcf) {
		if(rcf.getAccrue_basis() == null){
			rcf.setAccrue_basis("ACTUAL/365");
		}
		if(rcf.getAccrue_freq() == null){
			rcf.setAccrue_freq("ME");
		}
		if(rcf.getMtrty_dt().compareTo(rcf.getData_dt())<=0){
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getData_dt());
			ca.add(Calendar.DATE, 1);
			rcf.setMtrty_dt(ca.getTime());
		}
		if(rcf.getIntDate()==null || rcf.getIntDate().after(rcf.getMtrty_dt())){
			rcf.setIntDate(rcf.getMtrty_dt());
		}
		
		// 考虑一般情况数据，常规业务，业务日期<到期日,且利率不為0
		if (rcf.getMtrty_dt().compareTo(rcf.getData_dt()) > 0 && rcf.getActl_rt() != null && rcf.getActl_rt().doubleValue() != 0.00) {
			List<Date> pdates = getPrincDateList(rcf);// 本金流发生日期列表
			List<Date> intAccDates = null;// 利息权责发生制发生日期列表
			List<CashFlowResData> princ = null;// 本金流
			boolean flg = true;
			CashFlowResData boi = null;
			CashFlowResData rprcBo = null;
			intAccDates = evaluateIntDatesFu(rcf);
			if ("P".equals(rcf.getPmt_typ())) {// 等额本金
				princ = getFlowForP(pdates,intAccDates,rcf);
			} else {
				princ = new ArrayList<CashFlowResData>();
				princ.add(getDefaultOcf(rcf));
				//计算本金流
				Date nrd = getNextRepriceDate(rcf);
				Double sumPrice=0.0;
				Double Principal = rcf.getEnding_bal().doubleValue()/pdates.size();
				for(int i=0; i<pdates.size(); i++){
					CashFlowResData bo = new CashFlowResData();
					bo.setData_typ(rcf.getData_typ());
					bo.setOcc_dt(pdates.get(i));
					bo.setActl_rt(rcf.getActl_rt());
					bo.setPrinc(Principal);
					bo.setBeginningBal(rcf.getEnding_bal()-i*Principal);
					
					bo.setEnding_bal(bo.getBeginningBal()-bo.getPrinc());
					if(i==pdates.size()-1){
						bo.setPrinc(bo.getBeginningBal());
						bo.setEnding_bal(0d);
					}
					bo.setLiq_gap_days((int)((bo.getOcc_dt().getTime()-rcf.getData_dt().getTime())/86400000));
					bo.setNxt_rprc_dt(nrd.after(bo.getOcc_dt())?bo.getOcc_dt():nrd);
					bo.setRprc_gap_days((int)((bo.getNxt_rprc_dt().getTime()-rcf.getData_dt().getTime())/86400000));
					princ.add(bo);
				}
			}
		}
		return null;
	}
	 
	/**
	 * 根据计结息频率、起息日、到期日计算出未来的计息日列表，其中i的上一起息日是i-1，即 0<=发生日期 <1
	 * 
	 * @param intdates
	 * @return
	 * @throws ParseException
	 */
	public List<Date> evaluateIntDatesFu(CashFlowBaseData rcf) {
		List<Date> intdates = null;
		try {
			intdates = this.evaluateIntDates(rcf);
		} catch (ParseException e) {
			e.printStackTrace();
		}// 计算出全部的计息日
		List<Date> temp = new ArrayList<Date>();// 然后根据业务日期截出未来有效的计息日
		for (int i = 0; i < intdates.size(); i++) {
			Date dt = intdates.get(intdates.size() - 1 - i);
			if (dt.compareTo(rcf.getData_dt()) < 0) {
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
	
	/**
	 * 根据计结息频率、起息日、到期日计算出全部的计息日
	 * 
	 * @param bo
	 * @return
	 * @throws ParseException
	 */
	private List<Date> evaluateIntDates(CashFlowBaseData rcf) throws ParseException {
		List<Date> ret = new ArrayList<Date>();
		String formatM = "M";
		SimpleDateFormat sdfM = new SimpleDateFormat(formatM);
		if (rcf.getOrig_dt() != null) {
			// T-到期
			if ("T".equals(rcf.getAccrue_freq())) {
				ret.add(rcf.getOrig_dt());
				ret.add(rcf.getIntDate());
				return ret;
			} else if ("M".equals(rcf.getAccrue_freq())) {
				// M 每月对日
				ret.add(rcf.getOrig_dt());
				Calendar ca = null;
				for (int i = 1;; i++) {
					ca = Calendar.getInstance();
					ca.setTime(rcf.getOrig_dt());
					ca.add(Calendar.MONTH, i);
					if (ca.getTime().after(rcf.getIntDate())) {
						break;
					}
					ret.add(ca.getTime());
				}
				if (ret.get(ret.size() - 1).before(rcf.getIntDate())) {
					ret.add(rcf.getIntDate());
				}
				return ret;
			} else if ("Q".equals(rcf.getAccrue_freq())) {
				// Q 每季对日
				ret.add(rcf.getOrig_dt());
				int month = Integer.parseInt(sdfM.format(rcf.getOrig_dt()));
				Calendar ca = null;
				int dx = 3 - month % 3;
				for (int i = 0;; i++) {
					ca = Calendar.getInstance();
					ca.setTime(rcf.getOrig_dt());
					ca.add(Calendar.MONTH, i * 3 + dx);
					if (ca.getTime().after(rcf.getIntDate())) {
						break;
					}
					ret.add(ca.getTime());
				}
				if (ret.get(ret.size() - 1).before(rcf.getIntDate())) {
					ret.add(rcf.getIntDate());
				}
				return ret;
			} else if ("Y".equals(rcf.getAccrue_freq())) {
				// Y 每年对日
				ret.add(rcf.getOrig_dt());
				Calendar ca = null;
				int i = 0;
				for (;; i++) {
					ca = Calendar.getInstance();
					ca.setTime(rcf.getOrig_dt());
					ca.add(Calendar.YEAR, i);
					if (ca.getTime().after(rcf.getIntDate())) {
						break;
					}
					ret.add(ca.getTime());
				}
				if (ret.get(ret.size() - 1).before(rcf.getIntDate())) {
					ret.add(rcf.getIntDate());
				}
				return ret;
			} else if ("S".equals(rcf.getAccrue_freq())) {
				// S 每半年对日
				ret.add(rcf.getOrig_dt());
				Calendar ca = null;
				int i = 0;
				for (;; i++) {
					ca = Calendar.getInstance();
					ca.setTime(rcf.getOrig_dt());
					ca.add(Calendar.MONTH, i * 6);
					if (ca.getTime().after(rcf.getIntDate())) {
						break;
					}
					ret.add(ca.getTime());
				}
				if (ret.get(ret.size() - 1).before(rcf.getIntDate())) {
					ret.add(rcf.getIntDate());
				}
				return ret;
			} else if (AlCfUtil.checkFrequency(rcf.getAccrue_freq(), "D")) {// 20D规则 每隔20天
				int date = Integer.parseInt(rcf.getAccrue_freq().substring(0,rcf.getAccrue_freq().indexOf("D")));
				ret.add(rcf.getOrig_dt());
				Calendar ca = Calendar.getInstance();
				ca.setTime(rcf.getOrig_dt());
				
				for(;;){
					ca.add(Calendar.DATE, date);
					if (ca.getTime().compareTo(rcf.getIntDate()) >= 0) {
						ret.add(rcf.getIntDate());
						break;
					} else {
						ret.add(ca.getTime());
					}
				}
			}
		}
		// 每季末
		if ("QE".equals(rcf.getAccrue_freq())) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getData_dt());
			int month = ((ca.get(Calendar.MONTH) + 1) / 3) * 3 - 1;
			ca.set(Calendar.MONTH, month);
			ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
			Date temp = rcf.getOrig_dt();
			if (temp == null) {
				temp = rcf.getData_dt();
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
						&& ca.getTime().before(rcf.getIntDate())) {
					ret.add(ca.getTime());
				}
				if (ca.getTime().compareTo(rcf.getIntDate()) == 0) {
					ret.add(ca.getTime());
					break;
				}
				if (ca.getTime().after(rcf.getIntDate())) {
					ret.add(rcf.getIntDate());
					break;
				}
			}
		} else if ("ME".equals(rcf.getAccrue_freq())) {
			// 每月末
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getData_dt());
			ca.add(Calendar.MONTH, -1);
			Date temp = rcf.getOrig_dt();
			if (temp == null) {
				temp = rcf.getData_dt();
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
						&& ca.getTime().before(rcf.getIntDate())) {
					ret.add(ca.getTime());
				}
				if (ca.getTime().compareTo(rcf.getIntDate()) == 0) {
					ret.add(ca.getTime());
					break;
				}
				if (ca.getTime().after(rcf.getIntDate())) {
					ret.add(rcf.getIntDate());
					break;
				}
			}
		} else if (AlCfUtil.checkFrequency(rcf.getAccrue_freq(), "Q")) {// Q20规则，每季20日
			int date = Integer.parseInt(rcf.getAccrue_freq().substring(1));
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getData_dt());
			int month = ((ca.get(Calendar.MONTH) + 1) / 3) * 3 - 1;
			ca.set(Calendar.MONTH, month);
			if (date > ca.getActualMaximum(Calendar.DATE)) {
				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
			} else {
				ca.set(Calendar.DATE, date);
			}
			if (ca.getTime().after(rcf.getData_dt())) {
				ca.add(Calendar.MONTH, -3);
			}
			Date temp = rcf.getOrig_dt();
			if (temp == null) {
				temp = rcf.getData_dt();
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
						&& ca.getTime().before(rcf.getIntDate())) {
					ret.add(ca.getTime());
				}
				if (ca.getTime().compareTo(rcf.getIntDate()) == 0) {
					ret.add(ca.getTime());
					break;
				}
				if (ca.getTime().after(rcf.getIntDate())) {
					ret.add(rcf.getIntDate());
					break;
				}
			}
		} else if (AlCfUtil.checkFrequency(rcf.getAccrue_freq(), "M")) {// M20规则 每月20日
			int date = Integer.parseInt(rcf.getAccrue_freq().substring(1));
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getData_dt());
			ca.add(Calendar.MONTH, -1);
			if (date > ca.getActualMaximum(Calendar.DATE)) {
				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
			} else {
				ca.set(Calendar.DATE, date);
			}
			Date temp = rcf.getOrig_dt();
			if (temp == null) {
				temp = rcf.getData_dt();
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
						&& ca.getTime().before(rcf.getIntDate())) {
					ret.add(ca.getTime());
				}
				if (ca.getTime().compareTo(rcf.getIntDate()) == 0) {
					ret.add(ca.getTime());
					break;
				}
				if (ca.getTime().after(rcf.getIntDate())) {
					ret.add(rcf.getIntDate());
					break;
				}
			}
		} else if (AlCfUtil.checkFrequency(rcf.getAccrue_freq(), "MD")) {// M20规则 每月20日
			int Mnum = rcf.getAccrue_freq().indexOf("M");
			int Dnum = rcf.getAccrue_freq().indexOf("D");
			int month = Integer.parseInt(rcf.getAccrue_freq().substring(0,Mnum));
			int date = Integer.parseInt(rcf.getAccrue_freq().substring(Mnum+1,Dnum));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
			SimpleDateFormat sdfYYYY = new SimpleDateFormat("yyyy");
			Date temp = rcf.getOrig_dt();
			if (temp == null) {
				temp = rcf.getData_dt();
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
				if (ca.getTime().compareTo(rcf.getIntDate()) >= 0) {
					ret.add(rcf.getIntDate());
					break;
				} else {
					ret.add(ca.getTime());
				}
			}
		}
		return ret;
	}
	
	
	/**
	 * 计算等额本息情况下本息流
	 * 
	 * 修改 李福保 2019-12-16 ，增加传入参数 initAccDates ,权责发生制利息单独计算
	 * getFlowForP(List<Date> pdates)
	 * @return
	 */
	public List<CashFlowResData>  getFlowForP(List<Date> pdates,List<Date> intAccDates,CashFlowBaseData rcf){
		List<CashFlowResData> bolst = new ArrayList<CashFlowResData>();
		List<CashFlowResData> rtst = new ArrayList<CashFlowResData>();//权责发生制 利息流
		bolst = new ArrayList<CashFlowResData>();
		bolst.add(getDefaultOcf(rcf));
		Double princAndInt = getPrincAndInt(pdates.size(),rcf);
		// 计算本息流
		Date nrd = getNextRepriceDate(rcf);
		Date lastdate = evaluateLastIntDatesForP(rcf);// 上个本金日
		Double beginningBal = rcf.getEnding_bal();
		CashFlowResData bo = null;
		CashFlowResData boi = null;
		CashFlowResData rprcBo = null;
		List<Date> rprcDates = null ;
		boolean flg = true;
		for (int i = 0; i < pdates.size(); i++) {
			bo = new CashFlowResData();
			bo.setData_typ(rcf.getData_typ());
			//等额本息发生时间全部为期末
			bo.setOcc_dt(pdates.get(i));
			bo.setActl_rt(rcf.getActl_rt());
			bo.setLiq_gap_days((int) ((bo.getOcc_dt().getTime() - rcf.getData_dt().getTime()) / 86400000));
			bo.setNxt_rprc_dt(nrd.after(bo.getOcc_dt()) ? bo.getOcc_dt(): nrd);
			bo.setRprc_gap_days((int) ((bo.getNxt_rprc_dt().getTime() - rcf.getData_dt().getTime()) / 86400000));
			
			//若为此后的第一个本金日，则本次本息流开始日期认定为上个本金日后一天，若上个本今日为起息日，则直接认定为起息日
			//其余开始时间认定为第i - 1本金日后一天
			Date startDt = rcf.getOrig_dt();
			if (i == 0 ){
				startDt = lastdate;
				bo.setStart_int_dt(lastdate);
			}else{
				startDt = pdates.get(i - 1);
				bo.setStart_int_dt(pdates.get(i - 1));
			}
			bo.setEnd_int_dt(pdates.get(i));
			bolst.add(bo);
			bo.setInterest(evaluateInt(bo, bolst, i == 0 ? true: false,rcf));
			bo.setPrinc(princAndInt - bo.getInterest());
			if (i == 0 && pdates.size()>1) {
				bo.setBeginningBal(rcf.getEnding_bal());
			} else if (i == pdates.size() - 1) {
				bo.setBeginningBal(beginningBal);
				bo.setPrinc(beginningBal);
			} else if (i > 0) {
				bo.setBeginningBal(beginningBal);
			}
			bo.setEnding_bal(bo.getBeginningBal() - bo.getPrinc());
			Date lstRprcDt = getLastRepriceDate(bo,rcf);
			bo.setLst_rprc_dt(lstRprcDt);
			//若计息周期内有多个重定价周期，则将本息流拆分，利息流拆分成多条
			rprcDates = getRprcDateForDateRange(startDt,pdates.get(i),rcf);
			if(rprcDates != null && !rprcDates.isEmpty()){
				//清除本金流中利息相关数据
				bo.setStart_int_accrue_dt(null);
				bo.setStart_int_dt(null);
				bo.setEnd_int_accrue_dt(null);
				bo.setEnd_int_dt(null);
				bo.setInt_accrue(0d);
				bo.setInterest(0d);
				bo.setLst_rprc_dt(null);
				//添加利息流
				rprcDates.add(pdates.get(i));
				for(int j = 0;j<rprcDates.size();j++){
					Date rprcStart = null;
					Date rprcEnd = rprcDates.get(j);
					if(j==0){
						//若是第一个重定价周期，则周期开始与上个计息日，周期为左开右闭
						rprcStart = startDt;
					}else{
						rprcStart = rprcDates.get(j-1);
						lstRprcDt = rprcDates.get(j-1);
					}
					rprcBo = bo.clone();
					rprcBo.setPrinc(0d);
					rprcBo.setStart_int_dt(rprcStart);
					rprcBo.setEnd_int_dt(rprcEnd);
					rprcBo.setInterest(evaluateInt(rprcBo,rprcStart,rprcEnd,rcf));
					rprcBo.setLst_rprc_dt(lstRprcDt);
					rtst.add(rprcBo);
				}
			}else{
				CashFlowResData rtBo = bo.clone();
				//清除本金流中利息相关数据
				bo.setStart_int_accrue_dt(null);
				bo.setStart_int_dt(null);
				bo.setEnd_int_accrue_dt(null);
				bo.setEnd_int_dt(null);
				bo.setInt_accrue(0d);
				bo.setInterest(0d);
				rtBo.setPrinc(0d);
				rtst.add(rtBo);
			}
			beginningBal = bo.getEnding_bal();
			
		}
		
		// 计算权责发生制利息  add by lifubao  2019-12-16
		Date lstRprcDt = null;
		// 如果起息日 是当前业务日期 ，需特殊处理 ，且是结息当天
		if(intAccDates.size() > 1 && intAccDates.get(0).compareTo(rcf.getOrig_dt())==0 && rcf.getOrig_dt().compareTo(rcf.getData_dt())>=0){
			boi = new CashFlowResData();
			boi.setData_typ(rcf.getData_typ());	 
			//判断计结息方式为期初还是期末
			boi.setOcc_dt(intAccDates.get(0));
			boi.setActl_rt(rcf.getActl_rt());
			boi.setStart_int_accrue_dt(rcf.getOrig_dt());
			boi.setEnd_int_accrue_dt(intAccDates.get(0));
			boi.setLiq_gap_days(0); 
			boi.setInt_accrue(evaluateIntAccrue(boi,bolst,true,rcf));
			rtst.add(boi);
			flg = false;
		}
		for(int i=1; intAccDates.size()>1 && i<intAccDates.size(); i++){
			boi = new CashFlowResData();
			bo.setData_typ(rcf.getData_typ());
			//计算该段利息起息日期，若当前计结息日期为第一次计结息，则直接设定发生时间为第0个计结息日期即起息日，否则设定发生时间为上个计结息日期后一天
			Calendar ca = Calendar.getInstance();
			ca.setTime(intAccDates.get(i-1));
			if(i != 1){
				ca.add(Calendar.DATE, 1);
			}
			boi.setOcc_dt(intAccDates.get(i));
			boi.setActl_rt(rcf.getActl_rt());
			boi.setPrinc(0d);
			if(intAccDates.get(i-1).before(rcf.getData_dt())){
				boi.setStart_int_accrue_dt(rcf.getData_dt());
			}else{
				boi.setStart_int_accrue_dt(intAccDates.get(i-1));
			}
			boi.setEnd_int_accrue_dt(intAccDates.get(i));
			boi.setNxt_rprc_dt(nrd.after(boi.getOcc_dt()) ? boi.getOcc_dt(): nrd);
			boi.setLiq_gap_days((int)((intAccDates.get(i).getTime()-rcf.getData_dt().getTime())/86400000));
			// 给利息数据的开始金额、期末余额字段 赋值
			for(int j=1; j<bolst.size(); j++){ 
				if(j==bolst.size()-1){
					boi.setEnding_bal(bolst.get(j).getBeginningBal());
					boi.setBeginningBal(bolst.get(j).getBeginningBal());
					break;
				}
				if(bolst.get(j).getOcc_dt().compareTo(boi.getOcc_dt())<=0 && bolst.get(j+1).getOcc_dt().compareTo(boi.getOcc_dt())>0){
					boi.setEnding_bal(bolst.get(j).getBeginningBal());
					boi.setBeginningBal(bolst.get(j).getBeginningBal());
					break;
				}
			}
			
			//获取计息周期内的重定价日(由于权责发生制 从 当前业务日期开始算起，所以计算周期内的重定价日时，小于业务日期的，要从业务日期开始算起)
			if(ca.getTime().compareTo(rcf.getData_dt())<0){
				rprcDates = getRprcDateForDateRange(rcf.getData_dt(),intAccDates.get(i),rcf);
			}else{
				rprcDates = getRprcDateForDateRange(ca.getTime(),intAccDates.get(i),rcf);
			}
			lstRprcDt = getLastRepriceDate(boi,rcf);
			//如若计息周期内含有多个重定价周期，则计息周期内有多条利息流 
			if(rprcDates != null && !rprcDates.isEmpty()){
				rprcDates.add(intAccDates.get(i));
				for(int j =0 ;j<rprcDates.size();j++){
					Date rprcStart = null;
					Date rprcEnd = rprcDates.get(j);
					if(j==0){
						//若是第一个重定价周期，则周期开始与上个计息日，周期为左开右闭
						rprcStart = intAccDates.get(i-1);
					}else{
						rprcStart = rprcDates.get(j-1);
						lstRprcDt = rprcDates.get(j-1);
					}
					rprcBo = boi.clone();
					if(rprcStart.before(rcf.getData_dt())){
						rprcStart = rcf.getData_dt();
					}
					rprcBo.setStart_int_accrue_dt(rprcStart);
					rprcBo.setEnd_int_accrue_dt(rprcEnd);
					if(flg){
						rprcBo.setInt_accrue(evaluateIntAccrue(rprcBo,bolst,j==0&&i==1?true:false,rcf));
					}else{
						rprcBo.setInt_accrue(evaluateIntAccrue(rprcBo,bolst,false,rcf));
					}
					rprcBo.setLst_rprc_dt(lstRprcDt);
					rprcBo.setEnding_bal(null);
					rtst.add(rprcBo);
				}
			}else{ 
				if(i==1 && flg){
					boi.setInt_accrue(evaluateIntAccrue(boi,bolst,true,rcf));
				}else{
					boi.setInt_accrue(evaluateIntAccrue(boi,bolst,false,rcf));
				}
				boi.setLst_rprc_dt(lstRprcDt);
				boi.setEnding_bal(null);
				rtst.add(boi);
			}
		} //  权责发生制利息增加结束 
		for (CashFlowResData a : bolst) {
			System.out.println(a);
		}
		for (CashFlowResData a : rtst) {
			System.out.println(a);
		}
		return  mergeAlCf(bolst, rtst);
	}
	
}

