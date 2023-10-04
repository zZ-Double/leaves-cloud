package com.leaves.system.model.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/9/6
 */
@Setter
@Getter
public class PasswdForm implements Serializable {

    // 愿密码
    private String oldPasswd;
    // 新密码
    private String newPasswd;
    // 确认密码
    private String confirmPasswd;
}
