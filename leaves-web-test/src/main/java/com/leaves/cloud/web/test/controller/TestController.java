package com.leaves.cloud.web.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leaves
 * @version 1.0
 * @description
 * @date 2023/7/5
 */
@RestController
@Slf4j
public class TestController {

    @Value("${test}")
    String test;


    @GetMapping("/test/{msg}")
    public String test(@PathVariable("msg") String msg) {
//        Result<TestVO> testVOResult = provider.dubboTest(msg);
//        return test + testVOResult.getData().getTestStr();

        return test + " " + msg;
    }
}
