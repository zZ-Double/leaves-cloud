import App from '@/App.vue'
//引入模板的全局的样式
import '@/styles/index.scss'

// svg插件需要配置代码
import 'virtual:svg-icons-register'
// 引入路由
import router from './router'
// 引入路由守卫
import '@/permission';
// 引入pinia
import { createPinia } from 'pinia'
// 引入自定义指令
import { setupDirective } from '@/directive';

// 获取应用实例对象
const app = createApp(App)
// 全局注册 自定义指令(directive)
setupDirective(app);

app
  .use(router)
  .use(createPinia())
  .mount('#app')
