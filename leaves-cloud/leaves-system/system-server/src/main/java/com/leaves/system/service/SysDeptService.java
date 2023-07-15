package com.leaves.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaves.system.model.entity.SysDept;
import com.leaves.system.model.param.DeptParam;
import com.leaves.system.model.vo.DeptVO;

import java.util.List;

/**
 * @author leaves
 * @description 针对表【sys_dept(部门表)】的数据库操作Service
 * @createDate 2023-07-11 11:40:10
 */
public interface SysDeptService extends IService<SysDept> {

    Boolean saveDept(DeptParam param);

    Boolean removeDept(String ids);

    Boolean updateDept(DeptParam param);

    DeptVO getDept(String id);

    List<DeptVO> listDept(DeptParam param);
}
