//封装本地存储存储数据与读取数据方法
//存储数据
export const SET_TOKEN = (token: string) => {
  localStorage.setItem('Authorization', token)
}
//本地存储获取数据
export const GET_TOKEN = () => {
  return localStorage.getItem('Authorization')
}
//本地存储删除数据方法
export const REMOVE_TOKEN = () => {
  localStorage.removeItem('Authorization')
}
//本地存储删除数据方法
export const CLEAR_ALL = () => {
  localStorage.clear()
}
