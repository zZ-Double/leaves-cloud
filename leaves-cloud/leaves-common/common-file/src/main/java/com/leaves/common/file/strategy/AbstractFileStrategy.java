package com.leaves.common.file.strategy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.leaves.common.exception.BizException;
import com.leaves.common.file.enums.FileStorageEnum;
import com.leaves.common.file.model.FileInfo;
import com.leaves.common.file.utils.FileTypeUtils;
import com.leaves.common.file.utils.MimeTypeUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;

/**
 * 策略模板
 */
public abstract class AbstractFileStrategy implements FileStrategy {

    /**
     * 子类实现自己支持的存储类型
     */
    public abstract FileStorageEnum getFileStorage();

    public abstract FileInfo upload(MultipartFile file, String fileName);

    public abstract void download(String fileName);

    @Override
    public Boolean supports(FileStorageEnum fileStorage) {
        return fileStorage.equals(getFileStorage());
    }

    @Override
    public FileInfo uploadFile(MultipartFile file) {
        // 校验文件类型
        if (!isAllowedExtension(FileTypeUtils.getExtension(file))) {
            throw new BizException(StrUtil.format("文件类型不符合：{}，请重试", Arrays.toString(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION)));
        }
        // 生成文件名称
        String fileName = extractFilename(file);

        return upload(file, fileName);
    }

    @Override
    public void downloadFile(String fileName) {
        download(fileName);
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension 上传文件类型
     * @return true/false
     */
    private boolean isAllowedExtension(String extension) {
        for (String str : MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        return StrUtil.format("{}/{}_{}.{}", DateUtil.format(new Date(), "yyyy/MM/dd"),
                FilenameUtils.getBaseName(file.getOriginalFilename()), IdUtil.simpleUUID(), FileTypeUtils.getExtension(file));
    }
}
