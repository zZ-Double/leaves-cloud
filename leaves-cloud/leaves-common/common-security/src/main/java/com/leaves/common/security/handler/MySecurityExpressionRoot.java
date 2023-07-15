package com.leaves.common.security.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.leaves.common.constant.GlobalConstants;
import com.leaves.common.security.utils.SecurityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.util.AntPathMatcher;

import java.util.Set;

/**
 * 重写权限标识验证逻辑
 */
public class MySecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private RedisTemplate redisTemplate;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * Creates a new instance
     *
     * @param authentication the {@link Authentication} to use. Cannot be null.
     * @param redisTemplate
     */
    public MySecurityExpressionRoot(Authentication authentication, RedisTemplate redisTemplate) {
        super(authentication);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断当前对象是否具备某一个权限
     *
     * @param perm
     * @return
     */
    public boolean hasPerm(String perm) {

        if (StrUtil.isBlank(perm)) {
            return false;
        }

        if (SecurityUtils.isRoot()) {
            return true;
        }

        String userId = SecurityUtils.getUserId();

        //获取当前登录用户所具有的权限
        Set<String> perms = (Set<String>) redisTemplate.opsForValue().get(GlobalConstants.AUTH_PREFIX + userId);

        if (CollectionUtil.isEmpty(perms)) {
            return false;
        }

        return perms.stream().anyMatch(item -> antPathMatcher.match(perm, item));
    }

    /**
     * 是否具备多个权限中的任意一个权限
     *
     * @param permissions
     * @return
     */
    @SuppressWarnings("DuplicatedCode")
    public boolean hasAnyPerm(String... permissions) {

        if (StrUtil.isAllBlank(permissions)) {
            return false;
        }

        if (SecurityUtils.isRoot()) {
            return true;
        }

        String userId = SecurityUtils.getUserId();

        Set<String> perms = (Set<String>) redisTemplate.opsForValue().get(GlobalConstants.AUTH_PREFIX + userId);
        for (String perm : perms) {
            for (String permission : permissions) {
                if (antPathMatcher.match(perm, permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否具备多个权限中的所有权限
     *
     * @param permissions
     * @return
     */
    @SuppressWarnings("DuplicatedCode")
    public boolean hasAllPerm(String... permissions) {

        if (StrUtil.isAllBlank(permissions)) {
            return false;
        }

        if (SecurityUtils.isRoot()) {
            return true;
        }

        String userId = SecurityUtils.getUserId();

        Set<String> perms = (Set<String>) redisTemplate.opsForValue().get(GlobalConstants.AUTH_PREFIX + userId);

        for (String permission : permissions) {
            boolean flag = false;
            for (String perm : perms) {
                if (antPathMatcher.match(perm, permission)) {
                    flag = true;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
