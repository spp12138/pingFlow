package com.ruoyi.project.flow.param.controller;

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
import com.ruoyi.project.flow.param.domain.FlowParamInfo;
import com.ruoyi.project.flow.param.service.IFlowParamInfoService;
import com.ruoyi.project.flow.task.domain.FlowTaskInfo;
import com.ruoyi.project.flow.task.service.IFlowTaskInfoService;

/**
 * 【流程参数】Controller
 * 
 * @author SangYiPing
 * @date 2019-11-05
 */
@Controller
@RequestMapping("/flow/param")
public class FlowParamInfoController extends BaseController
{
    private String prefix = "flow/param";

    @Autowired
    private IFlowParamInfoService flowParamInfoService;
    @Autowired
    private IFlowTaskInfoService flowTaskInfoService;

    @RequiresPermissions("flow:param:view")
    @GetMapping("/info/{flowId}")
    public String info(@PathVariable("flowId") String flowId, ModelMap mmap){
    	FlowTaskInfo flowTaskSubInfo = flowTaskInfoService.selectFlowTaskInfoById(flowId);
    	mmap.put("flowId", flowId);
    	mmap.put("flowTaskSubInfo", flowTaskSubInfo);
        return prefix + "/info";
    }

    /**
     * 查询【流程参数】列表
     */
    @RequiresPermissions("flow:param:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowParamInfo flowParamInfo)
    {
        startPage();
        List<FlowParamInfo> list = flowParamInfoService.selectFlowParamInfoList(flowParamInfo);
        return getDataTable(list);
    }

    /**
     * 导出【流程参数】列表
     */
    @RequiresPermissions("flow:param:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowParamInfo flowParamInfo)
    {
        List<FlowParamInfo> list = flowParamInfoService.selectFlowParamInfoList(flowParamInfo);
        ExcelUtil<FlowParamInfo> util = new ExcelUtil<FlowParamInfo>(FlowParamInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
	 * 新增【流程参数】
	 */
	@GetMapping("/add/{flowId}")
	public String add(@PathVariable("flowId") String flowId, ModelMap mmap) {
		mmap.put("flowId", flowId);
 		return prefix + "/add";
	}

    /**
     * 新增保存【流程参数】
     */
    @RequiresPermissions("flow:param:add")
    @Log(title = "【流程参数】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
	public AjaxResult addSave(FlowParamInfo flowParamInfo) {
		return toAjax(flowParamInfoService.insertFlowParamInfo(flowParamInfo));
	}

    /**
     * 修改【流程参数】
     */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap) {
		FlowParamInfo flowParamInfo = flowParamInfoService.selectFlowParamInfoById(id);
		mmap.put("flowParamInfo", flowParamInfo);
		return prefix + "/edit";
	}

    /**
     * 修改保存【流程参数】
     */
    @RequiresPermissions("flow:param:edit")
    @Log(title = "【流程参数】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
	public AjaxResult editSave(FlowParamInfo flowParamInfo) {
		return toAjax(flowParamInfoService.updateFlowParamInfo(flowParamInfo));
	}

    /**
     * 删除【流程参数】
     */
    @RequiresPermissions("flow:param:remove")
    @Log(title = "【流程参数】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowParamInfoService.deleteFlowParamInfoByIds(ids));
    }
}
