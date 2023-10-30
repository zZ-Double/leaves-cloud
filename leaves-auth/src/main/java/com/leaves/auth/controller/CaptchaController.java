package com.leaves.auth.controller;

import cn.hutool.core.util.IdUtil;
import com.leaves.auth.captcha.enums.CaptchaTypeEnum;
import com.leaves.auth.captcha.producer.CaptchaProducer;
import com.leaves.auth.captcha.properties.CaptchaProperties;
import com.leaves.common.constant.GlobalConstants;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Api(tags = "验证码管理")
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
@Slf4j
public class CaptchaController {

    private final CaptchaProducer captchaProducer;
    private final StringRedisTemplate stringRedisTemplate;
    private final CaptchaProperties properties;

    @ApiOperation("生成图形验证码")
    @GetMapping("/image")
    public Map<String, Object> imageHandle() {

        Map<String, Object> result = new HashMap<>(3);
        result.put("captchaOnOff", properties.getCaptchaOnOff());
        if (!properties.getCaptchaOnOff()) {
            return result;
        }

        Captcha captcha = captchaProducer.getCaptcha(properties.getType());
        String captchaValue = captcha.text();
        // 对于数学类型的需要进行处理
        if (Objects.isNull(properties.getType()) || properties.getType() == CaptchaTypeEnum.ARITHMETIC) {
            if (captcha.getCharType() - 1 == CaptchaTypeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
                captchaValue = captchaValue.split("\\.")[0];
            }
        }

        // 缓存验证码至Redis
        String uuid = IdUtil.simpleUUID();
        stringRedisTemplate.opsForValue().set(GlobalConstants.VERIFY_CODE_KEY_PREFIX + uuid, captchaValue, properties.getTtl(), TimeUnit.SECONDS);

        // 获取到验证码Base64编码字符串
        String captchaBase64 = captcha.toBase64();

        result.put("verifyCodeKey", uuid);
        result.put("verifyCodeImg", captchaBase64);

        return result;
    }
}
