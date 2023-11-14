package com.ruoyi.project.flow.chart.line.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 【流程任务线】对象 flow_task_line
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
public class FlowTaskLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    
    
    private String flowid;
    private String id;
    private String name;
    private String type;
    private String from;
    private String to;

    public void setFlowid(String flowid) 
    {
        this.flowid = flowid;
    }

    public String getFlowid() 
    {
        return flowid;
    }
    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setFrom(String from) 
    {
        this.from = from;
    }

    public String getFrom() 
    {
        return from;
    }
    public void setTo(String to) 
    {
        this.to = to;
    }

    public String getTo() 
    {
        return to;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("flowid", getFlowid())
            .append("id", getId())
            .append("name", getName())
            .append("type", getType())
            .append("from", getFrom())
            .append("to", getTo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
