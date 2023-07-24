package com.leaves.common.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leaves.common.result.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Objects;

/**
 * 全局统一返回
 */
@Slf4j
@RestControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 排除swagger/knife4j
        if ("ApiResourceController".equals(methodParameter.getDeclaringClass().getSimpleName())) {
            return false;
        }

        // 方法级别忽略
        if (methodParameter.getMethod().isAnnotationPresent(ApiIgnore.class)) {
            return false;
        }

        // 类级别忽略
        if (methodParameter.getDeclaringClass().isAnnotationPresent(ApiIgnore.class)) {
            return false;
        }

        // 如果接口返回的类型本身就是ApiRestResponse那就没有必要进行额外的操作，返回false
        //methodParameter.getGenericParameterType() 连同范型<T>一起获取，会导致始终不等于ApiRestResponse 除非controller中只返回ApiRestResponse类型 而非形如ApiRestResponse<SysUser>
        return !methodParameter.getParameterType().equals(Result.class);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (Objects.isNull(o)) {
            return Result.failed();
        }
        // String类型不能直接包装，所以要进行些特别的处理
        if (methodParameter.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(Result.success(o));
        }
        // 将原本的数据包装
        return Result.success(o);
    }
}
