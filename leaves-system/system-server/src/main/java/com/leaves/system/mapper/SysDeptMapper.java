package com.leaves.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaves.system.model.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author leaves
 * @description 针对表【sys_dept(部门表)】的数据库操作Mapper
 * @createDate 2023-07-11 11:40:10
 * @Entity com.leaves.system.model.entity.SysDept
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    @Select("SELECT count(1) from sys_dept where dept_name = #{deptName} and parent_id = #{parentId} limit 1")
    Integer checkUniqueName(@Param("deptName") String deptName, @Param("parentId") String parentId);

}




