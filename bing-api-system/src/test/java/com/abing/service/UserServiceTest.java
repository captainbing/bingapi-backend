package com.abing.service;

import com.abing.model.domain.User;
import com.abing.utils.EncryptUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author CaptainBing
 * @Date 2023/9/17 13:55
 * @Description
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;


    @Test
    void updateUserPassword() {
        User user = userService.getOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUserAccount, "750321038@qq.com"));
        if (user != null) {
            String newPassword = EncryptUtil.enCryptPasswordMd5("750321");
            user.setUserPassword(newPassword);
            assertTrue(userService.updateById(user));

        }
    }
}