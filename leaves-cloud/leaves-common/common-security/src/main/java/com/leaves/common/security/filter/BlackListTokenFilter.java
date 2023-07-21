package com.leaves.common.security.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leaves.common.constant.GlobalConstants;
import com.leaves.common.result.Result;
import com.leaves.common.result.ResultCode;
import com.nimbusds.jose.JWSObject;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import reactor.core.publisher.Mono;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/21
 */

public class BlackListTokenFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;

    public BlackListTokenFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 过滤jwt-Token是否在黑名单中
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头
        String token = request.getHeader(GlobalConstants.AUTH_REQUEST_HEARD);
        if (StrUtil.isBlank(token)) {
            filterChain.doFilter(request, response);
        }
        token = token.replace(GlobalConstants.JWT_TOKEN_PREFIX, Strings.EMPTY);
        JWSObject jwsObject = JWSObject.parse(token);
        String payload = jwsObject.getPayload().toString();

        // 黑名单token(登出、修改密码)校验
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        String jti = jsonObject.getStr("jti"); // JWT唯一标识

        Boolean isBlack = redisTemplate.hasKey(GlobalConstants.BLACKLIST_TOKEN_PREFIX + jti);
        if (isBlack) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), Result.failed(ResultCode.INVALID_TOKEN));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
