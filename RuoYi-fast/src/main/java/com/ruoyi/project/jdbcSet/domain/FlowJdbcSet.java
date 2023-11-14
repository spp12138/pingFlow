package com.ruoyi.project.jdbcSet.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 数据库连接管理对象 flow_jdbc_set
 * 
 * @author SangYiPing
 * @date 2019-11-04
 */
public class FlowJdbcSet extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;
	private String jdbcName;
	private String jdbcDriver;
	private String jdbcUrl;
	private String jdbcUsername;
	private String jdbcPassword;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setJdbcName(String jdbcName) {
		this.jdbcName = jdbcName;
	}

	public String getJdbcName() {
		return jdbcName;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUsername(String jdbcUsername) {
		this.jdbcUsername = jdbcUsername;
	}

	public String getJdbcUsername() {
		return jdbcUsername;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId()).append("jdbcName", getJdbcName())
				.append("jdbcDriver", getJdbcDriver())
				.append("jdbcUrl", getJdbcUrl())
				.append("jdbcUsername", getJdbcUsername())
				.append("jdbcPassword", getJdbcPassword())
				.append("createBy", getCreateBy())
				.append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
