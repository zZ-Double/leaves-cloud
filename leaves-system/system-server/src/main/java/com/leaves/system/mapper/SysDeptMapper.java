package com.leaves.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaves.system.model.entity.SysDept;
import org.apache.ibatis.annotations.Param;

/**
 * @author leaves
 * @description 针对表【sys_dept(部门表)】的数据库操作Mapper
 * @createDate 2023-07-11 11:40:10
 * @Entity com.leaves.system.model.entity.SysDept
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    Integer checkUniqueName(@Param("deptName") String deptName, @Param("parentId") String parentId, @Param("id") String id);

}




