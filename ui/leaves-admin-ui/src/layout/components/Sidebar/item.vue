<template>
    <div v-if="!item.meta || !item.meta.hidden">
        <!-- 没有子菜单 -->
        <template v-if="hasOneShowingChild(item.children, item) &&
            (!onlyOneChild.children || onlyOneChild.noShowingChildren) &&
            (!item.meta || !item.meta.alwaysShow)">

            <!-- 组件：判断是外部还是内部链接 -->
            <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
                <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{ 'submenu-title-noDropdown': !isNest }">
                    <svg-icon v-if="onlyOneChild.meta && onlyOneChild.meta.icon" :icon-class="onlyOneChild.meta.icon" />
                    <template #title>{{ onlyOneChild.meta.title }}</template>
                </el-menu-item>
            </app-link>
        </template>

        <!-- 有子菜单 -->
        <el-sub-menu v-else :index="resolvePath(item.path)" popper-append-to-body>
            <template #title>
                <svg-icon v-if="item.meta && item.meta.icon" :icon-class="item.meta.icon"></svg-icon>
                <span v-if="item.meta && item.meta.title">{{ item.meta.title }}</span>
            </template>
            <!-- 重复调用这个组件 -->
            <Item v-for="child in item.children" :key="child.path" :item="child" :is-nest="true"
                :base-path="resolvePath(child.path)" class="nest-menu" />
        </el-sub-menu>
    </div>
</template>

<!-- 循环引用自己，定义组件名称 -->
<script lang="ts">
export default {
    name: 'Item'
}
</script>
  
<script setup lang="ts">
import { ref } from 'vue';
import path from 'path-browserify';
import { isExternal } from '@/utils/validate';
import AppLink from './link.vue';
import SvgIcon from '@/components/SvgIcon/index.vue';

const props = defineProps({
    item: {
        type: Object,
        required: true,
    },
    isNest: {
        type: Boolean,
        required: false,
    },
    basePath: {
        type: String,
        required: true,
    },
});

const onlyOneChild = ref();

function hasOneShowingChild(children = [] as any, parent: any) {
    if (!children) {
        children = [];
    }
    const showingChildren = children.filter((item: any) => {
        if (item.meta && item.meta.hidden) {
            return false;
        } else {
            // Temp set(will be used if only has one showing child)
            onlyOneChild.value = item;
            return true;
        }
    });

    // When there is only one child router, the child router is displayed by default
    if (showingChildren.length === 1) {
        return true;
    }

    // Show parent if there are no child router to display
    if (showingChildren.length === 0) {
        onlyOneChild.value = { ...parent, path: '', noShowingChildren: true };
        return true;
    }

    return false;
}

function resolvePath(routePath: string) {
    if (isExternal(routePath)) {
        return routePath;
    }
    if (isExternal(props.basePath)) {
        return props.basePath;
    }
    return path.resolve(props.basePath, routePath);
}
</script>
  