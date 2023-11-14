package com.ruoyi.project.flow.chart.line.controller;

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
import com.ruoyi.project.flow.chart.line.domain.FlowTaskLine;
import com.ruoyi.project.flow.chart.line.service.IFlowTaskLineService;

/**
 * 【流程任务线】Controller
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
@Controller
@RequestMapping("/flow/chart/line")
public class FlowTaskLineController extends BaseController
{
    private String prefix = "chart/line";

    @Autowired
    private IFlowTaskLineService flowTaskLineService;

    @RequiresPermissions("chart:line:view")
    @GetMapping()
    public String line()
    {
        return prefix + "/line";
    }

    /**
     * 查询【流程任务线】列表
     */
    @RequiresPermissions("chart:line:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowTaskLine flowTaskLine)
    {
        startPage();
        List<FlowTaskLine> list = flowTaskLineService.selectFlowTaskLineList(flowTaskLine);
        return getDataTable(list);
    }

    /**
     * 导出【流程任务线】列表
     */
    @RequiresPermissions("chart:line:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowTaskLine flowTaskLine)
    {
        List<FlowTaskLine> list = flowTaskLineService.selectFlowTaskLineList(flowTaskLine);
        ExcelUtil<FlowTaskLine> util = new ExcelUtil<FlowTaskLine>(FlowTaskLine.class);
        return util.exportExcel(list, "line");
    }

    /**
     * 新增【流程任务线】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【流程任务线】
     */
    @RequiresPermissions("chart:line:add")
    @Log(title = "【流程任务线】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FlowTaskLine flowTaskLine)
    {
        return toAjax(flowTaskLineService.insertFlowTaskLine(flowTaskLine));
    }

    /**
     * 修改【流程任务线】
     */
    @GetMapping("/edit/{flowid}")
    public String edit(@PathVariable("flowid") String flowid, ModelMap mmap)
    {
        FlowTaskLine flowTaskLine = flowTaskLineService.selectFlowTaskLineById(flowid);
        mmap.put("flowTaskLine", flowTaskLine);
        return prefix + "/edit";
    }

    /**
     * 修改保存【流程任务线】
     */
    @RequiresPermissions("chart:line:edit")
    @Log(title = "【流程任务线】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowTaskLine flowTaskLine)
    {
        return toAjax(flowTaskLineService.updateFlowTaskLine(flowTaskLine));
    }

    /**
     * 删除【流程任务线】
     */
    @RequiresPermissions("chart:line:remove")
    @Log(title = "【流程任务线】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowTaskLineService.deleteFlowTaskLineByIds(ids));
    }
}
