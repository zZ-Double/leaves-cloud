package com.leaves.common.file.strategy;

import com.leaves.common.exception.BizException;
import com.leaves.common.file.enums.FileStorageEnum;
import com.leaves.common.file.model.FileInfo;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 本地 文件存储
 */
@Component
@ConfigurationProperties(prefix = "file")
@ConditionalOnProperty(prefix = "file", name = "enabled")
public class LocalFileStrategy extends AbstractFileStrategy {

    /**
     * 文件默认大小
     */
    @Setter
    private Long fileMaxSize;

    /**
     * 对外访问
     */
    @Setter
    private String url;

    /**
     * 本地地址
     */
    @Setter
    private String path;

    @Override
    public FileStorageEnum getFileStorage() {
        return FileStorageEnum.LOCAL_STO;
    }

    @Override
    public FileInfo upload(MultipartFile file, String fileName) {

        if (file.getSize() > fileMaxSize) {
            throw new BizException("上传文件大小超出: " + fileMaxSize);
        }

        String absPath = getAbsoluteFile(path, fileName).getAbsolutePath();
        try {
            file.transferTo(Paths.get(absPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException("上传文件错误，请联系管理员");
        }

        return FileInfo.builder()
                .fileName(fileName)
                .fileUrl(url + path + File.separator + fileName)
                .build();
    }

    @Override
    public void download(String fileName) {

    }

    private File getAbsoluteFile(String uploadDir, String fileName) {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc.isAbsolute() ? desc : desc.getAbsoluteFile();
    }
}
