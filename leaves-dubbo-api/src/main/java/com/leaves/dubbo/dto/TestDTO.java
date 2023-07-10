package com.leaves.dubbo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/10
 */
@Setter
@Getter
public class TestDTO implements Serializable {

    private String name;

    private Integer age;
}
