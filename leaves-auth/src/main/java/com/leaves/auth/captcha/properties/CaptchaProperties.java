package com.leaves.auth.captcha.properties;

import com.leaves.auth.captcha.enums.CaptchaTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证码 配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "captcha")
public class CaptchaProperties {

	/**
	 * 是否开启验证码
	 */
	private Boolean captchaOnOff;

	/**
	 * 验证码类型
 	 */
    private CaptchaTypeEnum type;

	/**
	 * 过期时间
	 */
	private long ttl;
}
