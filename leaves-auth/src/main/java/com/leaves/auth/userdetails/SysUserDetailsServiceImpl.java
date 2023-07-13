package com.leaves.auth.userdetails;

import cn.hutool.core.lang.Assert;
import com.leaves.system.api.SysUserProvider;
import com.leaves.system.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/13
 */
@Service("sysUserDetailsService")
@Slf4j
public class SysUserDetailsServiceImpl implements UserDetailsService {

    @DubboReference(check = false)
    private SysUserProvider userProvider;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            UserVO userVO = userProvider.getUserAuthInfo(userName).get().getData();
            Assert.isTrue(Objects.nonNull(userVO), "用户名或密码错误，请重新输入");

            SysUserDetails userDetails = new SysUserDetails(userVO);
            return userDetails;

        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("用户名或密码错误，请重新输入");
        }

    }
}
