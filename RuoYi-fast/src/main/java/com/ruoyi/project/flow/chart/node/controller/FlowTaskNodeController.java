package com.ruoyi.project.flow.chart.node.controller;

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
import com.ruoyi.project.flow.chart.node.domain.FlowTaskNode;
import com.ruoyi.project.flow.chart.node.service.IFlowTaskNodeService;

/**
 * 【流程任务节点】Controller
 * 
 * @author SangYiPing
 * @date 2019-10-31
 */
@Controller
@RequestMapping("/flow/chart/node")
public class FlowTaskNodeController extends BaseController
{
    private String prefix = "chart/node";

    @Autowired
    private IFlowTaskNodeService flowTaskNodeService;

    @RequiresPermissions("chart:node:view")
    @GetMapping()
    public String node()
    {
        return prefix + "/node";
    }

    /**
     * 查询【流程任务节点】列表
     */
    @RequiresPermissions("chart:node:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowTaskNode flowTaskNode)
    {
        startPage();
        List<FlowTaskNode> list = flowTaskNodeService.selectFlowTaskNodeList(flowTaskNode);
        return getDataTable(list);
    }

    /**
     * 导出【流程任务节点】列表
     */
    @RequiresPermissions("chart:node:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowTaskNode flowTaskNode)
    {
        List<FlowTaskNode> list = flowTaskNodeService.selectFlowTaskNodeList(flowTaskNode);
        ExcelUtil<FlowTaskNode> util = new ExcelUtil<FlowTaskNode>(FlowTaskNode.class);
        return util.exportExcel(list, "node");
    }

    /**
     * 新增【流程任务节点】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【流程任务节点】
     */
    @RequiresPermissions("chart:node:add")
    @Log(title = "【流程任务节点】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FlowTaskNode flowTaskNode)
    {
        return toAjax(flowTaskNodeService.insertFlowTaskNode(flowTaskNode));
    }

    /**
     * 修改【流程任务节点】
     */
    @GetMapping("/edit/{flowid}")
    public String edit(@PathVariable("flowid") String flowid, ModelMap mmap)
    {
        FlowTaskNode flowTaskNode = flowTaskNodeService.selectFlowTaskNodeById(flowid);
        mmap.put("flowTaskNode", flowTaskNode);
        return prefix + "/edit";
    }

    /**
     * 修改保存【流程任务节点】
     */
    @RequiresPermissions("chart:node:edit")
    @Log(title = "【流程任务节点】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowTaskNode flowTaskNode)
    {
        return toAjax(flowTaskNodeService.updateFlowTaskNode(flowTaskNode));
    }

    /**
     * 删除【流程任务节点】
     */
    @RequiresPermissions("chart:node:remove")
    @Log(title = "【流程任务节点】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowTaskNodeService.deleteFlowTaskNodeByIds(ids));
    }
}
