package com.ruoyi.project.al.cashFlow.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class CashFlowBaseData {

	
	String data_id;
	Date data_dt;
	String data_typ ;
	String org_cd ;
	String corp_cd;
	String subject_cd ;
	String acct_num ;
	String crncy_cd ;
	String cust_nm ;
	String cust_num;
	Double ending_bal ;
	Double bas_rt ;
	Double actl_rt;
	String rt_mk ;
	Date orig_dt;
	Date mtrty_dt;
	String rprc_freq;
	String accrue_basis;
	String accrue_freq ;
	String pmt_typ ;
	String pmt_freq ;
	String ovrdue_flg;
	Date load_dt;
	Date intDate;
	
	Date lastHkDt;
	Date nextHkDt;
	
	
	public Date getLastHkDt() {
		return lastHkDt;
	}
	public void setLastHkDt(Date lastHkDt) {
		this.lastHkDt = lastHkDt;
	}
	public Date getNextHkDt() {
		return nextHkDt;
	}
	public void setNextHkDt(Date nextHkDt) {
		this.nextHkDt = nextHkDt;
	}
	public String getRprc_freq() {
		return rprc_freq;
	}
	public void setRprc_freq(String rprc_freq) {
		this.rprc_freq = rprc_freq;
	}
	public Date getIntDate() {
		return intDate;
	}
	public void setIntDate(Date intDate) {
		this.intDate = intDate;
	}
	public String getData_id() {
		return data_id;
	}
	public void setData_id(String data_id) {
		this.data_id = data_id;
	}
	public Date getData_dt() {
		return data_dt;
	}
	public void setData_dt(Date data_dt) {
		this.data_dt = data_dt;
	}
	public String getData_typ() {
		return data_typ;
	}
	public void setData_typ(String data_typ) {
		this.data_typ = data_typ;
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
	public String getSubject_cd() {
		return subject_cd;
	}
	public void setSubject_cd(String subject_cd) {
		this.subject_cd = subject_cd;
	}
	public String getAcct_num() {
		return acct_num;
	}
	public void setAcct_num(String acct_num) {
		this.acct_num = acct_num;
	}
	public String getCrncy_cd() {
		return crncy_cd;
	}
	public void setCrncy_cd(String crncy_cd) {
		this.crncy_cd = crncy_cd;
	}
	public String getCust_nm() {
		return cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}
	public String getCust_num() {
		return cust_num;
	}
	public void setCust_num(String cust_num) {
		this.cust_num = cust_num;
	}
	public Double getEnding_bal() {
		return ending_bal;
	}
	public void setEnding_bal(Double ending_bal) {
		this.ending_bal = ending_bal;
	}
	public Double getBas_rt() {
		return bas_rt;
	}
	public void setBas_rt(Double bas_rt) {
		this.bas_rt = bas_rt;
	}
	public Double getActl_rt() {
		return actl_rt;
	}
	public void setActl_rt(Double actl_rt) {
		this.actl_rt = actl_rt;
	}
	public String getRt_mk() {
		return rt_mk;
	}
	public void setRt_mk(String rt_mk) {
		this.rt_mk = rt_mk;
	}
	public Date getOrig_dt() {
		return orig_dt;
	}
	public void setOrig_dt(Date orig_dt) {
		this.orig_dt = orig_dt;
	}
	public Date getMtrty_dt() {
		return mtrty_dt;
	}
	public void setMtrty_dt(Date mtrty_dt) {
		this.mtrty_dt = mtrty_dt;
	}
	public String getAccrue_basis() {
		return accrue_basis;
	}
	public void setAccrue_basis(String accrue_basis) {
		this.accrue_basis = accrue_basis;
	}
	public String getAccrue_freq() {
		return accrue_freq;
	}
	public void setAccrue_freq(String accrue_freq) {
		this.accrue_freq = accrue_freq;
	}
	public String getPmt_typ() {
		return pmt_typ;
	}
	public void setPmt_typ(String pmt_typ) {
		this.pmt_typ = pmt_typ;
	}
	public String getPmt_freq() {
		return pmt_freq;
	}
	public void setPmt_freq(String pmt_freq) {
		this.pmt_freq = pmt_freq;
	}
	public String getOvrdue_flg() {
		return ovrdue_flg;
	}
	public void setOvrdue_flg(String ovrdue_flg) {
		this.ovrdue_flg = ovrdue_flg;
	}
	public Date getLoad_dt() {
		return load_dt;
	}
	public void setLoad_dt(Date load_dt) {
		this.load_dt = load_dt;
	}
	
	
	@SuppressWarnings("unchecked")
	public CashFlowBaseData setReadyCashFlowBean(Object m ) {
		return setReadyCashFlowBean((HashMap<String,Object>)m);
	}
	
	public CashFlowBaseData setReadyCashFlowBean(HashMap<String,Object> m ) {
		data_id = String.valueOf(m.get("DATA_ID"));
		data_dt = (Date) m.get("DATA_DT");
		data_typ = (String) m.get("DATA_TYP");
		org_cd = (String) m.get("ORG_CD");
		corp_cd = (String) m.get("CORP_CD");
		subject_cd = (String) m.get("SUBJECT_CD");
		acct_num = (String) m.get("ACCT_NUM");
		crncy_cd = (String) m.get("CRNCY_CD");
		cust_nm = (String) m.get("CUST_NM");
		cust_num = (String) m.get("CUST_NUM");
		ending_bal = ((BigDecimal)m.get("ENDING_BAL")).doubleValue();
		bas_rt = ((BigDecimal)m.get("BAS_RT")).doubleValue();
		actl_rt = ((BigDecimal)m.get("ACTL_RT")).doubleValue();
		rt_mk = (String) m.get("RT_MK");
		orig_dt = (Date) m.get("ORIG_DT");
		mtrty_dt = (Date) m.get("MTRTY_DT");
		accrue_basis = (String) m.get("ACCRUE_BASIS");
		accrue_freq = (String) m.get("ACCRUE_FREQ");
		pmt_typ = (String) m.get("PMT_TYP");
		pmt_freq = (String) m.get("PMT_FREQ");
		ovrdue_flg = (String) m.get("OVRDUE_FLG");
		load_dt = (Date) m.get("LOAD_DT");
		return this;
	}
	@Override
	public String toString() {
		return "ReadyCashFlow {\"data_id\":\"" + data_id + "\", \"data_dt\":\"" + data_dt
				+ "\", \"data_typ\":\"" + data_typ + "\", \"org_cd\":\"" + org_cd
				+ "\", \"corp_cd\":\"" + corp_cd + "\", \"subject_cd\":\"" + subject_cd
				+ "\", \"acct_num\":\"" + acct_num + "\", \"crncy_cd\":\"" + crncy_cd
				+ "\", \"cust_nm\":\"" + cust_nm + "\", \"cust_num\":\"" + cust_num
				+ "\", \"ending_bal\":\"" + ending_bal + "\", \"bas_rt\":\"" + bas_rt
				+ "\", \"actl_rt\":\"" + actl_rt + "\", \"rt_mk\":\"" + rt_mk + "\", \"orig_dt\":\""
				+ orig_dt + "\", \"mtrty_dt\":\"" + mtrty_dt + "\", \"accrue_basis\":\""
				+ accrue_basis + "\", \"accrue_freq\":\"" + accrue_freq + "\", \"pmt_typ\":\""
				+ pmt_typ + "\", \"pmt_freq\":\"" + pmt_freq + "\", \"ovrdue_flg\":\""
				+ ovrdue_flg + "\", \"load_dt\":\"" + load_dt + "\"}";
	}
	public String checkData() {
		return null;
	}
	
	
}
