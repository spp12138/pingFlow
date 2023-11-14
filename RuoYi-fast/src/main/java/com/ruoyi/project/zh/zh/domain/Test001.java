package com.ruoyi.project.zh.zh.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * zh对象 test_001
 * 
 * @author SangYiPing
 * @date 2022-07-01
 */
public class Test001 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;

    /** 日期 */
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    /** 顾问 */
    @Excel(name = "顾问")
    private String gw;

    /** 老师 */
    @Excel(name = "老师")
    private String ls;

    /** 学员 */
    @Excel(name = "学员")
    private String xy;

    /** 是否转化 */
    @Excel(name = "是否转化")
    private String sfzh;
    /** 操作员 */
    @Excel(name = "操作员")
    private String czy;
    
    private Date date_start;
    
    private Date date_end;

    private String zhl;
    
    
    public String getCzy() {
		return czy;
	}

	public void setCzy(String czy) {
		this.czy = czy;
	}

	public String getZhl() {
		return zhl;
	}

	public void setZhl(String zhl) {
		this.zhl = zhl;
	}

	public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setDate(Date date) 
    {
        this.date = date;
    }

    public Date getDate() 
    {
        return date;
    }
    public void setGw(String gw) 
    {
        this.gw = gw;
    }

    public String getGw() 
    {
        return gw;
    }
    public void setLs(String ls) 
    {
        this.ls = ls;
    }

    public String getLs() 
    {
        return ls;
    }
    public void setXy(String xy) 
    {
        this.xy = xy;
    }

    public String getXy() 
    {
        return xy;
    }
    public void setSfzh(String sfzh) 
    {
        this.sfzh = sfzh;
    }

    public String getSfzh() 
    {
        return sfzh;
    }

    public Date getDate_start() {
		return date_start;
	}

	public void setDate_start(Date date_start) {
		this.date_start = date_start;
	}

	public Date getDate_end() {
		return date_end;
	}

	public void setDate_end(Date date_end) {
		this.date_end = date_end;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("date", getDate())
            .append("gw", getGw())
            .append("ls", getLs())
            .append("xy", getXy())
            .append("sfzh", getSfzh())
            .toString();
    }
}
