import request from '@/utils/request'
import { AxiosPromise } from 'axios'
import { LoginForm, LoginResult, VerifyCode } from './types'

/**
 *
 * @param data {LoginForm}
 * @returns
 */
export function loginApi(data: LoginForm): AxiosPromise<LoginResult> {
  return request({
    url: '/leaves-auth/oauth/token',
    method: 'post',
    params: data,
    headers: {
      Authorization: 'Basic Y2xpZW50X3Rlc3Q6MTIzNDU2', // 客户端信息Base64明文：client_test:123456
    },
  })
}

/**
 * 注销
 */
export function logoutApi() {
  return request({
    url: '/leaves-auth/oauth/logout',
    method: 'delete',
  })
}

/**
 * 获取图片验证码
 */
export function getCaptcha(): AxiosPromise<VerifyCode> {
  return request({
    url: '/leaves-auth/captcha/image?t=' + new Date().getTime().toString(),
    method: 'get',
  })
}
