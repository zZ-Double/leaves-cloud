package com.leaves.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.common.model.Option;
import com.leaves.common.constant.GlobalConstants;
import com.leaves.common.enums.StatusEnum;
import com.leaves.system.mapper.SysDeptMapper;
import com.leaves.system.model.entity.SysDept;
import com.leaves.system.model.param.DeptParam;
import com.leaves.system.model.vo.DeptVO;
import com.leaves.system.service.SysDeptService;
import com.leaves.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        Integer exist = this.baseMapper.checkUniqueName(param.getDeptName(), param.getParentId(), "");
        Assert.isTrue(exist <= 0, "部门名称" + param.getDeptName() + "已存在");

        // 判断父节点状态
        SysDept parentDept = null;
        if (!param.getParentId().equals(GlobalConstants.ROOT_NODE_ID)) {
            parentDept = this.baseMapper.selectById(param.getParentId());
            Assert.isTrue(!parentDept.getStatus().equals(StatusEnum.DISABLE.getValue()), "部门已停用，不允许新增");
        }

        SysDept sysDept = new SysDept();
        BeanUtil.copyProperties(param, sysDept, true);
        sysDept.setAncestors(Objects.nonNull(parentDept) ? parentDept.getAncestors() + "," + parentDept.getId() : GlobalConstants.ROOT_NODE_ID);
        return this.baseMapper.insert(sysDept) > 0;
    }

    @Override
    public Boolean removeDept(String ids) {

        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的数据为空");
        List<String> deptIds = Arrays.asList(ids.split(","));

        // 判断当前节点是否存在用户
        Integer integer = userService.listUserByDeptId(deptIds);
        Assert.isTrue(integer == 0, "当前节点存在用户，不能删除");

        return this.baseMapper.deleteBatchIds(deptIds) > 0;
    }

    @Override
    public Boolean updateDept(DeptParam param) {
        SysDept dbSysDept = this.baseMapper.selectById(param.getId());
        Assert.isTrue(Objects.nonNull(dbSysDept), "未查询到当前记录,请重试");

        // 重名判断
        Integer exist = this.baseMapper.checkUniqueName(param.getDeptName(), param.getParentId(), dbSysDept.getId());
        Assert.isTrue(exist <= 0, "部门名称" + param.getDeptName() + "已存在");

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
                .like(StrUtil.isNotBlank(param.getLeader()), SysDept::getLeader, param.getLeader())
                .orderByAsc(SysDept::getParentId, SysDept::getOrderNum);
        List<SysDept> deptList = this.baseMapper.selectList(queryWrapper);

        List<DeptVO> list = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(deptList)) {

            Set<String> cacheDeptIds = deptList.stream().map(SysDept::getId).collect(Collectors.toSet());

            for (SysDept dept : deptList) {
                String parentId = dept.getParentId();
                // 不在缓存ID列表的parentId是顶级节点ID，以此作为递归开始
                if (cacheDeptIds.contains(parentId) == false) {
                    list.addAll(recurDepartments(parentId, deptList));
                    cacheDeptIds.add(parentId); // 避免重复递归
                }
            }
        }

        //  列表为空说明所有的节点都是独立的
        if (list.isEmpty()) {
            return BeanUtil.copyToList(deptList, DeptVO.class);
        }

        return list;
    }

    /**
     * 部门下拉选项
     */
    @Override
    public List<Option> deptOptions() {
        List<SysDept> deptList = this.list(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getStatus, StatusEnum.ENABLE.getValue())
                .select(SysDept::getId, SysDept::getParentId, SysDept::getDeptName)
                .orderByAsc(SysDept::getOrderNum)
        );

        List<Option> options = buildDeptTree(deptList);
        return options;
    }

    /**
     * 递归生成部门层级列表
     *
     * @param parentId
     * @param deptList
     * @return
     */
    public List<DeptVO> recurDepartments(String parentId, List<SysDept> deptList) {
        List<DeptVO> list = deptList.stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .map(dept -> {
                    DeptVO deptVO = new DeptVO();
                    BeanUtil.copyProperties(dept, deptVO);
                    List<DeptVO> children = recurDepartments(dept.getId(), deptList);
                    deptVO.setChildren(children);
                    return deptVO;
                }).collect(Collectors.toList());
        return list;
    }


    /**
     * 递归生成部门表格层级列表
     */
    public List<Option> buildDeptTree(List<SysDept> deptList) {
        if (CollectionUtil.isEmpty(deptList)) {
            return Collections.EMPTY_LIST;
        }
        List<Option> returnList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        for (SysDept dept : deptList) {
            tempList.add(dept.getId());
        }
        for (SysDept dept : deptList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                Option option = new Option(dept.getId(), dept.getDeptName());
                recursionFn(deptList, option);
                returnList.add(option);
            }
        }
        if (returnList.isEmpty()) {
            deptList.stream().forEach(dept -> {
                Option option = new Option(dept.getId(), dept.getDeptName());
                returnList.add(option);
            });
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, Option t) {
        // 得到子节点列表
        List<Option> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Option tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Option> getChildList(List<SysDept> list, Option t) {
        List<Option> options = new ArrayList<>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext()) {
            SysDept n = it.next();
            if (n.getParentId() != null && n.getParentId() == t.getValue()) {
                Option option = new Option(n.getId(), n.getDeptName());
                options.add(option);
            }
        }
        return options;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, Option t) {
        return getChildList(list, t).size() > 0;
    }
}




