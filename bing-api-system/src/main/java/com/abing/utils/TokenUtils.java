package com.abing.utils;

import com.abing.constant.UserConstant;
import com.abing.model.domain.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author CaptainBing
 * @Date 2023/9/21 21:39
 * @Description
 */
public class TokenUtils {

    /**
     * 获取request对象
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前用户登录用户名
     * @return
     */
    public static String getLoginUserName(){
        HttpSession httpSession = getRequest().getSession();
        User user = (User) httpSession.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user != null){
            return user.getUserName();
        }
        return null;
    }


    /**
     * 获取当前用户登录账号（邮箱）
     * @return
     */
    public static String getLoginUserAccount(){
        HttpSession httpSession = getRequest().getSession();
        User user = (User) httpSession.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user != null){
            return user.getUserAccount();
        }
        return null;
    }
}
