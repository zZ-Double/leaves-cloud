package com.leaves.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaves.system.mapper.SysDeptMapper;
import com.leaves.system.model.entity.SysDept;
import com.leaves.system.model.param.DeptParam;
import com.leaves.system.model.vo.DeptVO;
import com.leaves.system.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leaves
 * @description 针对表【sys_dept(部门表)】的数据库操作Service实现
 * @createDate 2023-07-11 11:40:10
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public Boolean saveDept(DeptParam param) {
        return null;
    }

    @Override
    public Boolean removeDept(List<String> deptIds) {
        return null;
    }

    @Override
    public Boolean updateDept(DeptParam param) {
        return null;
    }

    @Override
    public DeptVO getDept(String id) {
        return null;
    }

    @Override
    public List<DeptVO> listDept(DeptParam param) {
        return null;
    }
}




