package com.ruoyi.project.flow.subTask.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 【任务详情】对象 flow_task_sub_info
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
public class FlowTaskSubInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String flowId;
	private String subTaskId;
	private String subTaskName;
	private String subTaskType;
	private String subTaskModel;
	private String subJdbc;
	private String subSsh;
	private String subTaskLeftR;
	private String subTaskTopR;
	private String subTaskWidth;
	private String subTaskHeight;
	private String subTaskFrom;
	private String subTaskTo;
	private String exeStr;
	private String exeStr0;
	private String exeStr1;
	private String exeStr2;
	private String exeStr3;
	private String exeStr4;
	private String exeStr5;
	private String exeStr6;
	private String exeStr7;
	private String exeStr8;
	private String exeStr9;
	private String exeStr10;
	private String exeStr11;
	private String exeStr12;
	private String exeStr13;
	private String exeStr14;
	private String exeStr15;
	private String exeStr16;
	private String exeStr17;
	private String exeStr18;
	private String exeStr19;

	public String getExeStr() {
		return StringUtils.isNotBlank(exeStr) ? exeStr : exeStr0 + exeStr1
				+ exeStr2 + exeStr3 + exeStr4 + exeStr5 + exeStr6 + exeStr7
				+ exeStr8 + exeStr9 + exeStr10 + exeStr11 + exeStr12 + exeStr13
				+ exeStr14 + exeStr15 + exeStr16 + exeStr17 + exeStr18
				+ exeStr19;
	}

	public String getSubSsh() {
		return subSsh;
	}

	public void setSubSsh(String subSsh) {
		this.subSsh = subSsh;
	}

	public String getSubJdbc() {
		return subJdbc;
	}

	public void setSubJdbc(String subJdbc) {
		this.subJdbc = subJdbc;
	}

	public void setExeStr(String exeStr) {
		this.exeStr = exeStr;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setSubTaskId(String subTaskId) {
		this.subTaskId = subTaskId;
	}

	public String getSubTaskId() {
		return subTaskId;
	}

	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
	}

	public String getSubTaskName() {
		return subTaskName;
	}

	public void setSubTaskType(String subTaskType) {
		this.subTaskType = subTaskType;
	}

	public String getSubTaskType() {
		return subTaskType;
	}

	public void setSubTaskModel(String subTaskModel) {
		this.subTaskModel = subTaskModel;
	}

	public String getSubTaskModel() {
		return subTaskModel;
	}

	public void setSubTaskLeftR(String subTaskLeftR) {
		this.subTaskLeftR = subTaskLeftR;
	}

	public String getSubTaskLeftR() {
		return subTaskLeftR;
	}

	public void setSubTaskTopR(String subTaskTopR) {
		this.subTaskTopR = subTaskTopR;
	}

	public String getSubTaskTopR() {
		return subTaskTopR;
	}

	public void setSubTaskWidth(String subTaskWidth) {
		this.subTaskWidth = subTaskWidth;
	}

	public String getSubTaskWidth() {
		return subTaskWidth;
	}

	public void setSubTaskHeight(String subTaskHeight) {
		this.subTaskHeight = subTaskHeight;
	}

	public String getSubTaskHeight() {
		return subTaskHeight;
	}

	public void setSubTaskFrom(String subTaskFrom) {
		this.subTaskFrom = subTaskFrom;
	}

	public String getSubTaskFrom() {
		return subTaskFrom;
	}

	public void setSubTaskTo(String subTaskTo) {
		this.subTaskTo = subTaskTo;
	}

	public String getSubTaskTo() {
		return subTaskTo;
	}

	public void setExeStr0(String exeStr0) {
		this.exeStr0 = exeStr0;
	}

	public String getExeStr0() {
		return exeStr0;
	}

	public void setExeStr1(String exeStr1) {
		this.exeStr1 = exeStr1;
	}

	public String getExeStr1() {
		return exeStr1;
	}

	public void setExeStr2(String exeStr2) {
		this.exeStr2 = exeStr2;
	}

	public String getExeStr2() {
		return exeStr2;
	}

	public void setExeStr3(String exeStr3) {
		this.exeStr3 = exeStr3;
	}

	public String getExeStr3() {
		return exeStr3;
	}

	public void setExeStr4(String exeStr4) {
		this.exeStr4 = exeStr4;
	}

	public String getExeStr4() {
		return exeStr4;
	}

	public void setExeStr5(String exeStr5) {
		this.exeStr5 = exeStr5;
	}

	public String getExeStr5() {
		return exeStr5;
	}

	public void setExeStr6(String exeStr6) {
		this.exeStr6 = exeStr6;
	}

	public String getExeStr6() {
		return exeStr6;
	}

	public void setExeStr7(String exeStr7) {
		this.exeStr7 = exeStr7;
	}

	public String getExeStr7() {
		return exeStr7;
	}

	public void setExeStr8(String exeStr8) {
		this.exeStr8 = exeStr8;
	}

	public String getExeStr8() {
		return exeStr8;
	}

	public void setExeStr9(String exeStr9) {
		this.exeStr9 = exeStr9;
	}

	public String getExeStr9() {
		return exeStr9;
	}

	public void setExeStr10(String exeStr10) {
		this.exeStr10 = exeStr10;
	}

	public String getExeStr10() {
		return exeStr10;
	}

	public void setExeStr11(String exeStr11) {
		this.exeStr11 = exeStr11;
	}

	public String getExeStr11() {
		return exeStr11;
	}

	public void setExeStr12(String exeStr12) {
		this.exeStr12 = exeStr12;
	}

	public String getExeStr12() {
		return exeStr12;
	}

	public void setExeStr13(String exeStr13) {
		this.exeStr13 = exeStr13;
	}

	public String getExeStr13() {
		return exeStr13;
	}

	public void setExeStr14(String exeStr14) {
		this.exeStr14 = exeStr14;
	}

	public String getExeStr14() {
		return exeStr14;
	}

	public void setExeStr15(String exeStr15) {
		this.exeStr15 = exeStr15;
	}

	public String getExeStr15() {
		return exeStr15;
	}

	public void setExeStr16(String exeStr16) {
		this.exeStr16 = exeStr16;
	}

	public String getExeStr16() {
		return exeStr16;
	}

	public void setExeStr17(String exeStr17) {
		this.exeStr17 = exeStr17;
	}

	public String getExeStr17() {
		return exeStr17;
	}

	public void setExeStr18(String exeStr18) {
		this.exeStr18 = exeStr18;
	}

	public String getExeStr18() {
		return exeStr18;
	}

	public void setExeStr19(String exeStr19) {
		this.exeStr19 = exeStr19;
	}

	public String getExeStr19() {
		return exeStr19;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("flowId", getFlowId())
				.append("subTaskId", getSubTaskId())
				.append("subTaskName", getSubTaskName())
				.append("subTaskType", getSubTaskType())
				.append("subTaskModel", getSubTaskModel())
				.append("subJdbc", getSubJdbc())
				.append("subTaskLeftR", getSubTaskLeftR())
				.append("subTaskTopR", getSubTaskTopR())
				.append("subTaskWidth", getSubTaskWidth())
				.append("subTaskHeight", getSubTaskHeight())
				.append("subTaskFrom", getSubTaskFrom())
				.append("subTaskTo", getSubTaskTo())
				.append("exeStr0", getExeStr0())
				.append("exeStr1", getExeStr1())
				.append("exeStr2", getExeStr2())
				.append("exeStr3", getExeStr3())
				.append("exeStr4", getExeStr4())
				.append("exeStr5", getExeStr5())
				.append("exeStr6", getExeStr6())
				.append("exeStr7", getExeStr7())
				.append("exeStr8", getExeStr8())
				.append("exeStr9", getExeStr9())
				.append("exeStr10", getExeStr10())
				.append("exeStr11", getExeStr11())
				.append("exeStr12", getExeStr12())
				.append("exeStr13", getExeStr13())
				.append("exeStr14", getExeStr14())
				.append("exeStr15", getExeStr15())
				.append("exeStr16", getExeStr16())
				.append("exeStr17", getExeStr17())
				.append("exeStr18", getExeStr18())
				.append("exeStr19", getExeStr19())
				.append("createBy", getCreateBy())
				.append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
