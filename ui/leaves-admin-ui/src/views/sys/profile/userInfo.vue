<template>
  <el-form ref="userRef" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="用户昵称" prop="nickName">
      <el-input v-model="user.nickName" maxlength="30" />
    </el-form-item>
    <el-form-item label="手机号码" prop="phoneNumber">
      <el-input v-model="user.phoneNumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="性别" prop="sex">
      <el-radio-group v-model="user.sex">
        <el-radio label="MALE">男</el-radio>
        <el-radio label="FEMALE">女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { userInfo } from "@/api/sys/profile";

const userRef = ref()

const props = defineProps({
  user: {
    type: Object,
    required: true
  }
})

// 感觉这行没啥卵用，但是可以去除ES报错 
const { user } = toRefs(props)

const rules = ref({
  nickName: [{ required: true, message: "用户昵称不能为空", trigger: "blur" }],
  email: [{ required: true, message: "邮箱地址不能为空", trigger: "blur" }, { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }],
  phoneNumber: [{ required: true, message: "手机号码不能为空", trigger: "blur" }, { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }],
});

/** 提交按钮 */
function submit() {
  userRef.value.validate(valid => {
    if (valid) {
      userInfo(props.user.nickName, props.user.phoneNumber, props.user.email, props.user.sex)
        .then(({ code }) => {
          if (code === '20000') {
            ElMessage.success("修改成功")
          } else {
            ElMessage.error("修改失败")
          }
        })
    }
  });
};

</script>
