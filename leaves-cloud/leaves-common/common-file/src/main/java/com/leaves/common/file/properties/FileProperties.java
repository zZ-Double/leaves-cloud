package com.leaves.common.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/8/30
 */

@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /**
     * 文件名称长度
     */
    private Integer fileNameLength;

    /**
     * 文件默认大小
     */
    private Long fileMaxSize;

    /**
     * 本地地址
     */
    private String localPath;


}
