package com.ruoyi.project.flow.info.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 调度管理对象 flow_info
 * 
 * @author SangYiPing
 * @date 2019-10-26
 */
public class FlowInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "流程ID")
    private String flowId;

    /** $column.columnComment */
    @Excel(name = "流程名称")
    private String flowName;

    public void setFlowId(String flowId) 
    {
        this.flowId = flowId;
    }

    public String getFlowId() 
    {
        return flowId;
    }
    public void setFlowName(String flowName) 
    {
        this.flowName = flowName;
    }

    public String getFlowName() 
    {
        return flowName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("flowId", getFlowId())
            .append("flowName", getFlowName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
