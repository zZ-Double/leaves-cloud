<template>
  <div class="login-container">
    <el-form ref="loginFormRef" :model="loginForm" class="login-form" auto-complete="on"
      label-position="left">
      <div class="title-container">
        <h3 class="title">后台管理</h3>
      </div>
      <el-form-item prop="userName">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input v-model="loginForm.userName" name="username" type="text" tabindex="1" auto-complete="on"/>
      </el-form-item>
      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input v-model="loginForm.password" name="password" type="password" tabindex="1" auto-complete="on"/>
      </el-form-item>
      <!-- 验证码 -->
      <el-form-item prop="code">
        <span class="svg-container">
          <svg-icon icon-class="valid_code" />
        </span>
        <el-input
          v-model="loginForm.verifyCode"
          auto-complete="off"
          style="width: 65%"
        />

        <div class="captcha">
          <img
            :src="verifyCodeImgUrl"
            height="38px"
          />
        </div>
      </el-form-item>
      <el-button
        size="default"
        type="primary"
        style="width: 100%; margin-bottom: 30px"
        >登录
      </el-button>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from 'vue'
import SvgIcon from '@/components/SvgIcon/index.vue'

let loginFormRef = ref()
//收集账号与密码的数据
let loginForm = reactive({ userName: 'admin', password: '123456', verifyCode: '' })
let verifyCodeImgUrl = ref('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHgAAAAkCAIAAADNSmkJAAADVklEQVR42u3Y30uTURgH8P0JXXTRfV1254V4YUqRKGRXEninF774o+mamV4oLVZ6YRBJFw1Nc2ogOpLBktpUUsTAH9HyBzN1YrgYTq3pyunm6Xt6RWh7tfed75yL5+Eg7pxXd/jw8JznvBpGcSqhIQKCJmgKgiZogqYgaIKmIOhjY+Tjl7x7zy9k3dWkCOcyddnapx22D3uhMEGrGQ1t/SB+0uX46t3AR/eqr806mik8vnzL8HlhlaBVy2UoAzd6CUmNpbXNrURDLy+z5mZWXs5KSphez1pbmcdzFuxy7zxDBZD5MCoGcvmoVWOLrcDwMoY9WGYtVe+q0lvTU5tT8VP/Vu/yuWKCdjiYIEQOiE9NJZDYs/b94QsblOVDI2fFiiEZyPTz1/Qx7CTFlBIxID7pmVQIPT/PWbVaNjjIgkE+4/Wy9nY+WVbGfL7EHN9/fA+H/L864QOSUdhXaF+0B0McZ+XHSkV/BawLXhcohG5q4qbDw5HzJhOf7+2NHUuYwDhN6KMKtBhO9+Il/c2czhwwZXVkad9oh9xDMextO7gtJrVCaKQtQMNR3c/SEp83GhMCHSEuv0ajx5Bc2vy1ecV0/eojwRfwhcIhJGaXswvodYN1+KhoS+H9MKAz2jIUQqMWA3R/P3IeM2L1SB5otBbo5CSXHjjqL97Oi+jwArsBVIDu6W5FW+qb6wN0w0iDQujaWg46MyOxJB6JyQONWwn6ZXAfFD9B88/h9DpzX+XK349t3pbWkpbdkb3+c10htNXKQSsr2dgY29s7yGWXi1ksSZfRiDn3N7QW901W/CIHGkcc0lPOf55dmy2yFuHh/N58z5ZHeXu3u8saGyXaO3HU1CiVPX7EG5qXY3+guL7z8Ap+Q/cMFQOn38L6QvTDmJSZ0WJjZ5owoUbHemFBCtvt/NwrLWXFxRzXbGbT0xwavUeyQUsGegzzJ7PELea9EXbyoeNwBQe9ZNt3tkvHXw2qjNKBdPYH/YmDxp1Fp+NjZ+f/hl71q/mmSQn0xgYbGOCvO5DO4+Mn+tZEQ0cHOjmkMBITP1ExZOayqtARZyCK9ejoSb9VvZuh0itiPEKl0lFdzY9B+BoMrKdHlVccBE0Rt3dhREDQBE1B0ARN0BQETdAUiuM3Y+w4iESZY5EAAAAASUVORK5CYII=')

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
    width: 85%;

    .el-input__wrapper {
      padding: 0;
      background: transparent;
      box-shadow: none;

      .el-input__inner {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
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
