package com.leaves.common.file.strategy;

import com.leaves.common.file.enums.FileStorageEnum;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/8/30
 */

public interface FileStrategy {

    /**
     * 支持的文件存储类型
     */
    Boolean supports(FileStorageEnum fileStorage);

    /**
     * 上传
     */
    String uploadFile(MultipartFile file);

    /**
     * 下载
     */
    void downloadFile(String fileName);


}
