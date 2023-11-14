package com.ruoyi.project.al.cashFlow.domain;

import java.util.Date;


public class CashFlowResData{
	
	/**
	AL_BASE_DATA表的ID VARCHAR2(64)
	 */
	private String data_id;
	/**
	数据日期 DATE
	 */
	private String data_dt;
	/**
	         拆分模型类型
	   FLOATNG_RT 浮动利率
		FXD_RT 固定利率
		NO_MTRTY 无固定到期日
		NS 非敏感
	*/
	private String data_typ;
	/**
	现金流发生日期 VARCHAR2(20)
	 */
	private Date occ_dt;
	/**
	机构码 VARCHAR2(20)
	 */
	private String org_cd;
	/**
	法人代码 VARCHAR2(20)
	 */
	private String corp_cd;
	/**
	科目号 VARCHAR2(20)
	 */
	private String subjt_num;
	/**
	币种 VARCHAR2(50)
	 */
	private String crncy_cd;
	/**
	期末余额 VARCHAR2(10)
	 */
	private Double ending_bal;
	/**
	本金流 VARCHAR2(256)
	 */
	private Double princ;
	/**
	利息流 VARCHAR2(50)
	 */
	private Double interest;
	/**
	流动性缺口日 现金流发生日期-拆分现金流的日期 NUMBER(20,4)
	 */
	private Integer liq_gap_days;
	/**
	重定价缺口日 下一重定价日-拆分现金流的日期 NUMBER(12,6)
	 */
	private Integer rprc_gap_days;
	/**
	下一重定价日 NUMBER(12,6)
	 */
	private Date nxt_rprc_dt;
	/**
	上一重定价日 CHAR(1)
	 */
	private Date lst_rprc_dt;
	/**
	利率 DATE
	 */
	private Double actl_rt;
	/**
	数据加载时间 DATE
	 */
	private String load_dt;

	private Double beginningBal;

	private Date start_int_dt;
	private Date end_int_dt;
	private Date start_int_accrue_dt;
	private Date end_int_accrue_dt;
	/**
	* 计算利息的本金
	*/
	private Double endingBalForInt;

	/**
	 * 利息(权责发生制)
	 */
	private Double int_accrue;
	
	

	public Date getStart_int_dt() {
		return start_int_dt;
	}

	public void setStart_int_dt(Date start_int_dt) {
		this.start_int_dt = start_int_dt;
	}

	public Date getEnd_int_dt() {
		return end_int_dt;
	}

	public void setEnd_int_dt(Date end_int_dt) {
		this.end_int_dt = end_int_dt;
	}

	public String getData_id() {
		return data_id;
	}

	public void setData_id(String data_id) {
		this.data_id = data_id;
	}

	public String getData_dt() {
		return data_dt;
	}

	public void setData_dt(String data_dt) {
		this.data_dt = data_dt;
	}

	public String getData_typ() {
		return data_typ;
	}

	public void setData_typ(String data_typ) {
		this.data_typ = data_typ;
	}

	public Date getOcc_dt() {
		return occ_dt;
	}

	public void setOcc_dt(Date occ_dt) {
		this.occ_dt = occ_dt;
	}

	public String getOrg_cd() {
		return org_cd;
	}

	public void setOrg_cd(String org_cd) {
		this.org_cd = org_cd;
	}

	public String getCorp_cd() {
		return corp_cd;
	}

	public void setCorp_cd(String corp_cd) {
		this.corp_cd = corp_cd;
	}

	public String getSubjt_num() {
		return subjt_num;
	}

	public void setSubjt_num(String subjt_num) {
		this.subjt_num = subjt_num;
	}

	public String getCrncy_cd() {
		return crncy_cd;
	}

	public void setCrncy_cd(String crncy_cd) {
		this.crncy_cd = crncy_cd;
	}

	public Double getEnding_bal() {
		return ending_bal;
	}

	public void setEnding_bal(Double ending_bal) {
		this.ending_bal = ending_bal;
	}

	public Double getPrinc() {
		return princ;
	}

	public void setPrinc(Double princ) {
		this.princ = princ;
	}


	public Integer getLiq_gap_days() {
		return liq_gap_days;
	}

	public void setLiq_gap_days(Integer liq_gap_days) {
		this.liq_gap_days = liq_gap_days;
	}

	public Integer getRprc_gap_days() {
		return rprc_gap_days;
	}

	public void setRprc_gap_days(Integer rprc_gap_days) {
		this.rprc_gap_days = rprc_gap_days;
	}

	public Date getNxt_rprc_dt() {
		return nxt_rprc_dt;
	}

	public void setNxt_rprc_dt(Date nxt_rprc_dt) {
		this.nxt_rprc_dt = nxt_rprc_dt;
	}

	public Date getLst_rprc_dt() {
		return lst_rprc_dt;
	}

	public void setLst_rprc_dt(Date lst_rprc_dt) {
		this.lst_rprc_dt = lst_rprc_dt;
	}

	public Double getActl_rt() {
		return actl_rt;
	}

	public void setActl_rt(Double actl_rt) {
		this.actl_rt = actl_rt;
	}

	public String getLoad_dt() {
		return load_dt;
	}

	public void setLoad_dt(String load_dt) {
		this.load_dt = load_dt;
	}

	public Double getBeginningBal() {
		return beginningBal;
	}

	public void setBeginningBal(Double beginningBal) {
		this.beginningBal = beginningBal;
	}

	public Date getStart_int_accrue_dt() {
		return start_int_accrue_dt;
	}

	public void setStart_int_accrue_dt(Date start_int_accrue_dt) {
		this.start_int_accrue_dt = start_int_accrue_dt;
	}

	public Date getEnd_int_accrue_dt() {
		return end_int_accrue_dt;
	}

	public void setEnd_int_accrue_dt(Date end_int_accrue_dt) {
		this.end_int_accrue_dt = end_int_accrue_dt;
	}

	public Double getEndingBalForInt() {
		return endingBalForInt;
	}

	public void setEndingBalForInt(Double endingBalForInt) {
		this.endingBalForInt = endingBalForInt;
	}

	public Double getInt_accrue() {
		return int_accrue;
	}

	public void setInt_accrue(Double int_accrue) {
		this.int_accrue = int_accrue;
	}


	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public CashFlowResData clone(){
		CashFlowResData ac = new CashFlowResData();
		ac.setData_id(this.data_id);
		ac.setData_dt(this.data_dt);
		ac.setData_typ(this.data_typ);
		ac.setOcc_dt(this.occ_dt);
		ac.setOrg_cd(this.org_cd);
		ac.setCorp_cd(this.corp_cd);
		ac.setSubjt_num(this.subjt_num);
		ac.setCrncy_cd(this.crncy_cd);
		ac.setEnding_bal(this.ending_bal);
		ac.setPrinc(this.princ);
		ac.setInterest(this.interest);
		ac.setLiq_gap_days(this.liq_gap_days);
		ac.setRprc_gap_days(this.rprc_gap_days);
		ac.setNxt_rprc_dt(this.nxt_rprc_dt);
		ac.setLst_rprc_dt(this.lst_rprc_dt);
		ac.setActl_rt(this.actl_rt);
		ac.setLoad_dt(this.load_dt);
		ac.setBeginningBal(this.beginningBal);
		ac.setStart_int_accrue_dt(this.start_int_accrue_dt);
		ac.setEnd_int_accrue_dt(this.end_int_accrue_dt);
		ac.setEndingBalForInt(this.endingBalForInt);
		ac.setInt_accrue(this.int_accrue);
		return ac;
	}

 
}
