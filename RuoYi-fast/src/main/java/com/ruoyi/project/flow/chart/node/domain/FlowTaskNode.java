package com.ruoyi.project.flow.chart.node.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 【流程任务节点】对象 flow_task_node
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
public class FlowTaskNode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

	private String flowid;
	private String id;
	private String name;
	private Integer left;
	private Integer top;
	private String type;
	private Integer width;
	private Integer height;
	private String alt;

	
	
    public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

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
            .append("type", getType())
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
