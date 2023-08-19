<template>
    <div class="app-container">
        <div class="search-container">
            <el-form ref="queryFormRef" :inline="true">
                <el-form-item label="关键字" prop="keywords">
                    <el-input placeholder="菜单名称" clearable />
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
                <el-table-column prop="name" label="部门名称" min-width="200" />
                <el-table-column prop="status" label="状态" width="100">
                    <template #default="scope">
                        <el-tag v-if="scope.row.status == 'ENABLE'" type="success">正常</el-tag>
                        <el-tag v-else type="info">禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="sort" label="排序" width="100" />
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

            <el-form ref="dataFormRef" label-width="100px">

                <el-form-item label="上级部门" prop="parentId">
                    <el-tree-select v-model="formData.parentId" placeholder="选择上级部门" :data="deptOptionArr" filterable
                        check-strictly :render-after-expand="false" />
                </el-form-item>
                <el-form-item label="部门名称" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入部门名称" />
                </el-form-item>
                <el-form-item label="显示排序" prop="sort">
                    <el-input-number v-model="formData.sort" controls-position="right" style="width: 100px" :min="0" />
                </el-form-item>
                <el-form-item label="部门状态">
                    <el-radio-group v-model="formData.status">
                        <el-radio label="ENABLE">正常</el-radio>
                        <el-radio label="DISABLE">禁用</el-radio>
                    </el-radio-group>
                </el-form-item>

            </el-form>

        </el-dialog>
    </div>
</template>

<script setup lang="ts">
import { DeptVO } from '@/api/sys/dept/types'
import { Option } from '@/api/sys/menu/types';
import { listDepts, deptOptions } from '@/api/sys/dept/index'

// 获取form组件
let dataFormRef = ref()

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
    keywords: ''
})
// 表格数据
const deptList = ref<DeptVO[]>([])

// dialog下拉数据
let deptOptionArr = reactive<Option[]>([])

// form Data
const formData = reactive({
    parentId: '',
    name: '',
    sort: 0,
    status: 'ENABLE'
})

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
    dataFormRef.value.resetFields()
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
            // getDeptForm(deptId).then(({ data }) => {
            //   Object.assign(formData, data);
            // });
        } else {
            state.title = "新增部门";
            formData.parentId = parentId ?? '0';
        }
    })
}

// 关闭弹窗
function closeDialog() {

}

// 删除
function handleDelete(id?: string) {

}

/** 行复选框选中记录选中ID集合 */
function handleSelectionChange(selection: any) {
    ids.value = selection.map((item: any) => item.id);
}

// 组件挂载加载数据列表
onMounted(() => {
    handleQuery()
})
</script>
<style scoped lang="scss"></style>