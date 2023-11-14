package com.ruoyi.project.flow.info.controller;

import java.util.List;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.flow.info.domain.FlowInfo;
import com.ruoyi.project.flow.info.service.IFlowInfoService;
import com.ruoyi.project.flow.task.domain.FlowTaskInfo;
import com.ruoyi.project.flow.task.service.IFlowTaskInfoService;
import com.ruoyi.project.system.user.domain.User;

/**
 * 调度管理Controller
 * 
 * @author SangYiPing
 * @date 2019-10-26
 */
@Controller
@RequestMapping("/flow/info")
public class FlowInfoController extends BaseController
{
    private String prefix = "flow/info";

    @Autowired
    private IFlowInfoService flowInfoService;
    @Autowired
    private IFlowTaskInfoService flowTaskInfoService;

    @RequiresPermissions("flow:info:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 查询调度管理列表
     */
    @RequiresPermissions("flow:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowInfo flowInfo)
    {
        startPage();
        List<FlowInfo> list = flowInfoService.selectFlowInfoList(flowInfo);
        return getDataTable(list);
    }

    /**
     * 导出调度管理列表
     */
    @RequiresPermissions("flow:info:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowInfo flowInfo)
    {
        List<FlowInfo> list = flowInfoService.selectFlowInfoList(flowInfo);
        ExcelUtil<FlowInfo> util = new ExcelUtil<FlowInfo>(FlowInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增调度管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存调度管理
     */
    @RequiresPermissions("flow:info:add")
    @Log(title = "调度管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FlowInfo flowInfo)
    {
    	User sysUser = ShiroUtils.getSysUser();
    	flowInfo.setCreateBy(sysUser.getUserName());
    	flowInfo.setUpdateBy(sysUser.getUserName());
    	flowInfo.setCreateTime(DateUtils.getNowDate());
    	flowInfo.setUpdateTime(DateUtils.getNowDate());
    	flowInfo.setFlowId(UUID.randomUUID().toString().replace("-", ""));
        if (UserConstants.FLOW_NAME_NOT_UNIQUE.equals(flowInfoService.checkFlowNameUnique(flowInfo))){
            return error("新增流程'" + flowInfo.getFlowName() + "'失败，流程名称已存在");
        }
        return toAjax(flowInfoService.insertFlowInfo(flowInfo));
    }

    /**
     * 修改调度管理
     */
    @GetMapping("/edit/{flowId}")
    public String edit(@PathVariable("flowId") String flowId, ModelMap mmap)
    {
        FlowInfo flowInfo = flowInfoService.selectFlowInfoById(flowId);
        mmap.put("flowInfo", flowInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存调度管理
     */
    @RequiresPermissions("flow:info:edit")
    @Log(title = "调度管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowInfo flowInfo)
    {
        return toAjax(flowInfoService.updateFlowInfo(flowInfo));
    }

    /**
     * 删除调度管理
     */
    @RequiresPermissions("flow:info:remove")
    @Log(title = "调度管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowInfoService.deleteFlowInfoByIds(ids));
    }
    /**
     * 打开流程
     */
	@GetMapping("/openFlow/{flow_id}")
	public String detail(@PathVariable("flow_id") String flow_id, ModelMap mmap) {
		FlowInfo selectFlowInfoById = flowInfoService.selectFlowInfoById(flow_id);
		mmap.put("flow_id", flow_id);
		mmap.put("flowTitle", selectFlowInfoById.getFlowName());
		FlowTaskInfo flowTaskInfoById = flowTaskInfoService.selectFlowTaskInfoById(flow_id);
		if(null != flowTaskInfoById){
			mmap.put("flowTask", flowTaskInfoById.getFlowJson());
		}
		return prefix + "/flowIndex";
	}
}
