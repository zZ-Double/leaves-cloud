<template>
    <div class="app-container">
        <div class="search-container">
            <el-form>
                <el-form ref="queryFormRef" :model="queryParams" :inline="true">
                    <el-form-item label="关键字" prop="keywords">
                        <el-input v-model="queryParams.keywords" placeholder="角色名称" clearable @keyup.enter="handleQuery" />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="handleQuery">
                            <template #icon><i-ep-search /></template><!-- 自动导入图标使用方法 -->
                            搜索
                        </el-button>
                        <el-button @click="resetQuery">
                            <template #icon><i-ep-refresh /></template>
                            重置
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-form>
        </div>

        <el-card>
            <template #header>
                <el-button v-hasPerm="['sys:menu:add']" type="success" @click="openDialog()">
                    <template #icon><i-ep-plus /></template>新增
                </el-button>
                <el-button v-hasPerm="['sys:menu:add']" type="danger" :disabled="ids.length === 0" @click="handleDelete()">
                    <template #icon><i-ep-delete /></template>删除
                </el-button>
            </template>

            <el-table v-loading="loading" :data="roleList" row-key="id" default-expand-all
                :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" border size="small"
                @selection-change="handleSelectionChange">

                <el-table-column :selectable="handleDisable" type="selection" width="55" align="center" />
                <el-table-column prop="roleName" label="角色名称" min-width="100" />
                <el-table-column prop="roleCode" label="角色编码" min-width="80" />
                <el-table-column prop="status" label="状态" width="100">
                    <template #default="scope">
                        <el-tag v-if="scope.row.status == 'ENABLE'" type="success">正常</el-tag>
                        <el-tag v-else type="info">禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" fixed="right" align="left" width="220">
                    <template #default="scope">
                        <div v-if="scope.row.roleCode !== 'ROOT'">
                            <el-button v-hasPerm="['sys:dept:add']" type="primary" link size="small"
                                @click.stop="openMenuDialog(scope.row)"><i-ep-position />分配权限
                            </el-button>
                            <el-button v-hasPerm="['sys:dept:edit']" type="primary" link size="small"
                                @click.stop="openDialog(scope.row.id)"><i-ep-edit />编辑
                            </el-button>
                            <el-button v-hasPerm="['sys:dept:delete']" type="primary" link size="small"
                                @click.stop="handleDelete(scope.row.id)"><i-ep-delete />删除
                            </el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>

            <Pagination v-if="total > 0" v-model:total="total" v-model:page="queryParams.current"
                v-model:limit="queryParams.size" @pagination="handleQuery" />
        </el-card>

        <!-- 角色表单弹窗 -->
        <el-dialog v-model="state.visible" :title="state.title" width="500px" @close="closeDialog">
            <el-form ref="dataFormRef" :model="formData" :rules="rules" label-width="100px">
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="formData.roleName" placeholder="请输入角色名称" />
                </el-form-item>

                <el-form-item label="角色编码" prop="roleCode">
                    <el-input v-model="formData.roleCode" placeholder="请输入角色编码" />
                </el-form-item>

                <el-form-item label="数据权限" prop="dataScope">
                    <el-select v-model="formData.dataScope">
                        <el-option :key="0" label="全部数据" :value="0" />
                        <el-option :key="1" label="部门及子部门数据" :value="1" />
                        <el-option :key="2" label="本部门数据" :value="2" />
                        <el-option :key="3" label="本人数据" :value="3" />
                    </el-select>
                </el-form-item>

                <el-form-item label="状态" prop="status">
                    <el-radio-group v-model="formData.status">
                        <el-radio label="ENABLE">正常</el-radio>
                        <el-radio label="DISABLE">停用</el-radio>
                    </el-radio-group>
                </el-form-item>

            </el-form>

            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="handleSubmit">确 定</el-button>
                    <el-button @click="closeDialog">取 消</el-button>
                </div>
            </template>
        </el-dialog>

        <!-- 分配菜单弹窗  -->
        <el-dialog v-model="state.menuDialogVisible" :title="'【' + state.roleName + '】权限分配'" width="800px">
            <el-scrollbar v-loading="loading" max-height="600px">
                <el-tree ref="menuRef" node-key="value" show-checkbox :data="menuList" :default-expand-all="true">
                    <template #default="{ data }">
                        {{ data.label }}
                    </template>
                </el-tree>
            </el-scrollbar>

            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="handleRoleMenuSubmit">确 定</el-button>
                    <el-button @click="state.menuDialogVisible = false">取 消</el-button>
                </div>
            </template>
        </el-dialog>

    </div>
</template>

<script setup lang="ts">
import Pagination from '@/components/Pagination/index.vue'
import { Query, RoleVO } from '@/api/sys/role/types';
import { pageRoles, saveRole, updateRole, removeRole, getRole, getRoleMenuIds, updateRoleMenus } from '@/api/sys/role/index'
import { Option } from '@/api/sys/menu/types';
import { menuOptions } from '@/api/sys/menu';

let queryFormRef = ref()
let dataFormRef = ref()
let menuRef = ref()


let loading = ref(false)
let ids = ref<string[]>([])
let roleList = ref<RoleVO[]>([])
let total = ref(0)
let menuList = ref<Option[]>([])

const state = reactive({
    visible: false,
    title: '',
    roleId: '',
    roleName: '',
    menuDialogVisible: false,
    menuDialogTitle: ''

})

const queryParams = reactive<Query>({
    keywords: '',
    current: 1,
    size: 10
})

const formData = reactive<RoleVO>({
    id: '',
    roleCode: '',
    roleName: '',
    status: 'ENABLE',
    dataScope: 0
})

const rules = reactive({
    roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
    roleCode: [{ required: true, message: "请输入角色编码", trigger: "blur" }],
    dataScope: [{ required: true, message: "请选择数据权限", trigger: "blur" }],
    status: [{ required: true, message: "请选择状态", trigger: "blur" }],
});


function handleQuery() {
    loading.value = true
    pageRoles(queryParams)
        .then(({ data }) => {
            roleList.value = data.records
            total.value = data.total
        }).finally(() => {
            loading.value = false;
        })
}

function resetQuery() {
    queryFormRef.value.resetFields()
    handleQuery()
}

function openDialog(id?: string) {
    state.visible = true;
    if (id) {
        state.title = "修改角色";
        getRole(id).then(({ data }) => {
            Object.assign(formData, data);
        })
    } else {
        state.title = "新增角色";
    }
}

function closeDialog() {
    state.visible = false
    resetForm()
}

function handleDelete(id?: string) {
    const roleIds = [id || ids.value].join(",");
    if (!roleIds) {
        ElMessage.warning("请勾选删除项");
        return false;
    }

    ElMessageBox.confirm("确认删除已选中的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
    }).then(() => {
        removeRole(roleIds).then(() => {
            ElMessage.success("删除成功");
            handleQuery();
        });
    }).catch(() => {
        ElMessage.info("已取消删除")
    }).finally(() => {
        loading.value = false;
    })
}

function handleSelectionChange(selection: any) {
    ids.value = selection.map((item: any) => item.id);
}

function handleSubmit() {
    dataFormRef.value.validate((isValid: boolean) => {
        if (isValid) {
            if (formData.id) {
                updateRole(formData).then(() => {
                    ElMessage.success('修改成功');
                    closeDialog();
                    handleQuery();
                }).finally(() => {
                    loading.value = false;
                })
            } else {
                saveRole(formData).then(() => {
                    ElMessage.success('新增成功');
                    closeDialog();
                    handleQuery();
                }).finally(() => {
                    loading.value = false;
                })
            }
        }
    });
}

// 重置表单
function resetForm() {

    dataFormRef.value.resetFields();
    dataFormRef.value.clearValidate();

    formData.id = undefined
    formData.roleName = ''
    formData.roleCode = ''
    formData.status = 'ENABLE'
}

/** 打开分配菜单弹窗 */
function openMenuDialog(row: RoleVO) {
    const id = row.id;
    if (id) {
        state.roleId = id
        state.roleName = row.roleName
        state.menuDialogVisible = true;
        loading.value = true;

        // 获取所有的菜单
        menuOptions().then(({ data }) => {
            menuList.value = data
            // 回显角色已拥有的菜单
            getRoleMenuIds(id).then(({ data }) => {
                const checkedMenuIds = data
                console.log("勾选权限", checkedMenuIds)
                checkedMenuIds.forEach((menuId) =>
                    menuRef.value.setChecked(menuId, true, false)
                )
            }).finally(() => {
                loading.value = false;
            })
        });
    }
}

function handleRoleMenuSubmit() {
    const roleId = state.roleId;
    if (roleId) {
        const checkedMenuIds: string = menuRef.value.getCheckedNodes(false, true).map((node: any) => node.value)
        loading.value = true;
        updateRoleMenus(roleId, checkedMenuIds).then(() => {
            ElMessage.success("分配权限成功");
            state.menuDialogVisible = false;
            resetQuery();
        }).finally(() => {
            loading.value = false;
        })
    }
}

function handleDisable(row:RoleVO, index:number) {
    if (row.roleCode === 'ROOT') {
        return false
    } else {
        return true
    }
}

onMounted(() => {
    handleQuery()
})

</script>
<style scoped lang="scss"></style>