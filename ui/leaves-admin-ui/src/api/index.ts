import request from '@/utils/request'
import { AxiosPromise } from 'axios';
import { FileInfo } from './types';


/** 
 * 文件上传
 */
export function uploadFile(formData: any): AxiosPromise<FileInfo> {
    return request({
        url: '/leaves-system/api/v1/files/upload?fileStorage=MINIO_STO',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    })
}