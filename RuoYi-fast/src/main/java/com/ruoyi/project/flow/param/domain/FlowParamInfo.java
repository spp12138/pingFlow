package com.ruoyi.project.flow.param.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 【流程参数】对象 flow_param_info
 * 
 * @author SangYiPing
 * @date 2019-11-05
 */
public class FlowParamInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;
	private String flowId;
	private String paramKey;
	private String paramValue;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamValue() {
		return paramValue;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("flowId", getFlowId())
				.append("paramKey", getParamKey())
				.append("paramValue", getParamValue())
				.append("createBy", getCreateBy())
				.append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
