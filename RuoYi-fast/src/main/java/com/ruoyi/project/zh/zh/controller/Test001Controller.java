package com.ruoyi.project.zh.zh.controller;

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
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zh.zh.domain.Test001;
import com.ruoyi.project.zh.zh.service.ITest001Service;

/**
 * zhController
 * 
 * @author SangYiPing
 * @date 2022-07-01
 */
@Controller
@RequestMapping("/zh/zh")
public class Test001Controller extends BaseController
{
    private String prefix = "zh/zh";

    @Autowired
    private ITest001Service test001Service;

    @RequiresPermissions("zh:zh:view")
    @GetMapping()
    public String zh()
    {
        return prefix + "/zh";
    }

    /**
     * 查询zh列表
     */
    @RequiresPermissions("zh:zh:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Test001 test001)
    {
        startPage();
        List<Test001> list = test001Service.selectTest001List(test001);
        return getDataTable(list);
    }

    /**
     * 导出zh列表
     */
    @RequiresPermissions("zh:zh:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Test001 test001)
    {
        List<Test001> list = test001Service.selectTest001List(test001);
        ExcelUtil<Test001> util = new ExcelUtil<Test001>(Test001.class);
        return util.exportExcel(list, "zh");
    }

    /**
     * 新增zh
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存zh
     */
    @RequiresPermissions("zh:zh:add")
    @Log(title = "zh", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Test001 test001)
    {
    	test001.setCzy(ShiroUtils.getSysUser().getUserName());
        return toAjax(test001Service.insertTest001(test001));
    }

    /**
     * 修改zh
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Test001 test001 = test001Service.selectTest001ById(id);
        mmap.put("test001", test001);
        return prefix + "/edit";
    }

    /**
     * 修改保存zh
     */
    @RequiresPermissions("zh:zh:edit")
    @Log(title = "zh", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Test001 test001)
    {
    	test001.setCzy(ShiroUtils.getSysUser().getUserName());
        return toAjax(test001Service.updateTest001(test001));
    }

    /**
     * 删除zh
     */
    @RequiresPermissions("zh:zh:remove")
    @Log(title = "zh", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(test001Service.deleteTest001ByIds(ids));
    }
}
