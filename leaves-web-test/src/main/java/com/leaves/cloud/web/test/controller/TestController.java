package com.leaves.cloud.web.test.controller;

import com.leaves.common.result.Result;
import com.leaves.dubbo.api.ITestProvider;
import com.leaves.dubbo.dto.TestDTO;
import com.leaves.dubbo.vo.TestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/5
 */
@Api(tags = "测试接口")
@RestController
@Slf4j
public class TestController {

    @Value("${test}")
    String test;

    @DubboReference
    private ITestProvider provider;

    @ApiOperation(value = "测试接口1")
    @GetMapping("/test")
    public Result<TestVO> test() {
        log.info("从nacos读取配置test的值：{}", test);
        TestDTO dto = new TestDTO();
        dto.setName("张三");
        dto.setAge(18);
        Result<TestVO> result = new Result<>();
        try {
            result = provider.test(dto).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
