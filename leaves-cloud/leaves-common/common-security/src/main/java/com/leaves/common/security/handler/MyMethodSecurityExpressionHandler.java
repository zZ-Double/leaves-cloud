package com.leaves.common.security.handler;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * 设置自定义权限注解解析
 */
public class MyMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private RedisTemplate redisTemplate;

    public MyMethodSecurityExpressionHandler(RedisTemplate redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        MySecurityExpressionRoot root = new MySecurityExpressionRoot(authentication, redisTemplate);
        root.setTrustResolver(getTrustResolver());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }
}
