<template>
    <div class="app-container">
        <el-row :gutter="20">
            <!-- 部门树 -->
            <el-col :lg="4" :xs="24" class="mb-[12px]">
                <el-card shadow="never">
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

                <el-card shadow="never">
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

                    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
                        <el-table-column type="selection" width="50" align="center" />
                        <el-table-column key="username" label="用户名" align="center" prop="username" />
                        <el-table-column label="用户昵称" width="120" align="center" prop="nickName" />

                        <el-table-column label="性别" width="100" align="center" prop="sex" />

                        <el-table-column label="部门" width="120" align="center" prop="deptName" />
                        <el-table-column label="手机号码" align="center" prop="phoneNumber" width="120" />

                        <el-table-column label="状态" align="center" prop="status">
                            <template #default="scope">
                                <el-switch v-model="scope.row.status" inactive-value="DISABLE" active-value="ENABLE"
                                    @change="handleStatusChange(scope.row)" />
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

    </div>
</template>

<script setup lang="ts">
import { Option } from '@/api/sys/menu/types';
import { deptOptions } from '@/api/sys/dept';
import { UserForm, UserQuery, UserType } from "@/api/sys/user/types";
import { userPages } from '@/api/sys/user';


let searchDeptName = ref()
let deptList = ref<Option[]>([])
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

}

function openDialog(id?: any) {

}

function handleDelete(id?: any) {

}

function downloadTemplate() {

}

function openImportDialog() {

}

function handleUserExport() {

}


function handleSelectionChange() {

}


function handleStatusChange(row: any) {

}


function resetPassword(id: any) {

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