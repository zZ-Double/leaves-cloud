package com.leaves.common.web.aspectj;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.leaves.common.annotation.OperaLog;
import com.leaves.common.constant.GlobalConstants;
import com.leaves.common.enums.StatusEnum;
import com.leaves.common.interfaces.OperaLogInterface;
import com.leaves.common.model.OperaLogModel;
import com.leaves.common.utils.AddressUtils;
import com.leaves.common.web.util.RequestUtils;
import io.swagger.models.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * 操作日志切面
 */
@Slf4j
@Aspect
public class LogAspect {


    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerOperaLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, OperaLog controllerOperaLog, Object jsonResult) {
        handleLog(joinPoint, controllerOperaLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerOperaLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OperaLog controllerOperaLog, Exception e) {
        handleLog(joinPoint, controllerOperaLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, OperaLog controllerOperaLog, final Exception e, Object jsonResult) {
        try {

            // *========数据库日志=========*//
            OperaLogModel operaLog = new OperaLogModel();

            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();

            // 获取当前的用户
            String payload = RequestUtils.getJwtPayload();
            JSONObject entries = JSONObject.parseObject(payload);
            String userId = (String) entries.get(GlobalConstants.USER_ID);
            if (StrUtil.isNotBlank(userId)) {
                operaLog.setOperationUser(userId);
            }

            operaLog.setStatus(StatusEnum.ENABLE.getValue());
            // 异常处理
            if (e != null) {
                operaLog.setStatus(StatusEnum.DISABLE.getValue());
                operaLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            // 请求的地址
            String ip = ServletUtil.getClientIP(request);
            operaLog.setOperationIp(ip);
            operaLog.setOperationUrl(request.getRequestURI());
            operaLog.setOperationLocation(AddressUtils.getRealAddressByIP(ip));
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operaLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operaLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerOperaLog, operaLog, jsonResult, request);
            log.info(operaLog.toString());
            // 保存数据库
            SpringUtil.getBean(OperaLogInterface.class).recordOperaLog(operaLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param operaLog      日志
     * @param operaLogModel 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, OperaLog operaLog, OperaLogModel operaLogModel, Object jsonResult,
                                               HttpServletRequest request) throws Exception {
        // 设置标题
        operaLogModel.setTitle(operaLog.title());
        // 是否需要保存request，参数和值
        if (operaLog.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operaLogModel, request);
        }
        // 是否需要保存response，参数和值
        if (operaLog.isSaveResponseData() && ObjectUtil.isNotNull(jsonResult)) {
            operaLogModel.setResult(StringUtils.substring(JSONObject.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operaLogModel 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, OperaLogModel operaLogModel, HttpServletRequest request) throws Exception {
        String requestMethod = operaLogModel.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operaLogModel.setOperationParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operaLogModel.setOperationParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (ObjectUtil.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        params.append(JSONObject.toJSONString(o)).append(" ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
