package com.ruoyi.project.zh.zh.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zh.zh.domain.Test001;
import com.ruoyi.project.zh.zh.service.ITest002Service;

/**
 * zhController
 * 
 * @author SangYiPing
 * @date 2022-07-01
 */
@Controller
@RequestMapping("/zh/hz")
public class Test002Controller extends BaseController
{
    private String prefix = "zh/hz";

    @Autowired
    private ITest002Service test002Service;

    @RequiresPermissions("zh:hz:view")
    @GetMapping()
    public String zh()
    {
        return prefix + "/hz";
    }

    /**
     * 查询zh列表
     */
    @RequiresPermissions("zh:hz:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Test001 test001)
    {
        startPage();
        List<Test001> list = test002Service.selectTest002List(test001);
        return getDataTable(list);
    }

    /**
     * 导出zh列表
     */
    @RequiresPermissions("zh:hz:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Test001 test001)
    {
        List<Test001> list = test002Service.selectTest002List(test001);
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
}
