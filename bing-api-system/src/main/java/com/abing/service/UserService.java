package com.abing.service;

import com.abing.model.domain.User;
import com.abing.model.dto.user.ModifyPasswordRequest;
import com.abing.model.dto.user.SearchUserRequest;
import com.abing.model.vo.LoginUserVO;
import com.abing.model.vo.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-07-20 19:11:19
*/
public interface UserService extends IService<User> {


    /**
     * 用户使用邮箱登录
     * @param user
     * @param request
     * @return
     */
    UserVO userLogin(User user,HttpServletRequest request);


    /**
     * 验证码登录
     * @param userAccount
     * @param captcha
     * @param request
     * @return
     */
    UserVO userLoginByCaptcha(String userAccount, String captcha, HttpServletRequest request);


    /**
     * 邮箱注册发送验证码
     * @param userAccount
     * @param request
     * @return
     */
    String sendCaptcha(String userAccount,HttpServletRequest request);

    /**
     * 用户邮箱注册
     * @param userAccount 账号
     * @param captcha 验证码
     * @return
     */
    Integer userRegister(String userAccount,String captcha,HttpServletRequest request);







    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    String userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 查询用户集合
     *
     * @param searchUserRequest
     * @return
     */
    IPage<UserVO> searchUser(SearchUserRequest searchUserRequest);

    /**
     * 修改用户密码
     * @param modifyPasswordRequest
     * @param request
     * @return
     */
    Integer modifyUserPassword(ModifyPasswordRequest modifyPasswordRequest,HttpServletRequest request);

    /**
     * 根据Id获取用户
     * @param user
     * @return
     */
    UserVO getUserVOById(User user);

    /**
     * 重置密钥
     * @return
     */
    UserVO resetEncryptKey();
}
