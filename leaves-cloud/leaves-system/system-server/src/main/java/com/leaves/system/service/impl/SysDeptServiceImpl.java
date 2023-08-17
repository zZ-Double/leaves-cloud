package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.common.enums.StatusEnum;
import com.leaves.system.mapper.SysDeptMapper;
import com.leaves.system.model.entity.SysDept;
import com.leaves.system.model.param.DeptParam;
import com.leaves.system.model.vo.DeptVO;
import com.leaves.system.service.SysDeptService;
import com.leaves.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author leaves
 * @description 针对表【sys_dept(部门表)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    private final SysUserService userService;

    @Override
    public Boolean saveDept(DeptParam param) {

        // 重名判断
        Integer exist = this.baseMapper.checkUniqueName(param.getDeptName(), param.getParentId());
        Assert.isTrue(exist <= 0, "部门名称" + param.getDeptName() + "已存在");

        // 判断父节点状态
        SysDept parentDept = this.baseMapper.selectById(param.getParentId());
        Assert.isTrue(!parentDept.getStatus().equals(StatusEnum.DISABLE.getValue()), "部门已停用，不允许新增");

        SysDept sysDept = new SysDept();
        BeanUtil.copyProperties(param, sysDept, true);
        sysDept.setAncestors(parentDept.getAncestors() + "," + parentDept.getId());
        return this.baseMapper.insert(sysDept) > 0;
    }

    @Override
    public Boolean removeDept(String ids) {

        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的数据为空");
        List<String> deptIds = Arrays.asList(ids.split(","));

        // 判断当前部门是否存在下级节点
        QueryWrapper<SysDept> deptQueryWrapper = new QueryWrapper();
        deptQueryWrapper.lambda().in(CollectionUtil.isNotEmpty(deptIds), SysDept::getParentId, deptIds);
        Integer count = this.baseMapper.selectCount(deptQueryWrapper);
        Assert.isTrue(count == 0, "当前节点存在下级节点，不能删除");

        // 判断当前节点是否存在用户
        Integer integer = userService.listUserByDeptId(deptIds);
        Assert.isTrue(integer == 0, "当前节点存在用户，不能删除");

        return this.baseMapper.deleteBatchIds(deptIds) > 0;
    }

    @Override
    public Boolean updateDept(DeptParam param) {
        SysDept dbSysDept = this.baseMapper.selectById(param.getId());
        Assert.isTrue(Objects.nonNull(dbSysDept), "未查询到当前记录,请重试");

        BeanUtil.copyProperties(param, dbSysDept, true);
        return this.baseMapper.updateById(dbSysDept) > 0;
    }

    @Override
    public DeptVO getDept(String id) {
        SysDept dbSysDept = this.baseMapper.selectById(id);
        Assert.isTrue(Objects.nonNull(dbSysDept), "未查询到当前记录,请重试");
        DeptVO vo = new DeptVO();
        BeanUtil.copyProperties(dbSysDept, vo, true);
        return vo;
    }

    @Override
    public List<DeptVO> listDept(DeptParam param) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Objects.nonNull(param.getStatus()), SysDept::getStatus, param.getStatus())
                .like(StrUtil.isNotBlank(param.getDeptName()), SysDept::getDeptName, param.getDeptName())
                .orderByAsc(SysDept::getParentId, SysDept::getOrderNum);
        List<SysDept> deptList = this.baseMapper.selectList(queryWrapper);
        List<DeptVO> deptVOS = BeanUtil.copyToList(deptList, DeptVO.class);
        return deptVOS;
    }
}




