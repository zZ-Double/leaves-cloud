import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { sys_base_url } from '..';
import { UserProfile } from './types';

export function userProfile(): AxiosPromise<UserProfile> {
    return request({
        url: sys_base_url + 'user/profile',
        method: 'get',
    });
}

export function userAvatar(avatarUrl: string) {
    return request({
        url: sys_base_url + 'user/profile/avatar',
        method: 'get',
        params: avatarUrl
    });
}

export function userInfo(nickName?: string, phoneNumber?: string, email?: string, sex?: string) {
    const data = {
        nickName,
        phoneNumber,
        email,
        sex
    }
    return request({
        url: sys_base_url + 'user/profile/info',
        method: 'post',
        data
    });
}

export function userPasswd(oldPasswd: string, newPasswd: string, confirmPasswd: string) {
    const data = {
        oldPasswd,
        confirmPasswd,
        newPasswd
    }
    return request({
        url: sys_base_url + 'user/profile/passwd',
        method: 'post',
        data
    });
}