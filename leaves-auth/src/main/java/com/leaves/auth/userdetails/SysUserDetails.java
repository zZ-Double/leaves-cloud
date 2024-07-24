package com.leaves.auth.userdetails;

import cn.hutool.core.collection.CollectionUtil;
import com.leaves.auth.enums.PasswordEncoderTypeEnum;
import com.leaves.common.enums.StatusEnum;
import com.leaves.system.model.vo.UserVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/13
 */
@Data
public class SysUserDetails implements UserDetails {

    /**
     * 扩展字段：用户ID
     */
    private String userId;

    /**
     * 扩展字段：部门ID
     */
    private String deptId;

    /**
     * 用户角色数据权限集合
     */
    private Integer dataScope;

    /**
     * 用户租户ID
     */
    private String tenantId;

    /**
     * 默认字段
     */
    private String username;
    private String password;
    private Boolean enabled;
    private Collection<SimpleGrantedAuthority> authorities;

    private Set<String> perms;

    /**
     * 系统管理用户
     */
    public SysUserDetails(UserVO user) {
        this.setUserId(user.getId());
        this.setUsername(user.getUsername());
        this.setDeptId(user.getDeptId());
        this.setDataScope(user.getDataScope());
        this.setTenantId(user.getTenantId());
        this.setPassword(PasswordEncoderTypeEnum.BCRYPT.getPrefix() + user.getPassword());
        this.setEnabled(user.getStatus().getValue() == StatusEnum.ENABLE.getValue());
        if (CollectionUtil.isNotEmpty(user.getRoles())) {
            authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        this.setPerms(user.getPerms());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
