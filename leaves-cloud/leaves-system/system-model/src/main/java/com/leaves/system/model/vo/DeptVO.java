package com.leaves.system.model.vo;

import com.leaves.system.model.entity.SysDept;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DeptVO extends SysDept {

    private List<DeptVO> children;
}
