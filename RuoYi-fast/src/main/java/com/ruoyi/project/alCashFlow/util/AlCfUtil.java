package com.ruoyi.project.alCashFlow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.project.al.cashFlow.domain.CashFlowBaseData;


public class AlCfUtil {

	
	
	/**
	 * 校验还本计划,判断pmt_freq是否是rule的规则，rule的取值xM、xMyD、1Q、1QyD
	 * @param pmt_freq
	 * @param string
	 * @return
	 */
	public static boolean checkPmtFreq(String pmt_freq,String rule/*xM,xMyD,1Q,1QyD*/) {
		if(pmt_freq==null){
			return false;
		}
		if("xMyD".equals(rule)){
			if(pmt_freq.indexOf("D")+1==pmt_freq.length()){
				try{
					int month = Integer.parseInt(pmt_freq.substring(0,pmt_freq.indexOf("M")));
					if(month>0 && month<=12){
						int date = Integer.parseInt(pmt_freq.substring(pmt_freq.indexOf("M")+1,pmt_freq.indexOf("D")));
						if(date>0 && date<=31){
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} catch (Exception e){
					return false;
				}
			} else {
				return false;
			}
		} else if("xM".equals(rule)){
			if(pmt_freq.indexOf("M")+1==pmt_freq.length()){
				try{
					int month = Integer.parseInt(pmt_freq.substring(0,pmt_freq.indexOf("M")));
					if(month>0 && month<=12){
						return true;
					} else {
						return false;
					}
				} catch (Exception e){
					return false;
				}
			} else {
				return false;
			}
		} else if("1Q".equals(rule)){
			if("1Q".equals(pmt_freq)){
				return true;
			} else {
				return false;
			}
		} else if("1QyD".equals(rule)){
			if(pmt_freq.indexOf("1Q")==0 && pmt_freq.indexOf("D")+1==pmt_freq.length()){
				int date = Integer.parseInt(pmt_freq.substring(2,pmt_freq.indexOf("D")));
				if(date>0 && date<=31){
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}


	/**
	 * 校验计结息频率
	 * QE-每季末
	 * M20-每月20日
	 * M-每月对日
	 * Q-每季对日
	 * Y-每年对日
	 * T-到期
	 * ME-每月末
	 * Q20-每季20日
	 * S-每半年对日
	 * 20D-每隔20日
	 * 1M20D-每年1月20日
	 */
	public static boolean checkFrequency(String frequency){
		if(frequency==null){
			return false;
		}
		Map<String,String> temp = new HashMap<String,String>();
		temp.put("QE", "1");
		temp.put("M", "1");
		temp.put("Q", "1");
		temp.put("Y", "1");
		temp.put("T", "1");
		temp.put("ME", "1");
		temp.put("S", "1");
		if(temp.get(frequency)!=null){
			return true;
		} else if(checkFrequency(frequency,"Q")||checkFrequency(frequency,"M")||checkFrequency(frequency,"D")||checkFrequency(frequency,"MD")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 校验计结息规则
	 * ACTUAL/ACTUAL
	 * ACTUAL/360
	 * ACTUAL/365
	 * M/12
	 * 30/360
	 */
	public static boolean checkAccrualBasis(String accrualBasis){
		if(accrualBasis==null){
			return false;
		}
		Map<String,String> temp = new HashMap<String,String>();
		temp.put("ACTUAL/ACTUAL", "1");
		temp.put("ACTUAL/360", "1");
		temp.put("ACTUAL/365", "1");
		temp.put("M/12", "1");
		temp.put("30/360", "1");
		if(temp.get(accrualBasis)!=null){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 校验计结息规则针对Q20，M20,20D,1M20D
	 */
	public static boolean checkFrequency(String frequency/*Q20，M20,20D,1M20D*/,String type/*Q,M,D,MD*/){
		if("Q".equals(type)||"M".equals(type)){
			if(frequency.indexOf(type)==0){
				int num = Integer.parseInt(frequency.substring(1));
				if(num>0 && num<=31){
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if("D".equals(type)){
			if(frequency.indexOf("D")+1==frequency.length()){
				try{
					@SuppressWarnings("unused")
					int num = Integer.parseInt(frequency.substring(0,frequency.indexOf("D")));
					return true;
				} catch (Exception e){
					return false;
				}
			} else {
				return false;
			}
		} else if("MD".equals(type)){
			int Mnum = frequency.indexOf("M");
			int Dnum = frequency.indexOf("D");
			if(Dnum>Mnum && Dnum+1==frequency.length()){
				try{
					int month = Integer.parseInt(frequency.substring(0,Mnum));
					int date = Integer.parseInt(frequency.substring(Mnum+1,Dnum));
					if(month>0 && month<=12 && date>0 && date<=31){
						return true;
					} else {
						return false;
					}
				} catch (Exception e){
					return false;
				}
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
	
	/**
	 * 校验重定价频率规则,true:校验通过,false:校验不通过
	 * B1M21D-按月
	 * B1M-每月对日
	 * B3M-每三个月对日
	 * B1Q-每季对日
	 * B1Q21D-每季
	 * B1D-每隔几日
	 * B6M-每半年对日
	 * B12M-每年对日
	 * G1M1D-每年1月1日
	 * @param repriceFrequency
	 * @return
	 */
	public static boolean checkRepriceFrequency(String repriceFrequency){
		if(repriceFrequency==null){
			return false;
		}
		
		int Gnum = repriceFrequency.indexOf("G");
		int Mnum = repriceFrequency.indexOf("M");
		int Dnum = repriceFrequency.indexOf("D");
		int Bnum = repriceFrequency.indexOf("B");
		int Qnum = repriceFrequency.indexOf("Q");
		
		if(Gnum==0 && Mnum>0 && Dnum>Mnum && Dnum+1==repriceFrequency.length()){//G1M1D-每年1月1日
			try{
				int month = Integer.parseInt(repriceFrequency.substring(Gnum+1,Mnum));
				int date = Integer.parseInt(repriceFrequency.substring(Mnum+1,Dnum));
				if(month>0 && month<=12 && date>0 && date<=31){
					return true;
				} else {
					return false;
				}
			} catch (Exception e){
				return false;
			}
		} else if(Bnum==0 && Mnum+1==repriceFrequency.length()){//BxM,BQM
			try{
				int month = Integer.parseInt(repriceFrequency.substring(Bnum+1,Mnum));
				if(month>0 && month<=12){
					return true;
				}
			} catch (Exception e){
				return false;
			}
		} else if(Bnum==0 && Mnum>0 && Dnum>Mnum && Dnum+1==repriceFrequency.length()){//BxMyD
			try{
				int month = Integer.parseInt(repriceFrequency.substring(Bnum+1,Mnum));
				int date = Integer.parseInt(repriceFrequency.substring(Mnum+1,Dnum));
				if((month>0 && month<=12) && date>0 && date<=31){
					return true;
				} else {
					return false;
				}
			} catch (Exception e){
				return false;
			}
		} else if(repriceFrequency.equals("B1Q")){//B1Q
			return true;
		} else if(Bnum==0 && Qnum>0 && Dnum>Qnum && Dnum+1==repriceFrequency.length()){//B1QyD
			try{
				int date = Integer.parseInt(repriceFrequency.substring(Qnum+1,Dnum));
				if(date>0 && date<=31){
					return true;
				} else {
					return false;
				}
			} catch (Exception e){
				return false;
			}
		} else if(Bnum==0 && Dnum+1==repriceFrequency.length()){//B1D
			try{
				int date = Integer.parseInt(repriceFrequency.substring(Bnum+1,Dnum));
				if(date>0 && date<=31){
					return true;
				}
			} catch (Exception e){
				return false;
			}
		}
		return false;
	}
	
	
}
