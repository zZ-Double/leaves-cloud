import useUserStore from './modules/user'
import usePermissionStore from './modules/permission'
import useTagsViewStore from './modules/tagsView'
import useAppStore from './modules/app'
import useSettingStore from './modules/setting'

const useStore = () => ({
  app: useAppStore(),
  user: useUserStore(),
  permission: usePermissionStore(),
  tagsView: useTagsViewStore(),
  setting: useSettingStore(),
})

export default useStore
