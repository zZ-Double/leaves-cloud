import axios from 'axios'
import { localStorage } from '@/utils/storage';
import useStore from '@/store'

// 创建 axios 实例
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 50000,
  headers: { 'Content-Type': 'application/json;charset=utf-8' },
})

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    if (!config.headers) {
      throw new Error(
        `Expected 'config' and 'config.headers' not to be undefined`,
      )
    }
    const { user } = useStore()
    if (user.token) {
      config.headers.Authorization = localStorage.get_token()
    }
    return config
  },
  (error: any) => {
    return Promise.reject(error)
  },
)

// 响应拦截器
service.interceptors.response.use((response) => {

  console.log(JSON.stringify(response.data))

  const { code, msg } = response.data
  if (code === '20000') {
    return response.data
  } else {
    // 响应数据为二进制流处理(Excel导出)
    if (response.data instanceof ArrayBuffer) {
      return response
    }

    ElMessage.error(msg || '系统出错')
    
    return Promise.reject(new Error(msg || 'Error'))
  }
}, (error: any) => {

  debugger
  if (error.response.data) {
    const { code, msg } = error.response.data
    // token 过期,重新登录
    if (code === 'A0230') {
      ElMessageBox.confirm('当前页面已失效，请重新登录', 'Warning', {
        confirmButtonText: 'OK',
        type: 'warning',
      }).then(() => {
        localStorage.clear()
        window.location.href = '/'
      })
    } else {
      ElMessage.error(msg || '系统出错')
    }
  }
  return Promise.reject(error.message)
},
)

// 导出 axios 实例
export default service
