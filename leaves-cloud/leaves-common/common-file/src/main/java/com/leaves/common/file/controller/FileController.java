package com.leaves.common.file.controller;

import com.leaves.common.file.enums.FileStorageEnum;
import com.leaves.common.file.factory.FileStrategyFactory;
import com.leaves.common.file.model.FileInfo;
import com.leaves.common.file.strategy.FileStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件接口")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStrategyFactory factory;

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public FileInfo upload (
            @ApiParam("表单文件对象") @RequestParam(value = "file") MultipartFile file,
            @ApiParam("文件存储类型") @RequestParam(value = "fileStorage") FileStorageEnum fileStorage) {

        FileStrategy strategy = factory.getStrategy(fileStorage);
        return strategy.uploadFile(file);
    }

    @ApiOperation(value = "文件下载")
    @GetMapping("/download")
    public void download (@ApiParam("文件名称") @RequestParam(value = "fileName") String fileName,
                              @ApiParam("文件存储类型") @RequestParam(value = "fileStorage") FileStorageEnum fileStorage) {

        FileStrategy strategy = factory.getStrategy(fileStorage);
        strategy.downloadFile(fileName);
    }
}
