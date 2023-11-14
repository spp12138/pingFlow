package com.ruoyi.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <p>文件名: DateUtil.java</p>  
 * <p>描述: 日期时间工具类</p>  
 * <p>版权: Copyright (c) 2018</p>  
 * <p>公司: 信雅达 Sunyard</p>  
 * @author 桑一平  
 * @date 2019年8月20日
 * @version 1.0  
 * 创建时间:  2019年8月20日14:43:40
 *   
 * 修改历史:   
 * 时    间                          作    者                版    本             描    述   
 * ----------- -------- -------- -------------------------------------- 
 * 2019年8月20日      桑一平                1.0     新建
 *
 */
public class DateUtil {
	
	public static Log log = LogFactory.getLog(DateUtil.class);
	public static final long Milliseconds_HOUR = (long) (1000 * 3600);
	public static final long Milliseconds_DAY = (long) (Milliseconds_HOUR * 24);

	public static String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";
	
    /**
     * 日期格式： yyyyMMdd
     */
    public static final String DATE_FMT_YYYYMMDD = "yyyyMMdd";
    
    public static final String DATE_FMT_YYYY_MM_DD = "yyyy-MM-dd";
    
    public static final String DATE_FMT_HHMMSS = "HHmmss";
	
    public static final String DATE_FMT_HHMMSSSSS = "HHmmssSSS";

    public static final String DATE_FMT_MMDD = "MMdd";
    
    public static final String DATE_FMT_YYYYMMDDHHMMSS = "yyyyMMddhhmmss";
    
	public static Date parseDate(String strDate) {
		return parseDate(strDate, defaultDatePattern);
	}
	
	/**
	 * 字符串转化为日期
	 * @author SangYiPing 2015-01-09
	 * @param strDate  日期字符串
	 * @param datePattern  要转化的模式
	 * @return
	 */
	public static final Date parseDate(String strDate, String datePattern) {
		SimpleDateFormat df = new SimpleDateFormat(datePattern);
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			log.error("ParseException: " + pe);
		}
		return date;
	}

	/**
	 * 获得当前时间
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	public static String format(Date datetime) {
		return format(datetime, defaultDatePattern);
	}

	
	/**
	 * 日期转化字符串
	 * @author SangYiPing 2015-01-09
	 * @param datetime 日期
	 * @param pattern  转化模式
	 * @return
	 */
	public static String format(Date datetime, String pattern) {
		if (datetime == null) {
			return "";
		}
		return new SimpleDateFormat(pattern).format(datetime);
	}

	/**
	 * 计算两个日期之间间隔的天数
	 * @author SangYiPing 2015-01-09
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int diffDay(Date d1, Date d2) {
		double diff = d1.getTime() - d2.getTime();
		double ddiff = Math.ceil(diff / Milliseconds_DAY);
		return (int) ddiff;
	}

	/**
	 * 取得系统当前年月日,日期格式为"yyyyMMdd"
	 * @return
	 */
	public static String getYearMonthDay() {
		return format(new Date(), DATE_FMT_YYYYMMDD);
	}
	
	/**
	 * 取得系统当前年月日,日期格式为"yyyyMMdd"
	 * @return
	 */
	public static String getYearMonthDay1() {
		return format(new Date(), DATE_FMT_YYYY_MM_DD);
	}	
	/**
	 * 取得系统当前年月日,日期格式为"yyyy-MM-dd HH:mm:ss:ms"
	 * @return
	 */
	public static String getYearMonthDay3() {
		return format(new Date(), "yyyy-MM-dd HH:mm:ss:ms");
	}	
	/**
	 * 取得系统当前年时间,日期格式为"HHmmss"
	 * @return
	 */
	public static String getHourMinuteSecond(){
		return format(new Date(), DATE_FMT_HHMMSS);
	}
	
	public static Date addDay(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, amount);
		return cal.getTime();
	}
	public static Date addMonth(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}
	public static Date addYear(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		return cal.getTime();
	}
	
	//得到系统年份yyyy
	public static String getYear(){
		Calendar cal=Calendar.getInstance();
		
		Integer year=cal.get(Calendar.YEAR);
		String strYear=year.toString();
		
		return strYear;
	}
	
	/**
	 * 取得系统当前月日,日期格式为"MMdd"
	 * @return
	 */
	public static String getPriMonthDay() {
		Date date = addDay(new Date(), -1);
		return format(date,DATE_FMT_MMDD);
	}
	
	/**
	 * 取得系统当前年月日,日期格式为"yyyyMMdd"
	 * @return
	 */
	public static String getCurrMonthDay() {
		return format(new Date(),DATE_FMT_MMDD);
	}

	public static String getSysTime() {
		return format(new Date(),DATE_FMT_YYYYMMDDHHMMSS);
	}
	
	public static String getPriYearMonthDay() {
		Date date = addDay(new Date(), -1);
		return format(date,DATE_FMT_YYYYMMDD);
	}	
	
	/**
	 * 获取当前日期   yyyyMMdd格式
	 * @param formatDate
	 * @return
	 */
	public static String getDate(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	/**
	 * 获取当前日期   yyyy-MM-dd格式
	 * @param formatDate
	 * @return
	 */
	public static String getDateFormat(){
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}
	/**
	 * 获取当前日期   yyyyMMdd格式
	 * @param formatDate
	 * @return
	 */
	public static String getDate(String formatDate){
		return new SimpleDateFormat(formatDate).format(new Date());
	}
	/**
	 * 获取当前日期   yyyyMMdd格式
	 * @param formatDate
	 * @return
	 */
	public static String getDate(Date date,String formatDate){
		return new SimpleDateFormat(formatDate).format(date);
	}
	
	/**
	 * 获取当前日期 传入int天数  yyyyMMdd格式
	 * 正数加	负数减
	 * @param add 
	 * @return
	 */
	public static String getDate(int add){
		return getDate("yyyyMMdd",add);
	}
	
	/**
	 * 获取当前日期 
	 * @param formatDate 格式化样式 （yyyyMMdd）或（yyyy-mm-dd）等
	 * @param add 正数加	负数减 天数
	 * @return
	 */
	public static String getDate(String formatDate , int add){
		GregorianCalendar gc =new GregorianCalendar();
		SimpleDateFormat sf  =new SimpleDateFormat(formatDate);
		gc.setTime(new Date());
		gc.add(Calendar.DATE,add);
		return sf.format(gc.getTime());
	}
	/**
	 * 传入String日期
	 * 返回 Date 类型 
	 * @param date 日期
	 * @param form 格式
	 * @return
	 */
	public static Date getStringToDate(String date,String form){
		SimpleDateFormat sf  =new SimpleDateFormat(form);
		Date parse = null;
		try {
			parse = sf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
	}
	
	/**
	 * 传入String日期
	 * 返回 Date 类型 
	 * @return
	 */
	public static Date getStringToDate(String date){
		return getStringToDate(date,"yyyyMMdd");
	}
	
	/**
	 * 传入Date日期
	 * 返回 String 类型 
	 * @return
	 */
	public static String getDateToString(Date date){
		return getDateToString(date,"yyyyMMdd");
	}
	
	/**
	 * 传入Date日期
	 * 返回 String 类型 
	 * @param date 日期
	 * @param form 格式
	 * @return
	 */
	public static String getDateToString(Date date,String form){
		SimpleDateFormat sf  =new SimpleDateFormat(form);
		return sf.format(date);
	}
	
	/**
	 * 获取当前时间  HH:mm:ss 格式
	 */
	public static String getTime(){
		return new SimpleDateFormat("HH:mm:ss").format(new Date().getTime());
	}

	
	/**
	 * 计算两个日期之间的差距
	 * @param dt1,小
	 * @param dt2,大
	 * @return 	0:年差
	 * 			1:月差
	 * 			2:一年内的天数差
	 * 			3:一个月内的天数差
	 */
	
	@SuppressWarnings("deprecation")
	public static int[] ymddDiff(Date dt1,Date dt2,String accrueBasis){
		int[] ymdd = {0, 0, 0, 0};
		int monthDiff = (dt2.getYear()-dt1.getYear())*12+dt2.getMonth()-dt1.getMonth();
		Calendar ca1 = Calendar.getInstance();
		ca1.setTime(dt1);
		Calendar ca2 = Calendar.getInstance();
		ca2.setTime(dt2);
		Calendar ca11 = Calendar.getInstance();
		ca11.setTime(dt1);
		Calendar ca22 = Calendar.getInstance();
		ca22.setTime(dt2);
		ca11.set(Calendar.DAY_OF_MONTH, ca11.getActualMaximum(Calendar.DAY_OF_MONTH));
		ca22.set(Calendar.DAY_OF_MONTH, ca22.getActualMaximum(Calendar.DAY_OF_MONTH));
		if(ca11.get(Calendar.DAY_OF_MONTH)!=ca1.get(Calendar.DAY_OF_MONTH) && ca22.get(Calendar.DAY_OF_MONTH)!=ca2.get(Calendar.DAY_OF_MONTH) && ca1.get(Calendar.DAY_OF_MONTH)!= ca2.get(Calendar.DAY_OF_MONTH)){
			if(ca1.get(Calendar.DAY_OF_MONTH)> ca2.get(Calendar.DAY_OF_MONTH)){
				monthDiff--;
			}
			ymdd[0] = monthDiff / 12;
	        ymdd[1] = monthDiff % 12;
	        ca1.add(Calendar.YEAR, ymdd[0]);
	        ymdd[2] = (int)((ca2.getTimeInMillis() - ca1.getTimeInMillis())/86400000);
	        ca1.add(Calendar.MONTH, ymdd[1]);
	        ymdd[3] = (int)((ca2.getTimeInMillis() - ca1.getTimeInMillis())/86400000);
		} else if(ca11.get(Calendar.DAY_OF_MONTH)==ca1.get(Calendar.DAY_OF_MONTH) && ca22.get(Calendar.DAY_OF_MONTH)!=ca2.get(Calendar.DAY_OF_MONTH)){
			monthDiff--;
			ymdd[0] = monthDiff / 12;
	        ymdd[1] = monthDiff % 12;
	        ca1.add(Calendar.YEAR, ymdd[0]);
	        ymdd[2] = (int)((ca2.getTimeInMillis() - ca1.getTimeInMillis())/86400000);
	        ca1.add(Calendar.MONTH, ymdd[1]);
	        ymdd[3] = ca2.get(Calendar.DAY_OF_MONTH);
		} else if(ca11.get(Calendar.DAY_OF_MONTH)!=ca1.get(Calendar.DAY_OF_MONTH) && ca22.get(Calendar.DAY_OF_MONTH)==ca2.get(Calendar.DAY_OF_MONTH)){
			ymdd[0] = monthDiff / 12;
	        ymdd[1] = monthDiff % 12;
	        ca1.add(Calendar.YEAR, ymdd[0]);
	        ymdd[2] = (int)((ca2.getTimeInMillis() - ca1.getTimeInMillis())/86400000);
	        ca1.add(Calendar.MONTH, ymdd[1]);
	        ymdd[3] = ca22.get(Calendar.DAY_OF_MONTH)-ca1.get(Calendar.DAY_OF_MONTH);
		} else {
			ymdd[0] = monthDiff / 12;
	        ymdd[1] = monthDiff % 12;
	        ca1.add(Calendar.YEAR, ymdd[0]);
	        ymdd[2] = (int)((ca2.getTimeInMillis() - ca1.getTimeInMillis())/86400000);
	        ca1.add(Calendar.MONTH, ymdd[1]);
	        ymdd[3] = 0;
		}
		
		if("30".equals(accrueBasis)){
			ymdd[2] = ymdd[1]*30+ymdd[3];
		}
		return ymdd;
	}
	
	/**
	 * 获取后一天
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DATE, 1);
		return ca.getTime();
	}
	
	/**
	 * 获取前一天
	 * @param date
	 * @return
	 */
	public static Date getLastDate(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DATE, -1);
		return ca.getTime();
	}
	
	
	/**
	 * 获取传入Date当月最大的日期-INt
	 * @param date
	 * @return
	 */
	public static int getMaxDayOfMonth(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 获取传入Date当月最大的日期-Date
	 * @param date
	 * @return
	 */
	public static Date getMaxDateOfMonth(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, getMaxDayOfMonth(date));
		return ca.getTime();
	}
	
	
	
	
}
