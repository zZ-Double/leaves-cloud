<template>
    <div class="app-container">
        <div class="search-container">
            <el-form ref="queryFormRef" :model="queryParams" :inline="true">
                <el-form-item label="关键字" prop="keywords">
                    <el-input v-model="queryParams.keywords" placeholder="菜单名称" clearable @keyup.enter="handleQuery" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="handleQuery" :icon="Search">搜索</el-button>
                    <el-button @click="resetQuery" :icon="Refresh">重置</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-card shadow="never" size="small">
            <template #header>
                <el-button :icon="Plus" v-hasPerm="['sys:menu:add']" type="success" @click="openDialog(0)">新增</el-button>
            </template>

            <el-table v-loading="loading" :data="menuList" highlight-current-row :tree-props="{
                children: 'children',
                hasChildren: 'hasChildren',
            }" row-key="id" default-expand-all border size="small" @row-click="onRowClick">
                <el-table-column label="菜单名称" min-width="200">
                    <template #default="scope">
                        <svg-icon :icon-class="scope.row.type === MenuTypeEnum.BUTTON
                                ? 'button'
                                : scope.row.icon
                            " />
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
                        <el-tag v-if="scope.row.visible === 1" type="success">显示</el-tag>
                        <el-tag v-else type="info">隐藏</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="排序" align="center" width="80" prop="sort" />
            </el-table>
        </el-card>
    </div>
</template>

<script setup lang="ts">
// 依赖
import { ref, reactive } from 'vue';
import { MenuTypeEnum } from '@/enums/MenuTypeEnum'
import { Search, Refresh, Plus } from '@element-plus/icons-vue';

// 变量定义
const queryParams = reactive({
    keywords: ''
})
let loading = ref(false)

const menuList = reactive([])

// 搜索
function handleQuery() {

}

// 重置
function resetQuery() {

}

// 新增
function openDialog(param:any) {
    console.log(param)

}

// 
function onRowClick() {

}

</script>
<style scoped lang="scss"></style>