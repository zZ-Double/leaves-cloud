package com.leaves.system.controller;

import com.leaves.common.annotation.OperaLog;
import com.leaves.common.model.Option;
import com.leaves.system.model.param.DeptParam;
import com.leaves.system.model.vo.DeptVO;
import com.leaves.system.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "部门管理")
@RestController
@RequestMapping("/api/v1/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;


    @ApiOperation(value = "新增部门")
    @PostMapping(value = "/save")
    @PreAuthorize("hasPerm('sys:dept:save')")
    @OperaLog(title = "新增部门")
    public Boolean saveDept(@RequestBody @Validated(DeptParam.add.class) DeptParam param) {
        return deptService.saveDept(param);
    }

    @ApiOperation(value = "删除部门")
    @GetMapping(value = "/remove/{ids}")
    @PreAuthorize("hasPerm('sys:dept:remove')")
    @OperaLog(title = "删除部门")
    public Boolean removeDept(@PathVariable String ids) {
        return deptService.removeDept(ids);
    }


    @ApiOperation(value = "修改部门")
    @PostMapping(value = "/update")
    @PreAuthorize("hasPerm('sys:dept:update')")
    @OperaLog(title = "修改部门")
    public Boolean updateDept(@RequestBody @Validated(DeptParam.edit.class) DeptParam param) {
        return deptService.updateDept(param);
    }

    @ApiOperation(value = "部门ID查询部门")
    @GetMapping(value = "/query/{id}")
    @PreAuthorize("hasPerm('sys:dept:query')")
    @OperaLog(title = "部门ID查询部门")
    public DeptVO getDept(@PathVariable String id) {
        return deptService.getDept(id);
    }


    @ApiOperation(value = "查询部门列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasPerm('sys:dept:list')")
    @OperaLog(title = "查询部门列表")
    public List<DeptVO> listDept(DeptParam param) {
        return deptService.listDept(param);
    }


    @ApiOperation(value = "部门下拉列表")
    @GetMapping(value = "/options")
    @PreAuthorize("hasPerm('sys:dept:options')")
    @OperaLog(title = "部门下拉列表")
    public List<Option> options(@RequestParam(required = false) String tenantId) {
        return deptService.deptOptions(tenantId);
    }
}
