package com.ruoyi.project.alCashFlow.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.DateUtil;
import com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData;
import com.ruoyi.project.al.cashFlow.domain.CashFlowResData;
import com.ruoyi.project.al.cashFlow.domain.RateEval;
import com.ruoyi.project.alCashFlow.util.AlCfUtil;

public class AlEvalCf {

	/**
	 * 算头不算尾，用来判断本金还本日是否需要递减一天
	 */
	protected static final boolean FISRTORLAST = true; 
	
	public List<Date> getPrincDateList(CashFlowBaseData rcf) {
		List<Date> ret = new ArrayList<Date>();
		if (rcf.getPmt_typ() == null || "T".equals(rcf.getPmt_typ())) {
			ret.add(rcf.getMtrty_dt());
		} else if("P".equals(rcf.getPmt_typ()) || "A".equals(rcf.getPmt_typ())){
			if (AlCfUtil.checkPmtFreq(rcf.getPmt_freq(), "xM")) {// 每个x月对日
				int month = Integer.parseInt(rcf.getPmt_freq().substring(0,rcf.getPmt_freq().indexOf("M")));
				int i = 1;
				for (;; i++) {
					Calendar cat = Calendar.getInstance();
					cat.setTime(rcf.getOrig_dt());
					cat.add(Calendar.MONTH, month * i);
					if (cat.getTime().compareTo(rcf.getData_dt()) < 0) {
						continue;
					}
					if (cat.getTime().compareTo(rcf.getMtrty_dt()) < 0) {
						ret.add(cat.getTime());
						continue;
					} else if (cat.getTime().compareTo(rcf.getMtrty_dt()) == 0) {
						ret.add(cat.getTime());
						break;
					} else {
						ret.add(rcf.getMtrty_dt());
						break;
					}
				}
			} else if (AlCfUtil.checkPmtFreq(rcf.getPmt_freq(), "xMyD")) {// 每隔x月的y日
				int month = Integer.parseInt(rcf.getPmt_freq().substring(0, rcf.getPmt_freq().indexOf("M")));
				int dateOfM = Integer.parseInt(rcf.getPmt_freq().substring( rcf.getPmt_freq().indexOf("M") + 1, rcf.getPmt_freq().indexOf("D")));
				int i = 1;
				for (;; i++) {
					Calendar cat = Calendar.getInstance();
					cat.setTime(rcf.getOrig_dt());
					cat.add(Calendar.MONTH, month * i);
					if (cat.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfM) {
						cat.set(Calendar.DAY_OF_MONTH,
								cat.getActualMaximum(Calendar.DAY_OF_MONTH));
					} else {
						cat.set(Calendar.DAY_OF_MONTH, dateOfM);
					}
					if (cat.getTime().compareTo(rcf.getData_dt()) < 0) {
						continue;
					}
					if (cat.getTime().compareTo(rcf.getMtrty_dt()) < 0) {
						ret.add(cat.getTime());
						continue;
					} else if (cat.getTime().compareTo(rcf.getMtrty_dt()) == 0) {
						ret.add(cat.getTime());
						break;
					} else {
						ret.add(rcf.getMtrty_dt());
						break;
					}
				}
				
			} else if (AlCfUtil.checkPmtFreq(rcf.getPmt_freq(), "1Q")) {// 每季度对日
				SimpleDateFormat sdf = new SimpleDateFormat("M");
				int dx = Integer.parseInt(sdf.format(rcf.getOrig_dt()));
				dx = 3-dx%3;
				int i = 1;
				for (;; i++) {
					Calendar cat = Calendar.getInstance();
					cat.setTime(rcf.getOrig_dt());
					if(i>0){
						cat.add(Calendar.MONTH, 3 * (i-1)+dx);
					}
					if (cat.getTime().compareTo(rcf.getData_dt()) < 0) {
						continue;
					}
					

					if (cat.getTime().compareTo(rcf.getMtrty_dt()) < 0) {
						ret.add(cat.getTime());
						continue;
					} else if (cat.getTime().compareTo(rcf.getMtrty_dt()) == 0) {
						ret.add(cat.getTime());
						break;
					} else {
						ret.add(rcf.getMtrty_dt());
						break;
					}
				}
			} else if (AlCfUtil.checkPmtFreq(rcf.getPmt_freq(), "1QyD")) {// 每隔季度的y日
				int dateOfM = Integer.parseInt(rcf.getPmt_freq().substring( rcf.getPmt_freq().indexOf("Q") + 1, rcf.getPmt_freq().indexOf("D")));
				SimpleDateFormat sdf = new SimpleDateFormat("M");
				int dx = Integer.parseInt(sdf.format(rcf.getOrig_dt()));
				dx = 3-dx%3;
				int i = 1;
				for (;; i++) {
					Calendar cat = Calendar.getInstance();
					cat.setTime(rcf.getOrig_dt());
					if(i>0){
						cat.add(Calendar.MONTH, 3 * (i-1)+dx);
					}
					if (cat.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfM) {
						cat.set(Calendar.DAY_OF_MONTH,
								cat.getActualMaximum(Calendar.DAY_OF_MONTH));
					} else {
						cat.set(Calendar.DAY_OF_MONTH, dateOfM);
					}
					if (cat.getTime().compareTo(rcf.getData_dt()) < 0) {
						continue;
					}
					
					if (cat.getTime().compareTo(rcf.getMtrty_dt()) < 0) {
						ret.add(cat.getTime());
						continue;
					} else if (cat.getTime().compareTo(rcf.getMtrty_dt()) == 0) {
						ret.add(cat.getTime());
						break;
					} else {
						ret.add(rcf.getMtrty_dt());
						break;
					}
				}
			} else {
				ret.add(rcf.getMtrty_dt());
			}
		}
		return ret;
	}

	public List<CashFlowResData> evaluateOcf(CashFlowBaseData rcf)  {
		return null;
	}

	/**
	 * 获取参考现金流，即第一笔现金流
	 * 
	 * @return
	 */
	public CashFlowResData getDefaultOcf(CashFlowBaseData rcf) {
		CashFlowResData bo = new CashFlowResData();
		bo.setData_typ(rcf.getData_typ());
		bo.setActl_rt(rcf.getActl_rt());
		bo.setEnding_bal(rcf.getEnding_bal());
		if (rcf.getOrig_dt() != null && rcf.getOrig_dt().after(rcf.getData_dt())) {
			bo.setOcc_dt(rcf.getOrig_dt());
		} else {
			bo.setOcc_dt(rcf.getData_dt());
		}
		return bo;
	}
	
	/**
	 * 计算等额还款的本息
	 * 
	 * @param termsLeft
	 * @return
	 */
	public Double getPrincAndInt(int termsLeft,CashFlowBaseData rcf) {
		int paymentMonths = Integer.parseInt(rcf.getPmt_freq().substring(0, rcf.getPmt_freq().indexOf("M")));
		if (rcf.getActl_rt().doubleValue() == 0.0) {
			return rcf.getEnding_bal().doubleValue() / termsLeft;
		}
		double mi = rcf.getActl_rt().doubleValue() * paymentMonths / 12.0;
		// 支付因子
		double factor = (1.0 - 1.0 / Math.pow(1.0 + mi, termsLeft)) / mi;
		return rcf.getEnding_bal().doubleValue() / factor;
	}
	
	/**
	 * 常规数据的下一重定价日计算,即重定价频率、起息日、到期日都不为空的情况
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Date getNextRepriceDate(CashFlowBaseData rcf) {
		if (rcf.getOrig_dt() == null || rcf.getMtrty_dt() == null || !AlCfUtil.checkRepriceFrequency(rcf.getRprc_freq())) {
			return rcf.getMtrty_dt();
		}

		String format = "yyyy-M-d";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		// 到期日<基期,隔夜重定价
		if (rcf.getMtrty_dt().before(rcf.getData_dt())) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getData_dt());
			ca.add(Calendar.DAY_OF_MONTH, 1);
			return ca.getTime();
		}

		if (rcf.getRprc_freq().indexOf("G") != -1) {// G1M1D的规则
			String month = rcf.getRprc_freq().substring(rcf.getRprc_freq().indexOf("G") + 1, rcf.getRprc_freq().indexOf("M"));
			String dateOfMonth = rcf.getRprc_freq().substring( rcf.getRprc_freq().indexOf("M") + 1, rcf.getRprc_freq().indexOf("D"));
			String date = sdf.format(rcf.getData_dt()).substring(0, 4) + "-" + month + "-" + dateOfMonth;
			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (nrd_temp.before(rcf.getData_dt())) {
				Calendar ca = Calendar.getInstance();
				ca.setTime(nrd_temp);
				ca.add(Calendar.YEAR, 1);
				nrd_temp = ca.getTime();
			}

			if (nrd_temp.after(rcf.getMtrty_dt())) {
				return rcf.getMtrty_dt();
			} else {
				return nrd_temp;
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1 && rcf.getRprc_freq().indexOf("M") != -1 && rcf.getRprc_freq().indexOf("D") == -1) {// B1M-每月对日类型的规则
			String month = rcf.getRprc_freq().substring(rcf.getRprc_freq().indexOf("B") + 1,rcf.getRprc_freq().indexOf("M"));
			Calendar ca = null;
			for (int i = 0;; i++) {
				ca = Calendar.getInstance();
				ca.setTime(rcf.getOrig_dt());
				ca.add(Calendar.MONTH, Integer.parseInt(month) * i);
				if (ca.getTime().after(rcf.getData_dt())) {
					break;
				}
			}
			if (ca.getTime().after(rcf.getMtrty_dt())) {
				return rcf.getMtrty_dt();
			} else {
				return ca.getTime();
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1 && rcf.getRprc_freq().indexOf("M") != -1 && rcf.getRprc_freq().indexOf("D") != -1) {// B1M21D-按月类型的规则
			String month = rcf.getRprc_freq().substring(rcf.getRprc_freq().indexOf("B") + 1,rcf.getRprc_freq().indexOf("M"));
			String dateOfMonth = rcf.getRprc_freq().substring(rcf.getRprc_freq().indexOf("M") + 1,rcf.getRprc_freq().indexOf("D"));
			int basemon = Integer.parseInt(sdf.format(rcf.getOrig_dt()).split("-")[1]);
			int month_int = Integer.parseInt(month);

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getOrig_dt()).substring(0,4)+ "-" + basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < Integer
					.parseInt(dateOfMonth)) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateOfMonth));
			}

			Calendar ca1 = null;
			for (int i = 0;; i++) {
				ca1 = Calendar.getInstance();
				ca1.setTime(ca.getTime());
				ca1.add(Calendar.MONTH, month_int * i);
				if (ca1.getTime().after(rcf.getData_dt())) {
					break;
				}
			}
			if (ca1.getTime().after(rcf.getMtrty_dt())) {
				return rcf.getMtrty_dt();
			} else {
				return ca1.getTime();
			}
		} else if (rcf.getRprc_freq().equals("B1Q")) {// B1Q-每季对日
			int dateOfMonth = Integer.parseInt(sdf.format(rcf.getOrig_dt()).split("-")[2]);
			int basemon = Integer.parseInt(sdf.format(rcf.getData_dt()).split("-")[1]);
			if(basemon%3!=0){
				basemon = ((basemon / 3)+1) * 3;
			}

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getData_dt()).substring(0,4)+ "-" + basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfMonth) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, dateOfMonth);
			}

			if (ca.getTime().before(rcf.getData_dt())) {
				ca.add(Calendar.MONTH, 3);
			}
			if (ca.getTime().after(rcf.getMtrty_dt())) {
				return rcf.getMtrty_dt();
			} else {
				return ca.getTime();
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1 && rcf.getRprc_freq().indexOf("Q") != -1 && rcf.getRprc_freq().indexOf("D") != -1) {// B1QyD-每季
			String strdateOfMonth = rcf.getRprc_freq().substring(rcf.getRprc_freq().indexOf("Q") + 1,rcf.getRprc_freq().indexOf("D"));
			int dateOfMonth = Integer.parseInt(strdateOfMonth);
			int basemon = Integer.parseInt(sdf.format(rcf.getData_dt()).split(
					"-")[1]);
			if(basemon%3!=0){
				basemon = ((basemon / 3)+1) * 3;
			}

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getData_dt()).substring(0,4) + "-" + basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfMonth) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, dateOfMonth);
			}

			if (ca.getTime().before(rcf.getData_dt())) {
				ca.add(Calendar.MONTH, 3);
			}
			if (ca.getTime().after(rcf.getMtrty_dt())) {
				return rcf.getMtrty_dt();
			} else {
				return ca.getTime();
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("M") == -1
				&& rcf.getRprc_freq().indexOf("Q") == -1
				&& rcf.getRprc_freq().indexOf("D") != -1) {// BxD-每隔几日
			String strdateOfMonth = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("B") + 1,
					rcf.getRprc_freq().indexOf("D"));
			int dateOfMonth = Integer.parseInt(strdateOfMonth);
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getOrig_dt());
			
			while (ca.getTime().compareTo(rcf.getData_dt()) > 0) {
				ca.add(Calendar.DATE, dateOfMonth);
			}
			
			if (ca.getTime().after(rcf.getMtrty_dt())) {
				return rcf.getMtrty_dt();
			} else {
				return ca.getTime();
			}
		} else {
			return null;
		}
	}
	


	/**
	 * 得到等额还本时的上一本金日
	 * 
	 * @return
	 */
	public Date evaluateLastIntDatesForP(CashFlowBaseData rcf) {
		List<Date> ret = new ArrayList<Date>();
		if (rcf.getPmt_typ() == null || "T".equals(rcf.getPmt_typ())) {
			return rcf.getOrig_dt();
		} else {
			if (AlCfUtil.checkPmtFreq(rcf.getPmt_freq(), "xM")) {// 每个x月对日
				int month = Integer.parseInt(rcf.getPmt_freq().substring(0,
						rcf.getPmt_freq().indexOf("M")));
				for (int i = 0;; i++) {
					Calendar cat = Calendar.getInstance();
					cat.setTime(rcf.getOrig_dt());
					cat.add(Calendar.MONTH, month * i);
					if (cat.getTime().compareTo(rcf.getData_dt()) >= 0) {
						break;
					}
					ret.add(cat.getTime());
				}
				if (ret.size() > 0) {
					return ret.get(ret.size() - 1);
				} else {
					return rcf.getOrig_dt();
				}
			} else if (AlCfUtil.checkPmtFreq(rcf.getPmt_freq(), "xMyD")) {// 每隔x月的d日
				int month = Integer.parseInt(rcf.getPmt_freq().substring(0,
						rcf.getPmt_freq().indexOf("M")));
				int dateOfM = Integer.parseInt(rcf.getPmt_freq().substring(
						rcf.getPmt_freq().indexOf("M") + 1,
						rcf.getPmt_freq().indexOf("D")));
				for (int i = 1;; i++) {
					Calendar cat = Calendar.getInstance();
					cat.setTime(rcf.getOrig_dt());
					cat.add(Calendar.MONTH, month * i);
					if (cat.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfM) {
						cat.set(Calendar.DAY_OF_MONTH,
								cat.getActualMaximum(Calendar.DAY_OF_MONTH));
					} else {
						cat.set(Calendar.DAY_OF_MONTH, dateOfM);
					}
					if (cat.getTime().compareTo(rcf.getData_dt()) >= 0) {
						break;
					}
					ret.add(cat.getTime());
				}
				if (ret.size() > 0) {
					return ret.get(ret.size() - 1);
				} else {
					return rcf.getOrig_dt();
				}
			} else {
				return rcf.getOrig_dt();
			}
		}
	}

	/**
	 * 根据现金流中的上一计息日，利息流发生日，利率，计结息规则计算权责发生制的利息，考虑分段计息
	 * FixedRate 和 FloatRate Discount 
	 * @param bo
	 */
	public Double evaluateIntAccrue(CashFlowResData bo/* 利息流 */,List<CashFlowResData> princlist/* 本金流集合 */, boolean isFirst,CashFlowBaseData rcf) {
		Double interest = 0.0d;
		Calendar ca = null;
		for (int i = 0; i < princlist.size() - 1; i++) {
			// 本金开始时间
			Date princ_std = princlist.get(i).getOcc_dt();
			// 本金结束时间
			Date princ_endd = princlist.get(i + 1).getOcc_dt();
			
			if(FISRTORLAST){
				ca = Calendar.getInstance();
				ca.setTime(princ_std);
				ca.add(Calendar.DATE, -1);
				princ_std=ca.getTime();
				
				ca = Calendar.getInstance();
				ca.setTime(princ_endd);
				ca.add(Calendar.DATE, -1);
				princ_endd=ca.getTime();
			}
			
			Double endingBal = princlist.get(i).getEndingBalForInt()==null?princlist.get(i).getEnding_bal():princlist.get(i).getEndingBalForInt();
			Date std = null;
			Date endd = null;
			
			Date lastIntAccureDt = null;// 计算上个计息日
			if (bo.getStart_int_accrue_dt() == bo.getOcc_dt()) {
				lastIntAccureDt = bo.getOcc_dt();
			} else { 
				lastIntAccureDt = bo.getStart_int_accrue_dt();
			}
			//  若当前现金流权责发生制开始时间>本金流结束时间，说明，该段本金结束时，还未到该段计息周期，则跳过该本金的利息计算，继续下一段的利息计算
			if (lastIntAccureDt.after(princ_endd)) {
				continue;
			}
			// 如果不是第一笔计算 若当前现金流权责发生制结束时间<本金流开始时间，则后面所有的现金流都在该段计息周期之后，跳出循环,
			if (bo.getEnd_int_accrue_dt().before(princ_std)) {
				break;
			}
			
			// 如果计息结束日期 比本金的结束日期小。
			// 那么计息结束日期就是该段的计息结束日期，否则，本金分段，取本金的结束日期
			if (bo.getEnd_int_accrue_dt().compareTo(princ_endd) <= 0) {
				endd = bo.getEnd_int_accrue_dt();
			} else {
				endd = princ_endd;
			}
			// 如果本金 和利息 都是第一笔时
			if (i == 0 && isFirst) {
				// 计息的开始日期 小于 本金的开始日期 那么计息的开始日期就是上次结息日，否则是本金的发生日期
				if (lastIntAccureDt.compareTo(princ_std) < 0) {
					std = lastIntAccureDt;
				} else {
					std = princ_std;
				}
			} else {
				// 如果上此结息日 大于 本金的开始日期，则取 上次结息日作为本段的开始日期，否则取本金的发生日期作为开始日期
				if (lastIntAccureDt.compareTo(princ_std) >= 0) {
					std = lastIntAccureDt;
				} else {
					std = princ_std;
				}
			}

			RateEval rr = new RateEval(rcf.getAccrue_basis(), rcf.getActl_rt());
			rr.parse(bo.getEnd_int_accrue_dt());
			int[] ymdd = DateUtil.ymddDiff(std, endd,rr.getNumerator());
			double interestRate = 0.0;
			if (ymdd[0] > 0) {
				interestRate += ymdd[0] * rr.getYearRate();
			}
			if (rr.isUseMonthRate()) {
				interestRate += ymdd[1] * rr.getMonthRate();
				interestRate += ymdd[3] * rr.getDayRate();
			} else { 
				interestRate += ymdd[2] * rr.getDayRate();
			}
			interest += endingBal * interestRate;
		}
		return interest;
	} 
	
	/**
	 * 在输入的时间范围内，计算出所有的重定价日
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Date> getRprcDateForDateRange(Date start, Date end,CashFlowBaseData rcf) {
		List<Date> rprpcdates = new ArrayList<Date>();
		if (start == null || end == null || !AlCfUtil.checkRepriceFrequency(rcf.getRprc_freq())) {
			return null;
		}

		String format = "yyyy-M-d";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		if (rcf.getRprc_freq().indexOf("G") != -1) {// G1M1D的规则
			int month = Integer.valueOf(rcf.getRprc_freq().substring(rcf.getRprc_freq().indexOf("G") + 1,rcf.getRprc_freq().indexOf("M")));
			int dateOfMonth = Integer.valueOf(rcf.getRprc_freq().substring(rcf.getRprc_freq().indexOf("M") + 1,rcf.getRprc_freq().indexOf("D")));
			Date nrd_temp = null;
			Calendar cal = Calendar.getInstance();
			cal.setTime(rcf.getData_dt());
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DAY_OF_MONTH, dateOfMonth);
			nrd_temp = cal.getTime();

			while (nrd_temp.compareTo(end) < 0) {
				if(nrd_temp.compareTo(start) > 0){
					rprpcdates.add(nrd_temp);
				}
				Calendar ca = Calendar.getInstance();
				ca.setTime(nrd_temp);
				ca.add(Calendar.YEAR, 1);
				nrd_temp = ca.getTime();
			}

		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("M") != -1
				&& rcf.getRprc_freq().indexOf("D") == -1) {// B1M-每月对日类型的规则
			String month = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("B") + 1,
					rcf.getRprc_freq().indexOf("M"));
			Calendar ca = null;
			for (int i = 0;; i++) {
				ca = Calendar.getInstance();
				ca.setTime(rcf.getOrig_dt());
				ca.add(Calendar.MONTH, Integer.parseInt(month) * i);
				if (ca.getTime().compareTo(start) > 0
						&& ca.getTime().compareTo(end) < 0) {
					rprpcdates.add(ca.getTime());
				} else if (ca.getTime().after(end)) {
					break;
				}
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("M") != -1
				&& rcf.getRprc_freq().indexOf("D") != -1) {// B1M21D-按月类型的规则
			String month = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("B") + 1,
					rcf.getRprc_freq().indexOf("M"));
			String dateOfMonth = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("M") + 1,
					rcf.getRprc_freq().indexOf("D"));
			int basemon = Integer.parseInt(sdf.format(rcf.getOrig_dt()).split(
					"-")[1]);
			int month_int = Integer.parseInt(month);

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getOrig_dt()).substring(0, 4) + "-"
						+ basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < Integer
					.parseInt(dateOfMonth)) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateOfMonth));
			}
			Calendar ca1 = null;
			for (int i = 0;; i++) {
				ca1 = Calendar.getInstance();
				ca1.setTime(ca.getTime());
				ca1.add(Calendar.MONTH, month_int * i);
				if (ca1.getTime().after(start) && ca1.getTime().before(end)) {
					rprpcdates.add(ca1.getTime());
				} else if (ca1.getTime().after(end)) {
					break;
				}
			}
		} else if (rcf.getRprc_freq().equals("B1Q")) {// B1Q-每季对日
			int basemon = Integer.parseInt(sdf.format(rcf.getData_dt()).split(
					"-")[1]);
			int dateOfMonth = Integer.parseInt(sdf.format(rcf.getOrig_dt()).split(
					"-")[2]);
			if(basemon%3!=0){
				basemon = ((basemon / 3)+1) * 3;
			}

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getData_dt()).substring(0, 4) + "-"
						+ basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfMonth) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, dateOfMonth);
			}
			while (ca.getTime().compareTo(end) < 0) {
				
				if(ca.getTime().compareTo(start) > 0
						&& ca.getTime().compareTo(end) < 0){
					rprpcdates.add(ca.getTime());
				}
				ca.add(Calendar.MONTH, 3);
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("Q") != -1
				&& rcf.getRprc_freq().indexOf("D") != -1) {// B1QyD-每季
			String strdateOfMonth = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("Q") + 1,
					rcf.getRprc_freq().indexOf("D"));
			int basemon = Integer.parseInt(sdf.format(rcf.getData_dt()).split(
					"-")[1]);
			int dateOfMonth = Integer.parseInt(strdateOfMonth);
			if(basemon%3!=0){
				basemon = ((basemon / 3)+1) * 3;
			}

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getData_dt()).substring(0, 4) + "-"
						+ basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfMonth) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, dateOfMonth);
			}
			while (ca.getTime().compareTo(end) < 0) {
				
				if(ca.getTime().compareTo(start) > 0
						&& ca.getTime().compareTo(end) < 0){
					rprpcdates.add(ca.getTime());
				}
				ca.add(Calendar.MONTH, 3);
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("M") == -1
				&& rcf.getRprc_freq().indexOf("Q") == -1
				&& rcf.getRprc_freq().indexOf("D") != -1) {// BxD-每隔几日
			String strdateOfMonth = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("B") + 1,
					rcf.getRprc_freq().indexOf("D"));
			int dateOfMonth = Integer.parseInt(strdateOfMonth);
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getOrig_dt());
			
			while (ca.getTime().compareTo(end) < 0) {
				if(ca.getTime().compareTo(start) > 0
						&& ca.getTime().compareTo(end) < 0){
					rprpcdates.add(ca.getTime());
				}
				ca.add(Calendar.DATE, dateOfMonth);
			}
			
		}
		return rprpcdates;
	}
	
	/**
	 * 计算当前现金流(利息流)的上一重定价日
	 * 
	 * @return
	 */
	public Date getLastRepriceDate(CashFlowResData bo,CashFlowBaseData rcf) {
		Date baseDt = rcf.getOrig_dt();
		Date occt = null;
		occt = bo.getStart_int_accrue_dt();
		if (baseDt == null) {
			baseDt = rcf.getData_dt();
		}
		if (rcf.getMtrty_dt() == null || !AlCfUtil.checkRepriceFrequency(rcf.getRprc_freq())) {
			return baseDt;
		}

		String format = "yyyy-M-d";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		if (rcf.getRprc_freq().indexOf("G") != -1) {// G1M1D的规则
			String month = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("G") + 1,
					rcf.getRprc_freq().indexOf("M"));
			String dateOfMonth = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("M") + 1,
					rcf.getRprc_freq().indexOf("D"));
			String date = sdf.format(occt).substring(0, 4) + "-"
					+ month + "-" + dateOfMonth;
			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (nrd_temp.after(occt)) {
				Calendar ca = Calendar.getInstance();
				ca.setTime(nrd_temp);
				ca.add(Calendar.YEAR, -1);
				nrd_temp = ca.getTime();
			}

			if (nrd_temp.before(baseDt)) {
				return baseDt;
			} else {
				return nrd_temp;
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("M") != -1
				&& rcf.getRprc_freq().indexOf("D") == -1) {// B1M-每月对日类型的规则
			String month = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("B") + 1,
					rcf.getRprc_freq().indexOf("M"));
			Calendar ca = null;
			int i = 0;
			for (;; i++) {
				ca = Calendar.getInstance();
				ca.setTime(baseDt);
				ca.add(Calendar.MONTH, Integer.parseInt(month) * i);
				if (ca.getTime().after(occt)) {
					break;
				}
			}
			ca = Calendar.getInstance();
			ca.setTime(baseDt);
			ca.add(Calendar.MONTH, Integer.parseInt(month) * (i - 1));
			if (ca.getTime().before(baseDt)) {
				return baseDt;
			} else {
				return ca.getTime();
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("M") != -1
				&& rcf.getRprc_freq().indexOf("D") != -1) {// B1M21D-按月类型的规则
			String month = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("B") + 1,
					rcf.getRprc_freq().indexOf("M"));
			String dateOfMonth = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("M") + 1,
					rcf.getRprc_freq().indexOf("D"));
			int basemon = Integer.parseInt(sdf.format(rcf.getOrig_dt()).split(
					"-")[1]);
			int month_int = Integer.parseInt(month);

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getOrig_dt()).substring(0, 4) + "-"
						+ basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < Integer
					.parseInt(dateOfMonth)) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateOfMonth));
			}
			Calendar ca1 = null;
			Calendar ca2 = Calendar.getInstance();
			for (int i = 0;; i++) {
				ca1 = Calendar.getInstance();
				ca1.setTime(ca.getTime());
				ca1.add(Calendar.MONTH, month_int * i);
				if (ca1.getTime().after(occt)) {
					break;
				}
				ca2.setTime(ca1.getTime());
			}
			if (ca2.getTime().before(baseDt)) {
				return baseDt;
			} else {
				return ca2.getTime();
			}
		} else if (rcf.getRprc_freq().equals("B1Q")) {// B1Q-每季对日
			int dateOfMonth = Integer.parseInt(sdf.format(rcf.getOrig_dt()).split(
					"-")[2]);
			int basemon = Integer.parseInt(sdf.format(rcf.getData_dt()).split(
					"-")[1]);
			if(basemon%3!=0){
				basemon = ((basemon / 3)+1) * 3;
			}

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getData_dt()).substring(0,
						4)
						+ "-" + basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfMonth) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, dateOfMonth);
			}

			Calendar cal = null;
			int i = 0;
			for (;; i++) {
				cal = Calendar.getInstance();
				cal.setTime(ca.getTime());
				cal.add(Calendar.MONTH, 3 * i);
				if (cal.getTime().after(occt)) {
					break;
				}
			}
			cal = Calendar.getInstance();
			cal.setTime(baseDt);
			cal.add(Calendar.MONTH, 3 * (i - 1));
			if (ca.getTime().before(baseDt)) {
				return baseDt;
			} else {
				return ca.getTime();
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("Q") != -1
				&& rcf.getRprc_freq().indexOf("D") != -1) {// B1QyD-每季
			String strdateOfMonth = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("Q") + 1,
					rcf.getRprc_freq().indexOf("D"));
			int dateOfMonth = Integer.parseInt(strdateOfMonth);
			int basemon = Integer.parseInt(sdf.format(rcf.getData_dt()).split(
					"-")[1]);
			if(basemon%3!=0){
				basemon = ((basemon / 3)+1) * 3;
			}

			Date nrd_temp = null;
			try {
				nrd_temp = sdf.parse(sdf.format(rcf.getData_dt()).substring(0,
						4)
						+ "-" + basemon + "-1");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(nrd_temp);
			if (ca.getActualMaximum(Calendar.DAY_OF_MONTH) < dateOfMonth) {
				ca.set(Calendar.DAY_OF_MONTH,
						ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				ca.set(Calendar.DAY_OF_MONTH, dateOfMonth);
			}

			Calendar cal = null;
			int i = 0;
			for (;; i++) {
				cal = Calendar.getInstance();
				cal.setTime(ca.getTime());
				cal.add(Calendar.MONTH, 3 * i);
				if (cal.getTime().after(occt)) {
					break;
				}
			}
			cal = Calendar.getInstance();
			cal.setTime(baseDt);
			cal.add(Calendar.MONTH, 3 * (i - 1));
			if (ca.getTime().before(baseDt)) {
				return baseDt;
			} else {
				return ca.getTime();
			}
		} else if (rcf.getRprc_freq().indexOf("B") != -1
				&& rcf.getRprc_freq().indexOf("M") == -1
				&& rcf.getRprc_freq().indexOf("Q") == -1
				&& rcf.getRprc_freq().indexOf("D") != -1) {// BxD-每隔几日
			String strdateOfMonth = rcf.getRprc_freq().substring(
					rcf.getRprc_freq().indexOf("B") + 1,
					rcf.getRprc_freq().indexOf("D"));
			int dateOfMonth = Integer.parseInt(strdateOfMonth);
			Calendar ca = Calendar.getInstance();
			ca.setTime(rcf.getOrig_dt());
			
			Calendar cal = null;
			int i = 0;
			for (;; i++) {
				cal = Calendar.getInstance();
				cal.setTime(ca.getTime());
				cal.add(Calendar.DATE, dateOfMonth * i);
				if (cal.getTime().after(occt)) {
					break;
				}
			}
			cal = Calendar.getInstance();
			cal.setTime(baseDt);
			cal.add(Calendar.MONTH, dateOfMonth * (i - 1));
			
			if (ca.getTime().before(baseDt)) {
				return baseDt;
			} else {
				return ca.getTime();
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 合并现金流，本金流、利息收付实现制、利息权责发生制
	 * 
	 * @param princ
	 * @param intocf
	 * @param intaccocf
	 * @return
	 */
	public List<CashFlowResData> mergeAlCf(List<CashFlowResData> princ/* 本金流集合 */, List<CashFlowResData> intaccocf/* 利息权责发生制集合 */) {
		List<CashFlowResData> ret = new ArrayList<CashFlowResData>();
		CashFlowResData Bprinc = null;
		CashFlowResData Bintaccocf = null;
		int i = 0, k = 0;
		for (;;) {
			if (Bprinc == null && princ != null && i < princ.size()) {
				Bprinc = princ.get(i);
			}

			if (Bintaccocf == null && intaccocf != null && k < intaccocf.size()) {
				Bintaccocf = intaccocf.get(k);
			}

			if (Bprinc == null &&  Bintaccocf == null) {// 1
				break;
			}

			if (Bprinc != null &&  Bintaccocf == null) {// 2
				ret.add(princ.get(i++));
				Bprinc = null;
				continue;
			}

			if (Bprinc == null &&  Bintaccocf != null) {// 4
				ret.add(intaccocf.get(k++));
				Bintaccocf = null;
				continue;
			}
//
//			if (Bprinc != null && Bintaccocf == null) {// 5
//				if (Bprinc.getOccDt().compareTo(Bintocf.getOccDt()) < 0) {
//					ret.add(princ.get(i++));
//					Bprinc = null;
//					continue;
//				} else {
//					ret.add(intocf.get(j++));
//					Bintocf = null;
//					continue;
//				}
//			}
//
//			if (Bprinc != null &&  Bintaccocf != null) {// 6
//				if (Bprinc.getOccDt().compareTo(Bintaccocf.getOccDt()) <= 0) {
//					ret.add(princ.get(i++));
//					Bprinc = null;
//					continue;
//				} else {
//					ret.add(intaccocf.get(k++));
//					Bintaccocf = null;
//					continue;
//				}
//			}
//			// 把利息数据进行合并处理。减少数据量， modify by lfb 2018-07-25
//			if (Bprinc == null && Bintocf != null && Bintaccocf != null) {// 7
//				// 如果发生日期一致，需要比对上一重定日是否一样
//				if(Bintocf.getOccDt().compareTo(Bintaccocf.getOccDt()) == 0){
//					if((Bintocf.getLstRprcDt()!=null && Bintaccocf.getLstRprcDt()!=null &&Bintocf.getLstRprcDt().compareTo(Bintaccocf.getLstRprcDt()) == 0)
//							|| (Bintocf.getLstRprcDt()==null && Bintaccocf.getLstRprcDt()==null) 
//					){
//						// 合并数据
//						Bintocf.setIntAccrue(Bintaccocf.getIntAccrue());
//						Bintocf.setBpIntAccrue(Bintaccocf.getBpIntAccrue());
//						Bintocf.setStartIntAccrueDt(Bintaccocf.getStartIntAccrueDt());
//						Bintocf.setEndIntAccrueDt(Bintaccocf.getEndIntAccrueDt());
//						ret.add(Bintocf);
//						j++;
//						k++;
//						Bintocf = null;
//						Bintaccocf=null;
//						continue;
//					}else{
//						ret.add(intocf.get(j++));
//						Bintocf = null;
//						continue;
//					}
//				}else if(Bintocf.getOccDt().compareTo(Bintaccocf.getOccDt()) < 0) {
//					ret.add(intocf.get(j++));
//					Bintocf = null;
//					continue;
//				} else {
//					ret.add(intaccocf.get(k++));
//					Bintaccocf = null;
//					continue;
//				}
//			}
//
//			if (Bprinc != null && Bintocf != null && Bintaccocf != null) {// 8
//				if (Bprinc.getOccDt().compareTo(Bintocf.getOccDt()) <= 0) {
//					if (Bprinc.getOccDt().compareTo(Bintaccocf.getOccDt()) <= 0) {
//						ret.add(princ.get(i++));
//						Bprinc = null;
//						continue;
//					} else {
//						ret.add(intaccocf.get(k++));
//						Bintaccocf = null;
//						continue;
//					}
//				} else {
//					// 把利息数据进行合并处理。减少数据量， modify by lfb 2018-07-25
//					if(Bintocf.getOccDt().compareTo(Bintaccocf.getOccDt()) == 0){
//						// 如果发生日期一致，需要比对上一重定日是否一样
//						if((Bintocf.getLstRprcDt()!=null && Bintaccocf.getLstRprcDt()!=null && Bintocf.getLstRprcDt().compareTo(Bintaccocf.getLstRprcDt()) == 0)
//							|| (Bintocf.getLstRprcDt()==null && Bintaccocf.getLstRprcDt()==null) 	
//						){
//							// 合并数据
//							Bintocf.setIntAccrue(Bintaccocf.getIntAccrue());
//							Bintocf.setBpIntAccrue(Bintaccocf.getBpIntAccrue());
//							Bintocf.setStartIntAccrueDt(Bintaccocf.getStartIntAccrueDt());
//							Bintocf.setEndIntAccrueDt(Bintaccocf.getEndIntAccrueDt());
//							ret.add(Bintocf);
//							j++;
//							k++;
//							Bintocf = null;
//							Bintaccocf=null;
//							continue;
//						}else{
//							ret.add(intocf.get(j++));
//							Bintocf = null;
//							continue;
//						}
//					}else if (Bintocf.getOccDt().compareTo(Bintaccocf.getOccDt()) < 0) {
//						ret.add(intocf.get(j++));
//						Bintocf = null;
//						continue;
//					} else {
//						ret.add(intaccocf.get(k++));
//						Bintaccocf = null;
//						continue;
//					}
//				}
//			}

		}
		return ret;
	} 
	
	/**
	 * 计算给定时间周期内收付实现制的利息（时间周期遵循左开右闭原则），默认时间周期在一个计息周期内
	 * 
	 * @param bo
	 */
	public Double evaluateInt(CashFlowResData bo,Date start,Date end,CashFlowBaseData rcf) {
		int isflag = 0;
		if(start.compareTo(rcf.getOrig_dt())==0 && end.compareTo(rcf.getMtrty_dt())!=0)
			isflag = 1;
		if(start.compareTo(rcf.getOrig_dt())!=0 && end.compareTo(rcf.getMtrty_dt())==0)
			isflag = 2;
		RateEval rr = new RateEval(rcf.getAccrue_basis(), rcf.getActl_rt());
		rr.parse(end);
		int[] ymdd = DateUtil.ymddDiff(start, end,rr.getNumerator());
		double interest = 0.0;
		if (ymdd[0] > 0) {
			interest += ymdd[0] * rr.getYearRate();
		}
		if (rr.isUseMonthRate()) {
			interest += ymdd[1] * rr.getMonthRate();
			interest += ymdd[3] * rr.getDayRate();
		} else {
			if(isflag == 1)
				interest += (ymdd[2]+1) * rr.getDayRate();
			else if(isflag ==2 && ymdd[2]>=1)
				interest += (ymdd[2]-1) * rr.getDayRate();
			else 
				interest += ymdd[2] * rr.getDayRate();
		}
		return (bo.getEndingBalForInt()==null?bo.getBeginningBal():bo.getEndingBalForInt()) * interest;
	}
	
	/**
	 * 计算给定时间周期内收付实现制的利息（时间周期遵循左开右闭原则），默认时间周期在一个计息周期内
	 * 只针对于FloatRate
	 * @param bo
	 */
	public Double evaluateInt(CashFlowResData bo/* 利息流 */,List<CashFlowResData> princlist/* 本金流集合 */, boolean isFirst,CashFlowBaseData rcf) {
		double interest = 0.0;
		Date princ_std = null;
		Date princ_endd = null;
		Double endingBal = null;
		Date std = null;
		Date endd = null;

		Date lastIntDt = null;// 计算上个计息日
		RateEval rr = null ;
		int[] ymdd = null ;
		Calendar ca = null;
		double interestRate=0.0;
		for (int i = 0; i < princlist.size() - 1; i++) {
			princ_std = princlist.get(i).getOcc_dt();
			princ_endd = princlist.get(i + 1).getOcc_dt();
			endingBal = princlist.get(i).getEndingBalForInt()==null?princlist.get(i).getEnding_bal():princlist.get(i).getEndingBalForInt();
			if(FISRTORLAST){
				ca = Calendar.getInstance();
				ca.setTime(princ_std);
				ca.add(Calendar.DATE, -1);
				princ_std=ca.getTime();
				
				ca = Calendar.getInstance();
				ca.setTime(princ_endd);
				ca.add(Calendar.DATE, -1);
				princ_endd=ca.getTime();
			}
			if (bo.getStart_int_dt() == bo.getOcc_dt()) {
				lastIntDt = bo.getOcc_dt();
			} else {
//				lastIntDt = DateUtil.getLastDate(bo.getStartIntDt());
				lastIntDt = bo.getStart_int_dt();
			}

			if (lastIntDt.after(princ_endd)) {
				continue;
			}
			if (bo.getEnd_int_dt().before(princ_std)) {
				break;
			}
			if (bo.getEnd_int_dt().compareTo(princ_endd) <= 0) {
				endd = bo.getEnd_int_dt();
			} else {
				endd = princ_endd;
			}
			if (i == 0 && isFirst) {
				if (lastIntDt.compareTo(princ_std) < 0) {
					std = lastIntDt;
				} else {
					std = princ_std;
				}
			} else {
				if (lastIntDt.compareTo(princ_std) >= 0) {
					std = lastIntDt;
				} else {
					std = princ_std;
				}
			} 
			rr = new RateEval(rcf.getAccrue_basis(), rcf.getActl_rt());
			rr.parse(bo.getEnd_int_dt());
			ymdd = DateUtil.ymddDiff(std, endd,rr.getNumerator());
			interestRate = 0.0;
			if (ymdd[0] > 0) {
				interestRate += ymdd[0] * rr.getYearRate();
			}
			if (rr.isUseMonthRate()) {
				interestRate += ymdd[1] * rr.getMonthRate();
				interestRate += ymdd[3] * rr.getDayRate();
			} else {
				interestRate += ymdd[2] * rr.getDayRate();
			}
			interest += endingBal * interestRate;
		}
		return interest;
	}
	
}
