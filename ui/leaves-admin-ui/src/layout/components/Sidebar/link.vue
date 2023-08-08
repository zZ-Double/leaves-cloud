<template>
    <!-- 是外部链接 -->
    <a :href="to" v-if="isExternal(to)" target="_blank" rel="noopener">
        <slot />
    </a>
    <!-- 是内部链接 -->
    <div v-else @click="push">
        <slot />
    </div>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue";
import { useRouter } from "vue-router";
import { isExternal } from "@/utils/validate";
import useStore from '@/store';

const { app } = useStore();
//通过computed拿到store中的数据
const device = computed(() => app.device)
const sidebar = computed(() => app.sidebar)

export default defineComponent({
    props: {
        //从父组件获取要跳转的外部链接
        to: {
            type: String,
            required: true,
        }
    },
    setup(props) {
        const router = useRouter();
        //点击的是内部链接时
        const push = () => {
            if (device.value === 'mobile' && sidebar.value.opened == true) {
                app.closeSideBar(false);
            }
            //路由数组里添加路径
            router.push(props.to).catch((err) => {
                console.log(err);
            })
        }
        return {
            push,
            isExternal
        }
    }
})
</script>