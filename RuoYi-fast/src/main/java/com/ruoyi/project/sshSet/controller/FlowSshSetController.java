package com.ruoyi.project.sshSet.controller;

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

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.sshSet.domain.FlowSshSet;
import com.ruoyi.project.sshSet.service.IFlowSshSetService;

/**
 * 【SSH连接管理】Controller
 * 
 * @author SangYiPing
 * @date 2019-11-07
 */
@Controller
@RequestMapping("/flow/sshSet")
public class FlowSshSetController extends BaseController
{
    private String prefix = "flow/sshSet";

    @Autowired
    private IFlowSshSetService flowSshSetService;

    @RequiresPermissions("chart:set:view")
    @GetMapping()
    public String set()
    {
        return prefix + "/set";
    }

    /**
     * 查询【SSH连接管理】列表
     */
    @RequiresPermissions("chart:set:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowSshSet flowSshSet)
    {
        startPage();
        List<FlowSshSet> list = flowSshSetService.selectFlowSshSetList(flowSshSet);
        return getDataTable(list);
    }

    /**
     * 导出【SSH连接管理】列表
     */
    @RequiresPermissions("chart:set:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowSshSet flowSshSet)
    {
        List<FlowSshSet> list = flowSshSetService.selectFlowSshSetList(flowSshSet);
        ExcelUtil<FlowSshSet> util = new ExcelUtil<FlowSshSet>(FlowSshSet.class);
        return util.exportExcel(list, "set");
    }
    
    /**
     * 查询SSH连接管理列表所有
     */
    @PostMapping("/listAll")
    @ResponseBody
    public List<FlowSshSet> listAll(){
    	FlowSshSet flowSshSet = new FlowSshSet();
		return flowSshSetService.selectFlowSshSetList(flowSshSet);
    }
    

    /**
     * 新增【SSH连接管理】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【SSH连接管理】
     */
    @RequiresPermissions("chart:set:add")
    @Log(title = "【SSH连接管理】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(FlowSshSet flowSshSet) {
    	flowSshSet.setId(UUID.randomUUID().toString().replace("-", ""));
		return toAjax(flowSshSetService.insertFlowSshSet(flowSshSet));
	}

    /**
     * 修改【SSH连接管理】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        FlowSshSet flowSshSet = flowSshSetService.selectFlowSshSetById(id);
        mmap.put("flowSshSet", flowSshSet);
        return prefix + "/edit";
    }

    /**
     * 修改保存【SSH连接管理】
     */
    @RequiresPermissions("chart:set:edit")
    @Log(title = "【SSH连接管理】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowSshSet flowSshSet)
    {
        return toAjax(flowSshSetService.updateFlowSshSet(flowSshSet));
    }

    /**
     * 删除【SSH连接管理】
     */
    @RequiresPermissions("chart:set:remove")
    @Log(title = "【SSH连接管理】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowSshSetService.deleteFlowSshSetByIds(ids));
    }
}
