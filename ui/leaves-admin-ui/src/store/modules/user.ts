import { defineStore } from 'pinia'
import { UserState } from './types'

import { localStorage } from '@/utils/storage';
import { loginApi, logoutApi } from '@/api/auth'
import { getUserInfo } from '@/api/sys/user';
import { LoginForm } from '@/api/auth/types'
import { resetRouter } from '@/router'

const useUserStore = defineStore({
  id: 'user',
  state: (): UserState => ({
    token: localStorage.get_token() || '',
    nickname: '',
    avatar: '',
    roles: [],
    perms: [],
  }),
  actions: {
    async RESET_STATE() {
      this.$reset();
    },
    /**
     *  登陆
     */
    login(data: LoginForm) {
      const { username, password, grant_type, verifyCode, verifyCodeKey } = data
      return new Promise((resolve, reject) => {
        loginApi({
          username: username.trim(),
          password: password,
          grant_type: grant_type,
          verifyCode: verifyCode,
          verifyCodeKey: verifyCodeKey,
        })
          .then((response) => {
            const { access_token, token_type } = response.data
            const accessToken = token_type + ' ' + access_token
            localStorage.set_token(accessToken)
            this.token = accessToken
            resolve(access_token)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    /**
     *  获取用户信息（昵称、头像、角色集合、权限集合）
     */
    getUserInfo() {
      return new Promise((resolve, reject) => {
        getUserInfo()
          .then(({ data }) => {
            if (!data) {
              return reject('Verification failed, please Login again.');
            }
            const { nickname, avatar, roles, perms } = data;
            if (!roles || roles.length <= 0) {
              reject('getUserInfo: roles must be a non-null array!');
            }
            this.nickname = nickname;
            this.avatar = avatar;
            this.roles = roles;
            this.perms = perms;
            resolve(data);
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    /**
     *  注销
     */
    logout() {
      return new Promise((resolve, reject) => {
        logoutApi()
          .then(() => {
            localStorage.clear();
            this.RESET_STATE();
            resetRouter();
            resolve(null);
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    /**
     * 清除 Token
     */
    resetToken() {
      return new Promise((resolve) => {
        localStorage.remove_token();
        this.RESET_STATE();
        resolve(null);
      });
    },
  },
})

export default useUserStore
