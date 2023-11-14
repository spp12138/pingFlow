package com.ruoyi.project.flow.subTask.controller;

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
import com.ruoyi.project.flow.subTask.domain.FlowTaskSubInfo;
import com.ruoyi.project.flow.subTask.service.IFlowTaskSubInfoService;

/**
 * 【任务详情】Controller
 * 
 * @author SangYiPing
 * @date 2019-10-27
 */
@Controller
@RequestMapping("flowSubTask")
public class FlowTaskSubInfoController extends BaseController
{
    private String prefix = "subTask";

    @Autowired
    private IFlowTaskSubInfoService flowTaskSubInfoService;

    @RequiresPermissions("subTask:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 查询【任务详情】列表
     */
    @RequiresPermissions("subTask:list")
    @PostMapping("/list")
    @ResponseBody
	public TableDataInfo list(FlowTaskSubInfo flowTaskSubInfo) {
		startPage();
		List<FlowTaskSubInfo> list = flowTaskSubInfoService.selectFlowTaskSubInfoList(flowTaskSubInfo);
		return getDataTable(list);
	}

    /**
     * 导出【任务详情】列表
     */
    @RequiresPermissions("subTask:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowTaskSubInfo flowTaskSubInfo)
    {
        List<FlowTaskSubInfo> list = flowTaskSubInfoService.selectFlowTaskSubInfoList(flowTaskSubInfo);
        ExcelUtil<FlowTaskSubInfo> util = new ExcelUtil<FlowTaskSubInfo>(FlowTaskSubInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增【任务详情】
     */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

    /**
     * 新增保存【任务详情】
     */
    @RequiresPermissions("subTask:add")
    @Log(title = "【任务详情】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FlowTaskSubInfo flowTaskSubInfo){
        return toAjax(flowTaskSubInfoService.insertFlowTaskSubInfo(flowTaskSubInfo));
    }

    /**
     * 修改【任务详情】
     */
    @GetMapping("/edit/{flowId}")
    public String edit(@PathVariable("flowId") String flowId, ModelMap mmap){
        FlowTaskSubInfo flowTaskSubInfo = flowTaskSubInfoService.selectFlowTaskSubInfoById(flowId);
        mmap.put("flowTaskSubInfo", flowTaskSubInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存【任务详情】
     */
    @RequiresPermissions("subTask:edit")
    @Log(title = "【任务详情】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowTaskSubInfo flowTaskSubInfo){
        return toAjax(flowTaskSubInfoService.updateFlowTaskSubInfo(flowTaskSubInfo));
    }

    /**
     * 删除【任务详情】
     */
    @RequiresPermissions("subTask:remove")
    @Log(title = "【任务详情】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids){
        return toAjax(flowTaskSubInfoService.deleteFlowTaskSubInfoByIds(ids));
    }
}
