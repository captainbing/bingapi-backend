package com.abing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author abing
 * @Date 2023/7/20 19:09
 * @Description
 */
@RestController
public class DemoServiceController {
    @GetMapping("/demo")
    public String getDemo(){
        return "demo service controller";
    }
}
