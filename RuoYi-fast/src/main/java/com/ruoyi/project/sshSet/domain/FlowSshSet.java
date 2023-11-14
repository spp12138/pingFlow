package com.ruoyi.project.sshSet.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 【SSH连接管理】对象 flow_ssh_set
 * 
 * @author SangYiPing
 * @date 2019-11-07
 */
public class FlowSshSet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String id;
    private String sshName;
    private String sshHostname;
    private String sshUsername;
    private String sshPassword;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setSshName(String sshName) 
    {
        this.sshName = sshName;
    }

    public String getSshName() 
    {
        return sshName;
    }
    public void setSshHostname(String sshHostname) 
    {
        this.sshHostname = sshHostname;
    }

    public String getSshHostname() 
    {
        return sshHostname;
    }
    public void setSshUsername(String sshUsername) 
    {
        this.sshUsername = sshUsername;
    }

    public String getSshUsername() 
    {
        return sshUsername;
    }
    public void setSshPassword(String sshPassword) 
    {
        this.sshPassword = sshPassword;
    }

    public String getSshPassword() 
    {
        return sshPassword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sshName", getSshName())
            .append("sshHostname", getSshHostname())
            .append("sshUsername", getSshUsername())
            .append("sshPassword", getSshPassword())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
