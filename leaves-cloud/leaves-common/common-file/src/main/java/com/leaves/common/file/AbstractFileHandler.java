package com.leaves.common.file;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.leaves.common.exception.BizException;
import com.leaves.common.file.enums.FileStorageEnum;
import com.leaves.common.file.properties.FileProperties;
import com.leaves.common.file.strategy.FileStrategy;
import com.leaves.common.file.utils.FileTypeUtils;
import com.leaves.common.file.utils.MimeTypeUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 策略模板
 */
@RequiredArgsConstructor
@ConditionalOnBean(FileProperties.class)
public abstract class AbstractFileHandler implements FileStrategy {

    private final FileProperties properties;

    /**
     * 子类实现自己支持的存储类型
     */
    public abstract FileStorageEnum getFileStorage();

    public abstract String upload(MultipartFile file, String fileName);

    public abstract void download(String fileName);

    @Override
    public Boolean supports(FileStorageEnum fileStorage) {
        return fileStorage.equals(getFileStorage());
    }

    @Override
    public String uploadFile(MultipartFile file) {
        // 校验文件类型、大小、文件名等
        Integer fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNameLength > properties.getFileNameLength()) {
            throw new BizException(StrUtil.format("文件名称长度超出：{}，请重试", properties.getFileNameLength()));
        }
        if (file.getSize() > properties.getFileMaxSize()) {
            throw new BizException(StrUtil.format("文件大小超出：{}，请重试", properties.getFileNameLength()));
        }
        if (!isAllowedExtension(FileTypeUtils.getExtension(file))) {
            throw new BizException(StrUtil.format("文件类型不符合：{}，请重试", Arrays.toString(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION)));
        }
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
                FilenameUtils.getBaseName(file.getOriginalFilename()), UUID.fastUUID(), FileTypeUtils.getExtension(file));
    }
}
