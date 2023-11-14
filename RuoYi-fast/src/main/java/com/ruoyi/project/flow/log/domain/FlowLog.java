package com.ruoyi.project.flow.log.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 调度日志对象 flow_log
 * 
 * @author SangYiPing
 * @date 2020-01-16
 */
public class FlowLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

   
    private String id;

   
    @Excel(name = "流程实例ID")
    private String flowSlId;
    
    @Excel(name = "流程名称")
    private String flowName;

   
    @Excel(name = "开始时间")
    private Date startTime;
    
    @Excel(name = "进度")
    private String progress;

   
    @Excel(name = "结束时间")
    private Date endTime;

   
    @Excel(name = "状态 0 新建 , 1 开始, 2 完成, 3 暂停  , 4 异常")
    private String stauts;

   
    @Excel(name = "流程参数")
    private String param;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setFlowSlId(String flowSlId) 
    {
        this.flowSlId = flowSlId;
    }

    public String getFlowSlId() 
    {
        return flowSlId;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setStauts(String stauts) 
    {
        this.stauts = stauts;
    }

    public String getStauts() 
    {
        return stauts;
    }
    public void setParam(String param) 
    {
        this.param = param;
    }

    public String getParam() 
    {
        return param;
    }

    public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("flowSlId", getFlowSlId())
            .append("flowName", getFlowName())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("stauts", getStauts())
            .append("param", getParam())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
