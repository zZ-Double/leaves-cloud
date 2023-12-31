package com.leaves.common.constant;

/**
 * 全局常量 接口的所有变量默认都是 public static 和 final 的
 */
public interface GlobalConstants {

    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    String GBK = "GBK";

    /**
     * 超级管理员角色编码
     */
    String ROOT_ROLE_CODE = "ROOT";

    /**
     * 角色前缀
     */
    String ROLE_PREFIX = "ROLE_";

    /**
     * 缓存权限前缀
     */
    String AUTH_PREFIX = "AUTH:USER_PERMS:";

    /**
     * 权限
     */
    String AUTHORITIES_CLAIM_NAME = "authorities";


    /**
     * Token 请求头
     */
    String AUTH_REQUEST_HEARD = "Authorization";

    /**
     * Token 前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * Jwt 唯一标识
     */
    String JWT_JTI = "jti";

    /**
     * Jwt 过期时间
     */
    String JWT_EXP = "exp";

    /**
     * Jwt 用户ID
     */
    String USER_ID = "userId";

    /**
     * 根部门ID
     */
    String ROOT_NODE_ID = "0";

    /**
     * 系统默认密码
     */
    String DEFAULT_USER_PASSWORD = "123456";

    /**
     * 黑名单TOKEN Key前缀
     */
    String BLACKLIST_TOKEN_PREFIX = "AUTH:BLACKLIST_TOKEN:";

    /**
     * 验证码key前缀
     */
    String VERIFY_CODE_KEY_PREFIX = "AUTH:VERIFY_CODE:";

    /**
     * 短信验证码key前缀
     */
    String SMS_CODE_PREFIX = "SMS_CODE:";

    /**
     * 接口文档 Knife4j 测试客户端ID
     */
    String TEST_CLIENT_ID = "client";

    /**
     * 系统管理 web 客户端ID
     */
    String ADMIN_CLIENT_ID = "client_test";

    /**
     * 移动端（H5/Android/IOS）客户端ID
     */
    String APP_CLIENT_ID = "mall-app";

    /**
     * 微信小程序客户端ID
     */
    String WX_APP_CLIENT_ID = "mall-wx-app";

}
