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
import com.ruoyi.project.flow.log.domain.FlowLog;
import com.ruoyi.project.flow.log.service.IFlowLogService;

/**
 * 调度日志Controller
 * 
 * @author SangYiPing
 * @date 2020-01-16
 */
@Controller
@RequestMapping("/flow/log")
public class FlowLogController extends BaseController {
	private String prefix = "flow/log";

	@Autowired
	private IFlowLogService flowLogService;

	@RequiresPermissions("flow:log:view")
	@GetMapping()
	public String log() {
		return prefix + "/log";
	}

	/**
	 * 查询调度日志列表
	 */
	@RequiresPermissions("flow:log:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(FlowLog flowLog) {
		startPage();
		List<FlowLog> list = flowLogService.selectFlowLogList(flowLog);
		return getDataTable(list);
	}

	/**
	 * 导出调度日志列表
	 */
	@RequiresPermissions("flow:log:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(FlowLog flowLog) {
		List<FlowLog> list = flowLogService.selectFlowLogList(flowLog);
		ExcelUtil<FlowLog> util = new ExcelUtil<FlowLog>(FlowLog.class);
		return util.exportExcel(list, "log");
	}

	/**
	 * 新增调度日志
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存调度日志
	 */
	@RequiresPermissions("flow:log:add")
	@Log(title = "调度日志", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(FlowLog flowLog) {
		return toAjax(flowLogService.insertFlowLog(flowLog));
	}

	/**
	 * 修改调度日志
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap) {
		FlowLog flowLog = flowLogService.selectFlowLogById(id);
		mmap.put("flowLog", flowLog);
		return prefix + "/edit";
	}

	/**
	 * 修改保存调度日志
	 */
	@RequiresPermissions("flow:log:edit")
	@Log(title = "调度日志", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(FlowLog flowLog) {
		return toAjax(flowLogService.updateFlowLog(flowLog));
	}

	/**
	 * 删除调度日志
	 */
	@RequiresPermissions("flow:log:remove")
	@Log(title = "调度日志", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(flowLogService.deleteFlowLogByIds(ids));
	}

	/**
	 * 查看日志详情
	 */
	@Log(title = "查看调度日志详情", businessType = BusinessType.DELETE)
	@RequestMapping("/openDetail")
	public String openDetail(String flowSlId, ModelMap mmap) {
		mmap.put("flowSlId", flowSlId);
		return prefix + "/detail";
	}
}
