package com.ruoyi.project.jdbcSet.controller;

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

import com.ruoyi.common.utils.JDBCUtil;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.jdbcSet.domain.FlowJdbcSet;
import com.ruoyi.project.jdbcSet.service.IFlowJdbcSetService;

/**
 * 数据库连接管理Controller
 * 
 * @author SangYiPing
 * @date 2019-11-04
 */
@Controller
@RequestMapping("/flow/jdbcSet")
public class FlowJdbcSetController extends BaseController
{
    private String prefix = "flow/jdbcSet";

    @Autowired
    private IFlowJdbcSetService flowJdbcSetService;

    @RequiresPermissions("chart:set:view")
    @GetMapping()
    public String set()
    {
        return prefix + "/set";
    }

    /**
     * 测试数据库连接
     */
    @PostMapping("/testConnDb")
    @ResponseBody
    public AjaxResult testConnDb(FlowJdbcSet flowJdbcSet){
    	try {
    		JDBCUtil jc = new JDBCUtil(flowJdbcSet.getJdbcDriver(), flowJdbcSet.getJdbcUrl(), flowJdbcSet.getJdbcUsername(), flowJdbcSet.getJdbcPassword());
    		jc.excuteQuery("select 1 from dual", new Object[]{});
		} catch (Exception e) {
			return AjaxResult.error("连接失败!<br/>"+e.getMessage());
		}
    	return AjaxResult.success("连接成功!");
    }
    
    /**
     * 测试数据库连接By Id
     */
    @PostMapping("/testConnDbById")
    @ResponseBody
    public AjaxResult testConnDbById(String id){
    	FlowJdbcSet flowJdbcSet = flowJdbcSetService.selectFlowJdbcSetById(id);
    	return testConnDb(flowJdbcSet);
    }
    
    
    /**
     * 查询数据库连接管理列表所有
     */
    @PostMapping("/listAll")
    @ResponseBody
    public List<FlowJdbcSet> listAll(){
    	FlowJdbcSet flowJdbcSet = new FlowJdbcSet();
		return flowJdbcSetService.selectFlowJdbcSetList(flowJdbcSet);
    }
    /**
     * 查询数据库连接管理列表
     */
    @RequiresPermissions("chart:set:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FlowJdbcSet flowJdbcSet)
    {
        startPage();
        List<FlowJdbcSet> list = flowJdbcSetService.selectFlowJdbcSetList(flowJdbcSet);
        return getDataTable(list);
    }

    /**
     * 导出数据库连接管理列表
     */
    @RequiresPermissions("chart:set:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FlowJdbcSet flowJdbcSet)
    {
        List<FlowJdbcSet> list = flowJdbcSetService.selectFlowJdbcSetList(flowJdbcSet);
        ExcelUtil<FlowJdbcSet> util = new ExcelUtil<FlowJdbcSet>(FlowJdbcSet.class);
        return util.exportExcel(list, "set");
    }

    /**
     * 新增数据库连接管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存数据库连接管理
     */
    @RequiresPermissions("chart:set:add")
    @Log(title = "数据库连接管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FlowJdbcSet flowJdbcSet)
    {
    	flowJdbcSet.setId(UUID.randomUUID().toString().replace("-", ""));
        return toAjax(flowJdbcSetService.insertFlowJdbcSet(flowJdbcSet));
    }

    /**
     * 修改数据库连接管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        FlowJdbcSet flowJdbcSet = flowJdbcSetService.selectFlowJdbcSetById(id);
        mmap.put("flowJdbcSet", flowJdbcSet);
        return prefix + "/edit";
    }

    /**
     * 修改保存数据库连接管理
     */
    @RequiresPermissions("chart:set:edit")
    @Log(title = "数据库连接管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FlowJdbcSet flowJdbcSet)
    {
        return toAjax(flowJdbcSetService.updateFlowJdbcSet(flowJdbcSet));
    }

    /**
     * 删除数据库连接管理
     */
    @RequiresPermissions("chart:set:remove")
    @Log(title = "数据库连接管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(flowJdbcSetService.deleteFlowJdbcSetByIds(ids));
    }
}
