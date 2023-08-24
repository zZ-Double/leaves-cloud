<template>
    <div class="app-container">
        <el-row :gutter="20">
            <!-- 部门树 -->
            <el-col :lg="4" :xs="24" class="mb-[12px]">
                <el-card shadow="never" size="small">
                    <el-input v-model="searchDeptName" placeholder="部门名称" clearable>
                        <template #prefix>
                            <i-ep-search />
                        </template>
                    </el-input>

                    <el-tree v-slot="{ data }" ref="deptTreeRef" style="margin-top: 0.5rem" :data="deptList"
                        :props="{ children: 'children', label: 'label', disabled: '' }" :expand-on-click-node="false"
                        :filter-node-method="handleDeptFilter" default-expand-all @node-click="handleDeptNodeClick">
                        <span v-if="data.label.length <= 10">{{ data.label }}</span>
                        <span v-else>
                            <el-tooltip class="box-item" effect="dark" :content="data.label" placement="top"><span>{{
                                ellipsis(data.label, 10) }}</span>
                            </el-tooltip>
                        </span>
                    </el-tree>
                </el-card>
            </el-col>

            <!-- 用户表单 -->
            <el-col :lg="20" :xs="24">
                <div class="search-container">
                    <el-form ref="queryFormRef" :model="queryParams" :inline="true">
                        <el-form-item label="关键字" prop="keywords">
                            <el-input v-model="queryParams.keywords" placeholder="用户名/昵称/手机号" clearable style="width: 200px"
                                @keyup.enter="handleQuery" />
                        </el-form-item>

                        <el-form-item label="状态" prop="status">
                            <el-select v-model="queryParams.status" placeholder="全部" clearable style="width: 200px">
                                <el-option label="启用" value="ENABLE" />
                                <el-option label="禁用" value="DISABLE" />
                            </el-select>
                        </el-form-item>

                        <el-form-item>
                            <el-button type="primary" @click="handleQuery"><i-ep-search />搜索</el-button>
                            <el-button @click="resetQuery"><i-ep-refresh />重置</el-button>
                        </el-form-item>
                    </el-form>
                </div>

                <el-card shadow="never" size="small">
                    <template #header>
                        <div style="justify-content: space-between;display: flex;">
                            <div>
                                <el-button v-hasPerm="['sys:user:add']" type="success"
                                    @click="openDialog()"><i-ep-plus />新增</el-button>
                                <el-button v-hasPerm="['sys:user:delete']" type="danger" :disabled="ids.length === 0"
                                    @click="handleDelete()"><i-ep-delete />删除</el-button>
                            </div>
                            <div>
                                <el-dropdown split-button>
                                    导入
                                    <template #dropdown>
                                        <el-dropdown-menu>
                                            <el-dropdown-item @click="downloadTemplate">
                                                <i-ep-download />下载模板</el-dropdown-item>
                                            <el-dropdown-item @click="openImportDialog">
                                                <i-ep-top />导入数据</el-dropdown-item>
                                        </el-dropdown-menu>
                                    </template>
                                </el-dropdown>
                                <el-button class="ml-3" @click="handleUserExport"><template
                                        #icon><i-ep-download /></template>导出</el-button>
                            </div>
                        </div>
                    </template>

                    <el-table v-loading="loading" size="small" :data="userList" @selection-change="handleSelectionChange">
                        <el-table-column type="selection" width="50" align="center" />
                        <el-table-column key="username" label="用户名" align="center" prop="username" />
                        <el-table-column label="用户昵称" width="120" align="center" prop="nickName" />

                        <el-table-column label="性别" width="100" align="center" prop="sex">
                            <template #default="scope">
                                <el-tag v-if="scope.row.sex == 'MALE'" type="">男</el-tag>
                                <el-tag v-else-if="scope.row.sex == 'FEMALE'" type="success">女</el-tag>
                                <el-tag v-else type="info">未知</el-tag>
                            </template>
                        </el-table-column>

                        <el-table-column label="部门" width="120" align="center" prop="deptName" />
                        <el-table-column label="手机号码" align="center" prop="phoneNumber" width="120" />

                        <el-table-column label="状态" align="center" prop="status">
                            <template #default="scope">
                                <el-tag v-if="scope.row.status == 'ENABLE'" type="success">启用</el-tag>
                                <el-tag v-else type="danger">禁用</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
                        <el-table-column label="操作" fixed="right" width="220">
                            <template #default="scope">
                                <el-button v-hasPerm="['sys:user:reset_pwd']" type="primary" size="small" link
                                    @click="resetPassword(scope.row)"><i-ep-refresh-left />重置密码</el-button>
                                <el-button v-hasPerm="['sys:user:edit']" type="primary" link size="small"
                                    @click="openDialog(scope.row.id)"><i-ep-edit />编辑</el-button>
                                <el-button v-hasPerm="['sys:user:delete']" type="primary" link size="small"
                                    @click="handleDelete(scope.row.id)"><i-ep-delete />删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                    <pagination v-if="total > 0" v-model:total="total" v-model:page="queryParams.current"
                        v-model:limit="queryParams.size" @pagination="handleQuery" />
                </el-card>
            </el-col>
        </el-row>

        <!-- 表单弹窗 -->
        <el-dialog v-model="state.visible" :title="state.title" width="600px" append-to-body @close="closeDialog">
            <el-form ref="dataFormRef" :model="formData" :rules="rules" label-width="80px">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="formData.username" :readonly="!!formData.id" placeholder="请输入用户名" />
                </el-form-item>

                <el-form-item label="用户昵称" prop="nickName">
                    <el-input v-model="formData.nickName" placeholder="请输入用户昵称" />
                </el-form-item>

                <el-form-item label="手机号码" prop="phoneNumber">
                    <el-input v-model="formData.phoneNumber" placeholder="请输入手机号码" maxlength="11" />
                </el-form-item>

                <el-form-item label="所属部门" prop="deptId">
                    <el-tree-select v-model="formData.deptId" placeholder="请选择所属部门" :data="deptList" filterable
                        check-strictly :render-after-expand="false" />
                </el-form-item>

                <el-form-item label="状态" prop="status">
                    <el-radio-group v-model="formData.status">
                        <el-radio label="ENABLE">启用</el-radio>
                        <el-radio label="DISABLE">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="formData.sex">
                        <el-radio label="MALE">男</el-radio>
                        <el-radio label="FEMALE">女</el-radio>
                        <el-radio label="UN_KNOW">未知</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="角色" prop="roleIds">
                    <el-select v-model="formData.roleIds" multiple placeholder="请选择">
                        <el-option v-for="item in roleList" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>

                <el-form-item label="数据权限" prop="dataScope">
                    <el-select v-model="formData.dataScope">
                        <el-option :key="1" label="全部数据" :value="1" />
                        <el-option :key="2" label="部门及子部门数据" :value="2" />
                        <el-option :key="3" label="本部门数据" :value="3" />
                        <el-option :key="4" label="本人数据" :value="4" />
                    </el-select>
                </el-form-item>

                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="formData.email" placeholder="请输入邮箱" maxlength="50" />
                </el-form-item>
                
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="handleSubmit">确 定</el-button>
                    <el-button @click="closeDialog">取 消</el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script setup lang="ts">
import { Option } from '@/api/sys/menu/types';
import { deptOptions } from '@/api/sys/dept';
import { roleOptions } from '@/api/sys/role'
import { getUser, removeUser, updateUser, saveUser } from '@/api/sys/user'
import { UserForm, UserQuery, UserType } from "@/api/sys/user/types";
import { userPages } from '@/api/sys/user';

let queryFormRef = ref()
let dataFormRef = ref()
let searchDeptName = ref()
let deptList = ref<Option[]>([])
let roleList = ref<Option[]>([])
let loading = ref(false)
let total = ref(0)
let ids = ref<string[]>([])
let userList = ref<UserType[]>([])


const queryParams = reactive<UserQuery>({
    keywords: '',
    status: '',
    deptId: '',
    current: 1,
    size: 10
})

const state = reactive({
    visible: false,
    title: ''
})

const formData = reactive<UserForm>({
    id: undefined,
    username: '',
    nickName: '',
    phoneNumber: '',
    sex: 'MALE',
    email: '',
    status: 'ENABLE',
    deptId: '',
    remark: '',
    roleIds: [],
    dataScope: undefined
})

const rules = reactive({
    username: [{ required: true, message: "用户名不能为空", trigger: "blur" }],
    nickName: [{ required: true, message: "用户昵称不能为空", trigger: "blur" }],
    deptId: [{ required: true, message: "所属部门不能为空", trigger: "blur" }],
    roles: [{ required: true, message: "用户角色不能为空", trigger: "blur" }],
    email: [
        {
            pattern: /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/,
            message: "请输入正确的邮箱地址",
            trigger: "blur",
        },
    ],
    phoneNumber: [
        {
            required: true,
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
        },
    ],
});



// 部门下拉
function handleDeptOptions() {
    deptOptions().then(({ data }) => {
        deptList.value = data
    })
}
// 部门筛选
function handleDeptFilter(value: string, data: any) {
    if (!value) {
        return true;
    }
    return data.label.indexOf(value) !== -1;
}
// 部门树节点
function handleDeptNodeClick(data: { [key: string]: any }) {
    queryParams.deptId = data.value;
    handleQuery();
}


// 用户分页列表查询
function handleQuery() {
    loading.value = true
    userPages(queryParams).then(({ data }) => {
        userList.value = data.records
        total.value = data.total
    }).finally(() => {
        loading.value = false;
    })
}

function resetQuery() {
    queryFormRef.value.resetFields()
    handleQuery()
}

function openDialog(id?: any) {
    handleDeptOptions()
    roleOptopns()
    state.visible = true;
    if (id) {
        state.title = "修改用户";
        getUser(id).then(({ data }) => {
            Object.assign(formData, data);
        })
    } else {
        state.title = "新增用户";
    }
}

function closeDialog() {
    state.visible = false
    resetForm()
}

function handleDelete(id?: any) {
    const userIds = [id || ids.value].join(",");
    if (!userIds) {
        ElMessage.warning("请勾选删除项");
        return false;
    }

    ElMessageBox.confirm("确认删除已选中的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
    }).then(() => {
        removeUser(userIds).then(() => {
            ElMessage.success("删除成功");
            handleQuery();
        });
    }).catch(() => {
        ElMessage.info("已取消删除")
    }).finally(() => {
        loading.value = false;
    })
}

// 角色下拉选项
function roleOptopns() {
    roleOptions().then(({ data }) => {
        roleList.value = data
    })
}

function handleSubmit() {
    dataFormRef.value.validate((valid: any) => {
    if (valid) {
      const userId = formData.id;
      loading.value = true;
      if (userId) {
        updateUser(formData)
          .then(() => {
            ElMessage.success("修改用户成功");
            closeDialog();
            resetQuery();
          })
          .finally(() => (loading.value = false));
      } else {
        saveUser(formData).then(() => {
            ElMessage.success("新增用户成功");
            closeDialog();
            resetQuery();
          })
          .finally(() => (loading.value = false));
      }
    }
  });
}

// 重置表单
function resetForm() {

    dataFormRef.value.resetFields();
    dataFormRef.value.clearValidate();

    formData.id = undefined
    formData.username = ''
    formData.nickName = ''
    formData.phoneNumber = '',
    formData.sex = 'MALE',
    formData.email = '',
    formData.deptId = '',
    formData.remark = '',
    formData.roleIds = [],
    formData.status = 'ENABLE'
}

function handleUserExport() {

}

function handleSelectionChange() {

}

function resetPassword(id: any) {

}

function downloadTemplate() {

}

function openImportDialog() {

}

// 树结构节点文字超出处理
const ellipsis = (value: string, len: number) => {
    if (!value) return ''
    if (value.length > len) {
        return value.slice(0, len) + '...'
    }
    return value
}

onMounted(() => {
    handleDeptOptions()
    handleQuery()
})

</script>
<style scoped lang="scss"></style>