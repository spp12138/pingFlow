package com.ruoyi.project.flow.chart.area.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.flow.chart.area.domain.FlowTaskArea;
import com.ruoyi.project.flow.chart.area.service.IFlowTaskAreaService;

/**
 * 【流程任务Area】Controller
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
@Controller
@RequestMapping("/flow/chart/area")
public class FlowTaskAreaController extends BaseController
{
    private String prefix = "chart/area";

    @Autowired
    private IFlowTaskAreaService flowTaskAreaService;

    @RequiresPermissions("chart:area:view")
    @GetMapping()
    public String area()
    {
        return prefix + "/area";
    }

    /**
     * 查询【流程任务Area】列表
     */
    @RequiresPermissions("chart:area:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowTaskArea flowTaskArea)
    {
        startPage();
        List<FlowTaskArea> list = flowTaskAreaService.selectFlowTaskAreaList(flowTaskArea);
        return getDataTable(list);
    }

    /**
     * 导出【流程任务Area】列表
     */
    @RequiresPermissions("chart:area:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowTaskArea flowTaskArea)
    {
        List<FlowTaskArea> list = flowTaskAreaService.selectFlowTaskAreaList(flowTaskArea);
        ExcelUtil<FlowTaskArea> util = new ExcelUtil<FlowTaskArea>(FlowTaskArea.class);
        return util.exportExcel(list, "area");
    }

    /**
     * 新增【流程任务Area】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【流程任务Area】
     */
    @RequiresPermissions("chart:area:add")
    @Log(title = "【流程任务Area】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FlowTaskArea flowTaskArea)
    {
        return toAjax(flowTaskAreaService.insertFlowTaskArea(flowTaskArea));
    }

    /**
     * 修改【流程任务Area】
     */
    @GetMapping("/edit/{flowid}")
    public String edit(@PathVariable("flowid") String flowid, ModelMap mmap)
    {
        FlowTaskArea flowTaskArea = flowTaskAreaService.selectFlowTaskAreaById(flowid);
        mmap.put("flowTaskArea", flowTaskArea);
        return prefix + "/edit";
    }

    /**
     * 修改保存【流程任务Area】
     */
    @RequiresPermissions("chart:area:edit")
    @Log(title = "【流程任务Area】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowTaskArea flowTaskArea)
    {
        return toAjax(flowTaskAreaService.updateFlowTaskArea(flowTaskArea));
    }

    /**
     * 删除【流程任务Area】
     */
    @RequiresPermissions("chart:area:remove")
    @Log(title = "【流程任务Area】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowTaskAreaService.deleteFlowTaskAreaByIds(ids));
    }
}
