package com.leaves.common.file.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/9/1
 */
@Setter
@Getter
@Builder
public class FileInfo implements Serializable {

    private String fileName;

    private String fileUrl;
}
