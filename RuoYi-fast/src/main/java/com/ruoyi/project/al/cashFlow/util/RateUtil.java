package com.ruoyi.project.al.cashFlow.util;

import java.util.Calendar;
import java.util.Date;

public class RateUtil {
	//分子
	private String fz;
    //分母
	private String fm;

    //年利率
	private Double yRt;

    //月利率
	private Double mRt;

    //日利率
	private Double dRt;
	//利率计算天数
    private Integer number;
    //是否使用月利率
    private boolean usemRt = false;
    //计结息规则
    private String accrualBasis;

    

	public String getFz() {
		return fz;
	}

	public void setFz(String fz) {
		this.fz = fz;
	}

	public String getFm() {
		return fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

	public Double getyRt() {
		return yRt;
	}

	public void setyRt(Double yRt) {
		this.yRt = yRt;
	}

	public Double getmRt() {
		return mRt;
	}

	public void setmRt(Double mRt) {
		this.mRt = mRt;
	}

	public Double getdRt() {
		return dRt;
	}

	public void setdRt(Double dRt) {
		this.dRt = dRt;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public boolean isUsemRt() {
		return usemRt;
	}

	public void setUsemRt(boolean usemRt) {
		this.usemRt = usemRt;
	}

	public String getAccrualBasis() {
		return accrualBasis;
	}

	public void setAccrualBasis(String accrualBasis) {
		this.accrualBasis = accrualBasis;
	}

	/**
	 * 初始化利率计算实例
	 * @param accrualBasis 计结息规则
	 * @param actualRate 年利率
	 */
	public RateUtil(String accrualBasis, Double actualRate) {
        if (actualRate == null) {
            actualRate = 0.0;
        }
        if (accrualBasis == null) {
            dRt = 0.0;
        }
        this.accrualBasis = accrualBasis;
        yRt = actualRate;
    }
    
    public void parse(Date occDate){
    	if (accrualBasis==null) {
            return;
        }
        String[] bis = accrualBasis.split("/");
        if (bis != null && bis.length > 0) {
            fz = bis[0];
            fm = bis[1];
        }
        if (fm!=null) {
            if (fm.equals("ACTUAL") ) {
                number = getMaxDayOfYear(occDate);
            } else {
                number = Integer.parseInt(fm);
            }
            if (fz.equals("M")) {
                mRt = yRt / number;
                dRt = yRt / 365;
                usemRt = true;
            } else if (fz.equals("ACTUAL")) {
                dRt = yRt / number;
                usemRt = false;
            } else {
                dRt = yRt / 360;
            }
        } else {
            mRt = 0d;
            dRt = 0d;
        }
    }
    
    public Integer getMaxDayOfYear(Date baseDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }
    
    public String getfz(){
    	return fz;
    }
}
