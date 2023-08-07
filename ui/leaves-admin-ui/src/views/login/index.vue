<template>
  <div class="login-container">
    <el-form ref="loginFormRef" :model="loginForm" class="login-form" auto-complete="on" label-position="left">
      <!-- 管理后台名称 -->
      <div class="title-container">
        <h3 class="title">后台管理</h3>
      </div>

      <!-- 用户名 -->
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input v-model="loginForm.username" name="username" type="text" tabindex="1" auto-complete="on" />
      </el-form-item>

      <!-- 密码 -->
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input v-model="loginForm.password" name="password" type="password" tabindex="1" auto-complete="on"
          show-password />
      </el-form-item>

      <!-- 验证码 -->
      <el-form-item prop="code" v-if="state.captchaOnOff">
        <span class="svg-container">
          <svg-icon icon-class="valid_code" />
        </span>
        <el-input v-model="loginForm.verifyCode" auto-complete="off" style="width: 65%" />
        <div class="captcha">
          <img :src="state.verifyCodeImgUrl" height="38px" @click="handleCaptchaGenerate" />
        </div>
      </el-form-item>

      <!-- 登录按钮 -->
      <el-button @click="handleLogin" :loading="state.loading" size="default" type="primary"
        style="width: 100%; margin-bottom: 30px">登录</el-button>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref, reactive, watch } from 'vue'

// 组件依赖
import { useRouter, useRoute } from 'vue-router'
import SvgIcon from '@/components/SvgIcon/index.vue'

// 依赖
import useStore from '@/store'
import { getCaptcha } from '@/api/auth'

const { user } = useStore()

//获取路由器
let $router = useRouter()
//路由对象
let $route = useRoute()
// 获取form组件
let loginFormRef = ref()

// 数据定义
const state = reactive({
  redirect: '',
  otherQuery: {},
  loading: false,
  captchaOnOff: false, // 是否显示验证码
  verifyCodeImgUrl: '', //验证码url
})

const loginForm = reactive({
  username: 'admin', // 账户
  password: '123456', // 密码
  verifyCode: '', // 验证码
  verifyCodeKey: '', // 验证码key
  grant_type: '',
})

// 获取验证码
function handleCaptchaGenerate() {
  getCaptcha().then(({ data }) => {
    const { captchaOnOff, verifyCodeImg, verifyCodeKey } = data
    state.captchaOnOff = captchaOnOff
    loginForm.verifyCodeKey = verifyCodeKey
    state.verifyCodeImgUrl = verifyCodeImg
  })
}

// 登录
function handleLogin() {
  loginFormRef.value.validate((valid: boolean) => {
    if (valid) {
      state.loading = true
      user
        .login(loginForm)
        .then(() => {
          $router.push({ path: state.redirect || '/', query: state.otherQuery })
          state.loading = false
        })
        .catch(() => {
          state.loading = false
          handleCaptchaGenerate()
        })
    } else {
      return false
    }
  })
}
// 判断登录的组件URL：是否有query参数【即为用户未登录时候，想去而没有去成的路由】
function getOtherQuery(query: any) {
  return Object.keys(query).reduce((acc: any, cur: any) => {
    if (cur !== 'redirect') {
      acc[cur] = query[cur]
    }
    return acc
  }, {})
}
// 组件挂载加载方法
onMounted(() => {
  handleCaptchaGenerate()
})
// 监听登录方式
watch(
  () => state.captchaOnOff,
  () => {
    if (state.captchaOnOff) {
      loginForm.grant_type = 'captcha'
    } else {
      loginForm.grant_type = 'password'
    }
  },
  {
    immediate: true,
  },
)
// 监听路由跳转
watch(
  $route,
  () => {
    const query = $route.query
    if (query) {
      state.redirect = query.redirect as string
      state.otherQuery = getOtherQuery(query)
    }
  },
  {
    immediate: true,
  },
)
</script>

<style lang="scss">
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

/* reset element-ui css */
.login-container {
  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .el-input {
    display: inline-block;
    height: 36px;
    width: 90%;

    .el-input__wrapper {
      padding: 0;
      width: 100%;
      background: transparent;
      box-shadow: none;

      .el-input__inner {
        background: transparent;
        border: 0px;
        // -webkit-appearance: none;
        border-radius: 0px;
        color: $light_gray;
        height: 36px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }
  }

  .el-input__inner {
    &:hover {
      border-color: var(--el-input-hover-border, var(--el-border-color-hover));
      box-shadow: none;
    }

    box-shadow: none;
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }

  .copyright {
    width: 100%;
    position: absolute;
    bottom: 0;
    font-size: 12px;
    text-align: center;
    color: #cccccc;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 5px 10px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .captcha {
    position: absolute;
    right: 0;
    top: 0;

    img {
      height: 42px;
      cursor: pointer;
      vertical-align: middle;
    }
  }
}
</style>
