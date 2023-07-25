package com.abing.controller;

import com.abing.common.BaseResponse;
import com.abing.common.ResultUtils;
import com.abing.model.domain.User;
import com.abing.service.MailService;
import com.abing.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author abing
 * @Date 2023/7/20 19:24
 * @Description
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private MailService mailService;

    @GetMapping("/get")
    public String getMessage() {
        return mailService.sendMessage("750321038@qq.com");
    }
    @GetMapping("/list")
    public BaseResponse<List<User>> listUser(){
        return ResultUtils.success(userService.list());
    }
}
