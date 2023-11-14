package com.ruoyi.project.flow.chart.area.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 【流程任务Area】对象 flow_task_area
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
public class FlowTaskArea extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    
    
    private String flowid;
    private String id;
    private String name;
    private String left;
    private String top;
    private String color;
    private String width;
    private String height;

    
    
    private String alt;

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
    public void setLeft(String left) 
    {
        this.left = left;
    }

    public String getLeft() 
    {
        return left;
    }
    public void setTop(String top) 
    {
        this.top = top;
    }

    public String getTop() 
    {
        return top;
    }
    public void setColor(String color) 
    {
        this.color = color;
    }

    public String getColor() 
    {
        return color;
    }
    public void setWidth(String width) 
    {
        this.width = width;
    }

    public String getWidth() 
    {
        return width;
    }
    public void setHeight(String height) 
    {
        this.height = height;
    }

    public String getHeight() 
    {
        return height;
    }
    public void setAlt(String alt) 
    {
        this.alt = alt;
    }

    public String getAlt() 
    {
        return alt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("flowid", getFlowid())
            .append("id", getId())
            .append("name", getName())
            .append("left", getLeft())
            .append("top", getTop())
            .append("color", getColor())
            .append("width", getWidth())
            .append("height", getHeight())
            .append("alt", getAlt())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
