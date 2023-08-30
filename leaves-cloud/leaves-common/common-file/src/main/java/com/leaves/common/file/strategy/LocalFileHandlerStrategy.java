package com.leaves.common.file.strategy;

import com.leaves.common.exception.BizException;
import com.leaves.common.file.AbstractFileHandler;
import com.leaves.common.file.enums.FileStorageEnum;
import com.leaves.common.file.properties.FileProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 本地上传文件
 */

@ConditionalOnBean(FileProperties.class)
public class LocalFileHandlerStrategy extends AbstractFileHandler {

    private FileProperties properties;

    public LocalFileHandlerStrategy(FileProperties properties) {
        super(properties);
        this.properties = properties;
    }


    @Override
    public FileStorageEnum getFileStorage() {
        return FileStorageEnum.LOCAL_STO;
    }

    @Override
    public String upload(MultipartFile file, String fileName) {
        String absPath = getAbsoluteFile(properties.getLocalPath(), fileName).getAbsolutePath();
        try {
            file.transferTo(Paths.get(absPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new BizException("上传文件错误，请联系管理员");
        }
        return properties.getDomain() + properties.getLocalPath() + getPathFileName(fileName);
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

    private String getPathFileName(String fileName) {
        String pathFileName = "/" + fileName;
        return pathFileName;
    }
}
