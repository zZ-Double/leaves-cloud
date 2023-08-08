import { defineStore } from 'pinia'
import { PermissionState } from './types';

const usePermissionStore = defineStore({
    id: 'permission',
    state: (): PermissionState => ({
        routes: [],
        addRoutes: [],
    }),
    actions: {
        
    }
})

export default usePermissionStore;