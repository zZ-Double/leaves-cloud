<template>
    <div class="app-container">
        <div class="search-container">
            <el-form ref="queryFormRef" :model="queryParams" :inline="true">
                <el-form-item label="关键字" prop="keywords">
                    <el-input v-model="queryParams.keywords" placeholder="菜单名称" clearable @keyup.enter="handleQuery" />
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

        <el-card shadow="never" size="small">
            <template #header>
                <el-button v-hasPerm="['sys:menu:add']" type="success" @click="handleAdd">
                    <template #icon><i-ep-plus /></template>新增</el-button>
            </template>

            <el-table v-loading="loading" :data="menuList" highlight-current-row
                :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" row-key="id" default-expand-all border
                size="small" @row-click="onRowClick">
                <el-table-column label="菜单名称" min-width="200">
                    <template #default="scope">
                        <svg-icon :icon-class="scope.row.type === MenuTypeEnum.BUTTON ? 'button' : scope.row.icon" />
                        {{ scope.row.name }}
                    </template>
                </el-table-column>

                <el-table-column label="类型" align="center" width="80">
                    <template #default="scope">
                        <el-tag v-if="scope.row.type === MenuTypeEnum.CATALOG" type="warning">目录</el-tag>
                        <el-tag v-if="scope.row.type === MenuTypeEnum.MENU" type="success">菜单</el-tag>
                        <el-tag v-if="scope.row.type === MenuTypeEnum.BUTTON" type="danger">按钮</el-tag>
                        <el-tag v-if="scope.row.type === MenuTypeEnum.EXTLINK" type="info">外链</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="路由路径" align="left" width="150" prop="path" />

                <el-table-column label="组件路径" align="left" width="250" prop="component" />

                <el-table-column label="权限标识" align="center" width="200" prop="perm" />

                <el-table-column label="状态" align="center" width="80">
                    <template #default="scope">
                        <el-tag v-if="scope.row.status === 1" type="success">显示</el-tag>
                        <el-tag v-else type="info">隐藏</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="排序" align="center" width="80" prop="sort" />

                <el-table-column fixed="right" align="center" label="操作" width="220">
                    <template #default="scope">
                        <el-button v-if="scope.row.type == 'CATALOG' || scope.row.type == 'MENU'"
                            v-hasPerm="['sys:menu:add']" type="primary" link size="small"
                            @click.stop="handleAdd(scope.row.id)">
                            <i-ep-plus />新增
                        </el-button>

                        <el-button v-hasPerm="['sys:menu:edit']" type="primary" link size="small"
                            @click.stop="handleUpdate(scope.row.id)">
                            <i-ep-edit />编辑
                        </el-button>
                        <el-button v-hasPerm="['sys:menu:delete']" type="primary" link size="small"
                            @click.stop="handleDelete(scope.row.id)">
                            <i-ep-delete />删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>

        <el-dialog v-model="dialog.visible" :title="dialog.title" destroy-on-close append-to-body width="750px"
            @close="closeDialog">
            <el-form ref="dataFormRef" :model="formData" :rules="rules" label-width="100px">
                <el-form-item label="父级菜单" prop="parentId">
                    <el-tree-select v-model="formData.parentId" placeholder="选择上级菜单" :data="state.menuOptions" filterable
                        check-strictly :render-after-expand="false" />
                </el-form-item>

                <el-form-item label="菜单名称" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入菜单名称" />
                </el-form-item>

                <el-form-item label="菜单类型" prop="type">
                    <el-radio-group v-model="formData.type" @change="onMenuTypeChange">
                        <el-radio label="CATALOG">目录</el-radio>
                        <el-radio label="MENU">菜单</el-radio>
                        <el-radio label="BUTTON">按钮</el-radio>
                        <el-radio label="EXTLINK">外链</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item v-if="formData.type == 'EXTLINK'" label="外链地址" prop="path">
                    <el-input v-model="formData.path" placeholder="请输入外链完整路径" />
                </el-form-item>

                <el-form-item v-if="formData.type == MenuTypeEnum.CATALOG || formData.type == MenuTypeEnum.MENU"
                    label="路由路径" prop="path">
                    <el-input v-if="formData.type == MenuTypeEnum.CATALOG" v-model="formData.path" placeholder="system" />
                    <el-input v-else v-model="formData.path" placeholder="user" />
                </el-form-item>

                <!-- 组件页面完整路径 -->
                <el-form-item v-if="formData.type == MenuTypeEnum.MENU" label="页面路径" prop="component">
                    <el-input v-model="formData.component" placeholder="system/user/index" style="width: 95%">
                        <template v-if="formData.type == MenuTypeEnum.MENU" #prepend>src/views/</template>
                        <template v-if="formData.type == MenuTypeEnum.MENU" #append>.vue</template>
                    </el-input>
                </el-form-item>

                <!-- 权限标识 -->
                <el-form-item v-if="formData.type == 'BUTTON'" label="权限标识" prop="perm">
                    <el-input v-model="formData.perm" placeholder="sys:user:add" />
                </el-form-item>

                <el-form-item v-if="formData.type !== 'BUTTON'" label="图标" prop="icon">
                    <!-- 图标选择器 -->
                    <icon-select v-model="formData.icon" />
                </el-form-item>

                <el-form-item v-if="formData.type == MenuTypeEnum.CATALOG" label="跳转路由">
                    <el-input v-model="formData.redirect" placeholder="跳转路由" />
                </el-form-item>

                <el-form-item v-if="formData.type !== 'BUTTON'" label="状态">
                    <el-radio-group v-model="formData.status">
                        <el-radio :label="1">显示</el-radio>
                        <el-radio :label="0">隐藏</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model="formData.sort" style="width: 100px" controls-position="right" :min="0" />
                </el-form-item>
            </el-form>

            <template #footer>
                <div class="dialog-footer">
                    <el-button type="primary" @click="submitForm">确 定</el-button>
                    <el-button @click="closeDialog">取 消</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<script setup lang="ts">
// 依赖
import { MenuTypeEnum } from '@/enums/MenuTypeEnum'
import IconSelect from '@/components/IconSelect/index.vue'
import { menuOptions, saveMenu, listMenus } from '@/api/sys/menu/index'
import { Option, MenuForm, MenuQuery, MenuVO } from '@/api/sys/menu/types';
// 获取form组件
let dataFormRef = ref()
// 变量定义
const state = reactive({
    menuOptions: [] as Option[],
    currentRow: undefined
})
const queryParams = reactive({
    keywords: ''
})
const dialog = reactive({
    visible: false,
    title: ''
})
const formData: MenuForm = reactive({
    id: '',
    parentId: '',
    name: '',
    type: '',
    path: '',
    component: '',
    perm: '',
    icon: '',
    redirect: '',
    status: 1,
    sort: 0
})

let loading = ref(false)

const menuList = ref<MenuVO[]>([])

// 定义表单校验规则
const rules = reactive({
    parentId: [{ required: true, message: "请选择顶级菜单", trigger: "blur" }],
    name: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
    type: [{ required: true, message: "请选择菜单类型", trigger: "blur" }],
    path: [{ required: true, message: "请输入路由路径", trigger: "blur" }],
    component: [
        { required: true, message: "请输入组件完整路径", trigger: "blur" },
    ],
});

const menuCacheData = reactive({
    type: "",
    path: "",
});

// 搜索
function handleQuery() {
    // 重置父组件
    loading.value = true;
    listMenus(queryParams)
        .then(({ data }) => {
            menuList.value = data;
        })
        .then(() => {
            loading.value = false;
        });
}

// 重置
function resetQuery() {

}

/**
 * 新增菜单打开
 */
async function handleAdd(row: any) {
    formData.id = '';
    await loadMenuData();
    dialog.title = '添加菜单',
        dialog.visible = true

    if (row.id) {
        // 行点击新增
        formData.parentId = row.id;
    } else {
        // 工具栏新增
        if (state.currentRow) {
            // 选择行
            formData.parentId = (state.currentRow as any).id;
        } else {
            // 未选择行
            formData.parentId = '0';
        }
    }
}

/**
 * 编辑菜单
 */
async function handleUpdate(row: any) {
    //   await loadMenuData();
    //   state.dialog = {
    //     title: '编辑菜单',
    //     visible: true,
    //   };
    //   const id = row.id as string;
    //   getMenuDetail(id).then(({ data }) => {
    //     state.formData = data;
    //     cacheData.value.menuType = data.type;
    //     cacheData.value.menuPath = data.path;
    //   });
}

/** 关闭弹窗 */
function closeDialog() {
    dialog.visible = false;
    resetForm();
}

/** 重置表单 */
function resetForm() {
    dataFormRef.value.resetFields();
    dataFormRef.value.clearValidate();

    formData.id = undefined;
    formData.parentId = '0';
    formData.status = 1;
    formData.sort = 1;
    formData.perm = undefined;
    formData.component = undefined;
    formData.path = undefined;
    formData.redirect = undefined;
}

// 
function onRowClick() {

}

function handleDelete(menuId: string) {

}

function onMenuTypeChange() {
    // 如果菜单类型改变，清空路由路径；未改变在切换后还原路由路径
    if (formData.type !== menuCacheData.type) {
        formData.path = "";
    } else {
        formData.path = menuCacheData.path;
    }
}
/**
 * 表单提交
 */
function submitForm() {
    dataFormRef.value.validate((isValid: boolean) => {
        if (isValid) {
            //   if (state.formData.id) {
            //     updateMenu(state.formData.id, state.formData).then(() => {
            //       ElMessage.success('修改成功');
            //       cancel();
            //       handleQuery();
            //     });
            //   } else {
            saveMenu(formData).then(() => {
                ElMessage.success('新增成功');
                closeDialog();
                handleQuery();
            });
            //   }
        }
    });

}

/**
 * 加载菜单下拉树
 */
async function loadMenuData() {
    const menuOptionsList: any[] = [];
    await menuOptions().then(({ data }) => {
        const menuOption = { value: '0', label: '顶级菜单', children: data };
        menuOptionsList.push(menuOption);
        state.menuOptions = menuOptionsList;
    });
}

// 组件挂载
onMounted(() => {
    handleQuery()
})

</script>
<style scoped lang="scss"></style>