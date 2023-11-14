package com.ruoyi.project.flow.log.controller;

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
import com.ruoyi.project.flow.log.domain.FlowLogDetail;
import com.ruoyi.project.flow.log.service.IFlowLogDetailService;

/**
 * 调度日志详情Controller
 * 
 * @author SangYiPing
 * @date 2020-01-16
 */
@Controller
@RequestMapping("/flow/detail")
public class FlowLogDetailController extends BaseController
{
    private String prefix = "flow/log";

    @Autowired
    private IFlowLogDetailService flowLogDetailService;

    @RequiresPermissions("flow:detail:view")
    @GetMapping()
    public String detail()
    {
        return prefix + "/detail";
    }

    /**
     * 查询调度日志详情列表
     */
    @RequiresPermissions("flow:detail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowLogDetail flowLogDetail)
    {
        startPage();
        List<FlowLogDetail> list = flowLogDetailService.selectFlowLogDetailList(flowLogDetail);
        return getDataTable(list);
    }

    /**
     * 导出调度日志详情列表
     */
    @RequiresPermissions("flow:detail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowLogDetail flowLogDetail)
    {
        List<FlowLogDetail> list = flowLogDetailService.selectFlowLogDetailList(flowLogDetail);
        ExcelUtil<FlowLogDetail> util = new ExcelUtil<FlowLogDetail>(FlowLogDetail.class);
        return util.exportExcel(list, "detail");
    }

    /**
     * 新增调度日志详情
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存调度日志详情
     */
    @RequiresPermissions("flow:detail:add")
    @Log(title = "调度日志详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FlowLogDetail flowLogDetail)
    {
        return toAjax(flowLogDetailService.insertFlowLogDetail(flowLogDetail));
    }

    /**
     * 修改调度日志详情
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        FlowLogDetail flowLogDetail = flowLogDetailService.selectFlowLogDetailById(id);
        mmap.put("flowLogDetail", flowLogDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存调度日志详情
     */
    @RequiresPermissions("flow:detail:edit")
    @Log(title = "调度日志详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowLogDetail flowLogDetail)
    {
        return toAjax(flowLogDetailService.updateFlowLogDetail(flowLogDetail));
    }

    /**
     * 删除调度日志详情
     */
    @RequiresPermissions("flow:detail:remove")
    @Log(title = "调度日志详情", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowLogDetailService.deleteFlowLogDetailByIds(ids));
    }
}
