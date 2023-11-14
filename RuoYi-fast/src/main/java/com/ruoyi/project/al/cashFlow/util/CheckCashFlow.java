package com.ruoyi.project.al.cashFlow.util;

public class CheckCashFlow {

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
}
