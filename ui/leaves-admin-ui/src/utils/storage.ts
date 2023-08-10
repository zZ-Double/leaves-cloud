/**
 * window.localStorage 浏览器永久缓存
 */
export const localStorage = {

  // 设置token
  set_token(token: string) {
    window.localStorage.setItem('Authorization', token);
  },
  // 获取token
  get_token() {
    return window.localStorage.getItem('Authorization');
  },
  // 移除token
  remove_token() {
    return window.localStorage.removeItem('Authorization');
  },

  // 设置永久缓存
  set(key: string, val: any) {
    window.localStorage.setItem(key, JSON.stringify(val));
  },
  // 获取永久缓存
  get(key: string) {
    const json: any = window.localStorage.getItem(key);
    return JSON.parse(json);
  },
  // 移除永久缓存
  remove(key: string) {
    window.localStorage.removeItem(key);
  },
  // 移除全部永久缓存
  clear() {
    window.localStorage.clear();
  },
};