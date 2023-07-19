package com.leaves.auth.extension.refresh;

import com.leaves.auth.config.AuthorizationServerConfig;
import com.leaves.auth.util.RequestUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 刷新token再次认证 UserDetailsService
 */
@NoArgsConstructor
public class PreAuthenticatedUserDetailsService<T extends Authentication> implements AuthenticationUserDetailsService<T>, InitializingBean {

    /**
     * 客户端ID和用户服务 UserDetailService 的映射
     *
     * @see AuthorizationServerConfig#tokenServices(AuthorizationServerEndpointsConfigurer)
     */
    private Map<String, UserDetailsService> userDetailsServiceMap;

    public PreAuthenticatedUserDetailsService(Map<String, UserDetailsService> userDetailsServiceMap) {
        Assert.notNull(userDetailsServiceMap, "userDetailsService cannot be null.");
        this.userDetailsServiceMap = userDetailsServiceMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsServiceMap, "UserDetailsService must be set");
    }

    /**
     * 重写PreAuthenticatedAuthenticationProvider 的 preAuthenticatedUserDetailsService 属性，可根据客户端和认证方式选择用户服务 UserDetailService 获取用户信息 UserDetail
     *
     * @param authentication
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserDetails(T authentication) throws UsernameNotFoundException {
        String clientId = RequestUtils.getClientId();
        // 获取认证身份标识，默认是用户名:username
        UserDetailsService userDetailsService = userDetailsServiceMap.get(clientId);
//        if (clientId.equals(GlobalConstants.APP_CLIENT_ID)) {
//            // 移动端的用户体系是会员，认证方式是通过手机号 mobile 认证
//            MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
//            return memberUserDetailsService.loadUserByUsername(authentication.getName());
//        } else if (clientId.equals(GlobalConstants.WX_APP_CLIENT_ID)) {
//            // 小程序的用户体系是会员，认证方式是通过微信三方标识 openid 认证
//            MemberUserDetailsServiceImpl memberUserDetailsService = (MemberUserDetailsServiceImpl) userDetailsService;
//            return memberUserDetailsService.loadUserByOpenId(authentication.getName());
//        } else if (clientId.equals(GlobalConstants.ADMIN_CLIENT_ID)) {
//            // 管理系统的用户体系是系统用户，认证方式通过用户名 username 认证
//            return userDetailsService.loadUserByUsername(authentication.getName());
//        } else {
//            return userDetailsService.loadUserByUsername(authentication.getName());
//        }

        return userDetailsService.loadUserByUsername(authentication.getName());
    }
}
