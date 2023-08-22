<template>
    <div class="app-container">
        <div class="search-container">
            <el-form ref="queryFormRef" :inline="true" :model="queryParams">
                <el-form-item label="部门名称" prop="deptName">
                    <el-input v-model="queryParams.deptName" placeholder="部门名称" clearable @keyup.enter="handleQuery" />
                </el-form-item>
                <el-form-item label="部门负责人" prop="leader">
                    <el-input v-model="queryParams.leader" placeholder="部门负责人" clearable @keyup.enter="handleQuery" />
                </el-form-item>
                <el-form-item label="部门状态" prop="status">
                    <el-select v-model="queryParams.status" placeholder="部门状态" clearable>
                        <el-option value="ENABLE" label="正常" />
                        <el-option value="DISABLE" label="禁用" />
                    </el-select>
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
        </div>

        <el-card>
            <template #header>
                <el-button v-hasPerm="['sys:menu:add']" type="success" @click="openDialog('0', undefined)">
                    <template #icon><i-ep-plus /></template>新增
                </el-button>
                <el-button v-hasPerm="['sys:menu:add']" type="danger" :disabled="ids.length === 0" @click="handleDelete()">
                    <template #icon><i-ep-delete /></template>删除
                </el-button>
            </template>

            <el-table v-loading="loading" :data="deptList" row-key="id" default-expand-all
                :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" border size="small"
                @selection-change="handleSelectionChange">

                <el-table-column type="selection" width="55" align="center" />
                <el-table-column prop="deptName" label="部门名称" min-width="200" />
                <el-table-column prop="status" label="状态" width="100">
                    <template #default="scope">
                        <el-tag v-if="scope.row.status == 'ENABLE'" type="success">正常</el-tag>
                        <el-tag v-else type="info">禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="orderNum" label="排序" width="100" />
                <el-table-column label="操作" fixed="right" align="left" width="200">
                    <template #default="scope">
                        <el-button v-hasPerm="['sys:dept:add']" type="primary" link size="small"
                            @click.stop="openDialog(scope.row.id, undefined)"><i-ep-plus />新增
                        </el-button>
                        <el-button v-hasPerm="['sys:dept:edit']" type="primary" link size="small"
                            @click.stop="openDialog(scope.row.parentId, scope.row.id)"><i-ep-edit />编辑
                        </el-button>
                        <el-button v-hasPerm="['sys:dept:delete']" type="primary" link size="small"
                            @click.stop="handleDelete(scope.row.id)"><i-ep-delete />删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <el-dialog v-model="state.visible" :title="state.title" destroy-on-close append-to-body width="750px"
            @close="closeDialog">

            <el-form ref="dataFormRef" :model="formData" :rules="rules" label-width="100px">

                <el-form-item label="上级部门" prop="parentId">
                    <el-tree-select v-model="formData.parentId" placeholder="选择上级部门" :data="deptOptionArr" filterable
                        check-strictly :render-after-expand="false" />
                </el-form-item>

                <div style="display: flex;">
                    <el-form-item label="部门名称" prop="deptName" style="flex: 1;">
                        <el-input v-model="formData.deptName" placeholder="请输入部门名称" />
                    </el-form-item>
                    <el-form-item label="部门负责人" prop="leader" style="flex: 1;">
                        <el-input v-model="formData.leader" placeholder="请输入部门负责人" />
                    </el-form-item>
                </div>

                <div style="display: flex;">
                    <el-form-item label="联系电话" prop="phone" style="flex: 1;">
                        <el-input v-model="formData.phone" placeholder="请输入联系电话" />
                    </el-form-item>
                    <el-form-item label="邮箱" prop="email" style="flex: 1;">
                        <el-input v-model="formData.email" placeholder="请输入邮箱" />
                    </el-form-item>
                </div>

                <div style="display: flex;">
                    <el-form-item label="显示排序" prop="orderNum" style="flex: 1;">
                        <el-input-number v-model="formData.orderNum" controls-position="right" style="width: 100px"
                            :min="0" />
                    </el-form-item>
                    <el-form-item label="部门状态" style="flex: 1;">
                        <el-radio-group v-model="formData.status">
                            <el-radio label="ENABLE">正常</el-radio>
                            <el-radio label="DISABLE">禁用</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </div>

            </el-form>

            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="handleSubmit"> 确 定 </el-button>
                    <el-button @click="closeDialog"> 取 消 </el-button>
                </div>
            </template>

        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { DeptForm, DeptVO } from '@/api/sys/dept/types'
import { Option } from '@/api/sys/menu/types';
import { listDepts, deptOptions, removeDept, updateDept, saveDept, getDept } from '@/api/sys/dept/index'

// 获取form组件
let dataFormRef = ref()
let queryFormRef = ref()

// 临时变量
let loading = ref(false)
// 存储勾选的列表
let ids = ref<string[]>([])

const state = reactive({
    title: '',
    visible: false
})

// 查询参数
const queryParams = reactive({
    deptName: '',
    leader: '',
    status: ''
})
// 表格数据
const deptList = ref<DeptVO[]>([])

// dialog下拉数据
let deptOptionArr = reactive<Option[]>([])

// form Data
const formData: DeptForm = reactive({
    id: '',
    parentId: '',
    deptName: '',
    orderNum: 0,
    status: 'ENABLE',
    email: '',
    leader: '',
    phone: ''
})

// 表单校验
const rules = reactive({
    parentId: [{ required: true, message: "上级部门不能为空", trigger: "blur" }],
    deptName: [{ required: true, message: "部门名称不能为空", trigger: "blur" }],
    orderNum: [{ required: true, message: "显示排序不能为空", trigger: "blur" }],
});

// 查询数据列表
function handleQuery() {
    loading.value = true;
    listDepts(queryParams)
        .then(({ data }) => {
            deptList.value = data;
        })
        .then(() => {
            loading.value = false;
        });
}

/** 重置搜索条件 */
function resetQuery() {
    queryFormRef.value.resetFields()
    handleQuery()
}

// 打开弹窗
async function openDialog(parentId?: string, id?: string) {
    const deptOptionsList: any[] = [];
    deptOptions().then(({ data }) => {
        const deptOption = { value: '0', label: '顶级部门', children: data };
        deptOptionsList.push(deptOption);
        deptOptionArr = deptOptionsList;
    }).then(() => {
        state.visible = true;
        if (id) {
            state.title = "修改部门";
            getDept(id).then(({ data }) => {
                Object.assign(formData, data);
            });
        } else {
            state.title = "新增部门";
            formData.parentId = parentId ?? '0';
        }
    })
}

// 关闭弹窗
function closeDialog() {
    state.visible = false;
    resetForm();
}

// 删除
function handleDelete(id?: string) {
    const deptIds = [id || ids.value].join(",");
    if (!deptIds) {
        ElMessage.warning("请勾选删除项");
        return false;
    }

    ElMessageBox.confirm("确认删除已选中的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
    }).then(() => {
        removeDept(deptIds).then(() => {
            ElMessage.success("删除成功");
            handleQuery();
        });
    }).catch(() => ElMessage.info("已取消删除"));

}

/** 行复选框选中记录选中ID集合 */
function handleSelectionChange(selection: any) {
    ids.value = selection.map((item: any) => item.id);
}

// 表单提交
function handleSubmit() {
    dataFormRef.value.validate((isValid: boolean) => {
        if (isValid) {
            if (formData.id) {
                updateDept(formData).then(() => {
                    ElMessage.success('修改成功');
                    closeDialog();
                    handleQuery();
                });
            } else {
                saveDept(formData).then(() => {
                    ElMessage.success('新增成功');
                    closeDialog();
                    handleQuery();
                });
            }
        }
    });
}

// 重置表单
function resetForm() {

    dataFormRef.value.resetFields();
    dataFormRef.value.clearValidate();

    formData.id = undefined
    formData.parentId = '0'
    formData.deptName = ''
    formData.orderNum = 1
    formData.status = 'ENABLE'
    formData.email = undefined
    formData.leader = undefined
    formData.phone = undefined
}

// 组件挂载加载数据列表
onMounted(() => {
    handleQuery()
})
</script>
<style scoped lang="scss"></style>