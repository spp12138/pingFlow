package com.ruoyi.project.flow.task.controller;

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
import com.ruoyi.project.flow.task.domain.FlowTaskInfo;
import com.ruoyi.project.flow.task.service.IFlowTaskInfoService;

/**
 * 【流程任务】Controller
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Controller
@RequestMapping("flowtask")
public class FlowTaskInfoController extends BaseController
{
    private String prefix = "task";

    @Autowired
    private IFlowTaskInfoService flowTaskInfoService;

    @RequiresPermissions("flowtask:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 【流程任务】页面保存
     */
    @RequiresPermissions("flowtask:save")
    @Log(title = "【流程任务】", businessType = BusinessType.UPDATE)
    @PostMapping("/save")
    @ResponseBody
	public AjaxResult save(FlowTaskInfo flowTaskInfo) {
   		return toAjax(flowTaskInfoService.insertFlowTaskInfo(flowTaskInfo));
	}
    /**
     * 【流程任务】根据流程ID查询
     */
    @RequiresPermissions("flowtask:list")
    @PostMapping("/findByFlowId")
    @ResponseBody
    public FlowTaskInfo findByFlowId(FlowTaskInfo flowTaskInfo) {
    	return flowTaskInfoService.selectFlowTaskInfoById(flowTaskInfo.getFlowId());
    }
    
    /**
     * 查询【流程任务】列表
     */
    @RequiresPermissions("flowtask:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowTaskInfo flowTaskInfo)
    {
        startPage();
        List<FlowTaskInfo> list = flowTaskInfoService.selectFlowTaskInfoList(flowTaskInfo);
        return getDataTable(list);
    }

    /**
     * 导出【流程任务】列表
     */
    @RequiresPermissions("flowtask:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowTaskInfo flowTaskInfo)
    {
        List<FlowTaskInfo> list = flowTaskInfoService.selectFlowTaskInfoList(flowTaskInfo);
        ExcelUtil<FlowTaskInfo> util = new ExcelUtil<FlowTaskInfo>(FlowTaskInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增【流程任务】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【流程任务】
     */
    @RequiresPermissions("flowtask:add")
    @Log(title = "【流程任务】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FlowTaskInfo flowTaskInfo)
    {
        return toAjax(flowTaskInfoService.insertFlowTaskInfo(flowTaskInfo));
    }

    /**
     * 修改【流程任务】
     */
    @GetMapping("/edit/{flowId}")
    public String edit(@PathVariable("flowId") String flowId, ModelMap mmap)
    {
        FlowTaskInfo flowTaskInfo = flowTaskInfoService.selectFlowTaskInfoById(flowId);
        mmap.put("flowTaskInfo", flowTaskInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存【流程任务】
     */
    @RequiresPermissions("flowtask:edit")
    @Log(title = "【流程任务】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowTaskInfo flowTaskInfo)
    {
        return toAjax(flowTaskInfoService.updateFlowTaskInfo(flowTaskInfo));
    }

    /**
     * 删除【流程任务】
     */
    @RequiresPermissions("flowtask:remove")
    @Log(title = "【流程任务】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowTaskInfoService.deleteFlowTaskInfoByIds(ids));
    }
    
}
