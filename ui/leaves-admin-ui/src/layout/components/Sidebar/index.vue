<template>
    <div :class="{ 'has-logo': showLogo }">
        <!-- logo组件 -->>
        <Logo v-if="showLogo" :collapse="isCollapse" />

        <el-scrollbar wrap-class="scrollbar-wrapper">
            <el-menu :default-active="activeMenu" :collapse="isCollapse" :background-color="variables.menuBg"
                :text-color="variables.menuText" :active-text-color="variables.menuActiveText" :unique-opened="false"
                :collapse-transition="false" mode="vertical">
                <Item v-for="route in routes" :item="route" :key="route.path" :base-path="route.path"
                    :is-collapse="isCollapse" />
            </el-menu>
        </el-scrollbar>
    </div>
</template>
  
<script setup lang="ts">
// 依赖引入
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import Logo from './logo.vue'
import Item from './item.vue'
import useStore from '@/store';
import variables from '@/styles/variables.module.scss'

const { permission, setting, app } = useStore();

const route = useRoute();
const routes = computed(() => permission.routes);
const showLogo = computed(() => setting.sidebarLogo);
const isCollapse = computed(() => !app.sidebar.opened);

const activeMenu = computed(() => {
  const { meta, path } = route;
  // if set path, the sidebar will highlight the path you set
  if (meta.activeMenu) {
    return meta.activeMenu as string;
  }
  return path;
});


</script>
  