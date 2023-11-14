package com.ruoyi.project.al.cashFlow.domain;

import java.util.Calendar;
import java.util.Date;

public class RateEval {
	//分子
	private String numerator;
    //分母
	private String denominator;
    //年利率
	private Double yearRate;
    //月利率
	private Double monthRate;
    //日利率
	private Double dayRate;

    private Integer dm;

    private boolean useMonthRate = false;

    //计结息规则
    private String accrualBasis;
    
    public Double getYearRate() {
		return yearRate;
	}

	public Double getMonthRate() {
		return monthRate;
	}

	public Double getDayRate() {
		return dayRate;
	}

	public boolean isUseMonthRate() {
		return useMonthRate;
	}

	public RateEval(String accrualBasis, Double actualRate) {
        if (actualRate == null) {
            actualRate = 0.0;
        }
        if (accrualBasis == null) {
            dayRate = 0.0;
        }
        this.accrualBasis = accrualBasis;
        yearRate = actualRate;
    }
    
    public void parse(Date occDate){
    	if (accrualBasis==null) {
            return;
        }
        int bi = accrualBasis.indexOf('/');
        if (bi != -1) {
            numerator = accrualBasis.substring(0, bi);
            denominator = accrualBasis.substring(bi + 1);
        }
        if (denominator!=null) {
            if (denominator.equals("ACTUAL") ) {
                dm = getMaxDayOfYear(occDate);
            } else {
                dm = Integer.parseInt(denominator);
            }
            if (numerator.equals("M")) {
                monthRate = yearRate / dm;
                dayRate = yearRate / 365;
                useMonthRate = true;
            } else if (numerator.equals("ACTUAL")) {
                dayRate = yearRate / dm;
                useMonthRate = false;
            } else {
                dayRate = yearRate / 360;
            }
        } else {
            monthRate = 0d;
            dayRate = 0d;
        }
    }
    
    public Integer getMaxDayOfYear(Date baseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }
    
    public String getNumerator(){
    	return numerator;
    }
}
