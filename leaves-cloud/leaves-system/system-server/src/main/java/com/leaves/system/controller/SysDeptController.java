package com.leaves.system.controller;


import cn.hutool.core.util.ArrayUtil;
import com.leaves.system.model.param.DeptParam;
import com.leaves.system.model.vo.DeptVO;
import com.leaves.system.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@Api(tags = "部门管理")
@RestController
@RequestMapping("/api/v1/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;


    @PostMapping(value = "/save")
    @ApiOperation(value = "新增部门")
    public Boolean saveDept(@RequestBody @Validated(DeptParam.add.class) DeptParam param) {
        return deptService.saveDept(param);
    }


    @GetMapping(value = "/remove/{ids}")
    @ApiOperation(value = "删除部门")
    public Boolean removeDept(@PathVariable String ids) {
        return deptService.removeDept(ids);
    }


    @PostMapping(value = "/update")
    @ApiOperation(value = "修改部门")
    public Boolean updateDept(@RequestBody @Validated(DeptParam.edit.class) DeptParam param) {
        return deptService.updateDept(param);
    }


    @GetMapping(value = "/query/{id}")
    @ApiOperation(value = "部门ID查询部门")
    public DeptVO getDept(@PathVariable String id) {
        return deptService.getDept(id);
    }


    @GetMapping(value = "/list")
    @ApiOperation(value = "查询部门列表")
    public List<DeptVO> listDept(DeptParam param) {
        return deptService.listDept(param);
    }

    @GetMapping(value = "/list/exclude/{id}")
    @ApiOperation(value = "查询部门列表，排除本身")
    public List<DeptVO> listDeptExclude(@PathVariable String id) {
        List<DeptVO> deptList = deptService.listDept(new DeptParam());
        Iterator<DeptVO> it = deptList.iterator();
        while (it.hasNext()) {
            DeptVO next = it.next();
            boolean contains = ArrayUtil.contains(StringUtils.split(next.getAncestors(), ","), id);
            if (next.getId().equals(id) || contains) {
                it.remove();
            }
        }
        return deptList;
    }
}
