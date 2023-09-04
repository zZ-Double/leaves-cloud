<template>
    <div class="app-container">
        <el-row :gutter="20">
            <el-col :span="6" :xs="24">
                <el-card class="box-card">
                    <template #header>
                        <div class="clearfix">
                            <span>个人信息</span>
                        </div>
                    </template>
                    <div>
                        <div class="text-center">
                            <userAvatar :user="state.user" />
                        </div>
                        <ul class="list-group list-group-striped">
                            <li class="list-group-item">
                                <i-ep-user-filled />用户名称
                                <div class="pull-right">{{ state.user.nickName }}</div>
                            </li>
                            <li class="list-group-item">
                                <i-ep-phone-filled />手机号码
                                <div class="pull-right">{{ state.user.phoneNumber }}</div>
                            </li>
                            <li class="list-group-item">
                                <i-ep-promotion />用户邮箱
                                <div class="pull-right">{{ state.user.email }}</div>
                            </li>
                            <li class="list-group-item">
                                <i-ep-home-filled />所属部门
                                <div class="pull-right">{{ state.user.deptName }}</div>
                            </li>
                            <li class="list-group-item">
                                <i-ep-avatar />所属角色
                                
                                <div class="pull-right">{{ state.user.roleNames }}</div>
                            </li>
                            <li class="list-group-item">
                                <i-ep-calendar />创建日期
                                <div class="pull-right">{{ state.user.createTime }}</div>
                            </li>
                        </ul>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="18" :xs="24">
                <el-card>
                    <template #header>
                        <div class="clearfix">
                            <span>基本资料</span>
                        </div>
                    </template>
                    <el-tabs v-model="activeTab">
                        <el-tab-pane label="基本资料" name="userinfo">
                            <userInfo :user="state.user" />
                        </el-tab-pane>
                        <el-tab-pane label="修改密码" name="resetPwd">
                            <!-- <resetPwd /> -->
                        </el-tab-pane>
                    </el-tabs>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script setup lang="ts">
import { userProfile } from '@/api/sys/profile/index'
import userAvatar from '../profile/userAvatar.vue'
import userInfo from '../profile/userInfo.vue'

const activeTab = ref("userinfo");

const state = reactive({
    user: {
        nickName: '',
        avatar: '',
        phoneNumber: '',
        email: '',
        deptName: '',
        roleNames: '',
        createTime: ''

    }
})

function getUserProfile() {
    userProfile().then(({data}) => {
        state.user = data
    })
}

onMounted(() => {
    getUserProfile()
})
</script>
<style scoped lang="scss">
.text-center {
    text-align: center;
}

.list-group-striped>.list-group-item {
    border-left: 0;
    border-right: 0;
    border-radius: 0;
    padding-left: 0;
    padding-right: 0;
}

.list-group {
    padding-left: 0px;
    list-style: none;
}

.list-group-item {
    border-bottom: 1px solid #e7eaec;
    border-top: 1px solid #e7eaec;
    margin-bottom: -1px;
    padding: 11px 0px;
    font-size: 13px;

    svg {
        width: 2em;
        height: 1em;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
    }
}

.pull-right {
    float: right !important;
}</style>