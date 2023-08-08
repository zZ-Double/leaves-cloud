import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
const pathSrc = path.resolve(__dirname, "src")
//引入svg需要用到插件
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
//获取各种环境下的对应的变量
let env = loadEnv(mode, process.cwd());
  return {
    plugins: [vue(),
      createSvgIconsPlugin({
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
        symbolId: 'icon-[dir]-[name]',
      }),
      ],
      resolve: {
        alias: {
          "@": pathSrc // 相对路径别名配置，使用 @ 代替 src
        }
      },
      // 本地反向代理解决浏览器跨域限制
      server: {
        host: 'localhost',
        port: Number(env.VITE_APP_PORT),
        open: true, // 运行自动打开浏览器
        proxy: {
          [env.VITE_APP_BASE_API]: {
            //获取数据的服务器地址设置
            target: env.VITE_SERVE,
            //需要代理跨域
            changeOrigin: true,
            //路径重写
            rewrite: (path) =>
              path.replace(new RegExp('^' + env.VITE_APP_BASE_API), ''),
          },
        },
      },
  }

})
