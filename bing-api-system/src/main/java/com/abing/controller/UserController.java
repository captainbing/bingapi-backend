package com.abing.controller;

import cn.hutool.core.lang.Validator;
import com.abing.common.BaseResponse;
import com.abing.common.DeleteRequest;
import com.abing.common.ErrorCode;
import com.abing.common.ResultUtils;
import com.abing.constant.UserConstant;
import com.abing.exception.BusinessException;
import com.abing.model.domain.User;
import com.abing.model.dto.user.ModifyPasswordRequest;
import com.abing.model.dto.user.SearchUserRequest;
import com.abing.model.vo.UserVO;
import com.abing.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author abing
 * @Date 2023/7/20 19:24
 * @Description
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/login")
    public BaseResponse<UserVO> userLogin(User user, HttpServletRequest request) {
        if (StringUtils.isAnyEmpty(user.getUserAccount(), user.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!Validator.isEmail(user.getUserAccount())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱不符合规范");
        }
        if (user.getUserPassword().length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码必须设置为6位以上");
        }
        return ResultUtils.success(userService.userLogin(user, request));
    }

    @PostMapping("/edit")
    public BaseResponse<Boolean> editUser(@RequestBody User user){
        if (user.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.updateById(user));
    }

    @GetMapping("/captchaLogin")
    public BaseResponse<UserVO> captchaLogin(String userAccount, String captcha, HttpServletRequest request) {
        if (StringUtils.isAnyEmpty(userAccount, captcha)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.userLoginByCaptcha(userAccount, captcha, request));
    }

    @GetMapping("/captcha")
    public BaseResponse<String> sendCaptcha(String userAccount, HttpServletRequest request) {
        if (StringUtils.isEmpty(userAccount) || !Validator.isEmail(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.sendCaptcha(userAccount, request));
    }

    @PostMapping("/register")
    public BaseResponse<Integer> userRegister(String userAccount, String captcha, HttpServletRequest request) {
        if (StringUtils.isAnyEmpty(userAccount, captcha)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.userRegister(userAccount, captcha, request));
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if (session == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        User user = (User)session.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return ResultUtils.success(true);
    }
    @GetMapping("/getLoginUser")
    public BaseResponse<User> getLoginUser(String userAccount,HttpServletRequest request){
        if (StringUtils.isEmpty(userAccount)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User sessionUser = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (sessionUser == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"当前用户不存在");
        }
        return ResultUtils.success(sessionUser);
    }


    @GetMapping("/get")
    public BaseResponse<UserVO> getUserById(User user){
        if (user.getId() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.getUserVOById(user));
    }

    @PostMapping("/list")
    public BaseResponse<IPage<UserVO>> listUser(@RequestBody SearchUserRequest searchUserRequest) {
        return ResultUtils.success(userService.searchUser(searchUserRequest));
    }


    @PostMapping("/remove")
    public BaseResponse<Boolean> removeUserBatch(@RequestBody DeleteRequest deleteRequest){
        if (deleteRequest.getIds() == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.removeBatchByIds(deleteRequest.getIds()));
    }

    @PostMapping("/modify")
    public BaseResponse<Integer> modifyUserPassword(@RequestBody ModifyPasswordRequest modifyPasswordRequest, HttpServletRequest request){

        if (StringUtils.isAnyEmpty(modifyPasswordRequest.getOldPassword(),modifyPasswordRequest.getNewPassword(),modifyPasswordRequest.getCheckNewPassword())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (modifyPasswordRequest.getNewPassword().length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码必须设置为6位以上");
        }
        if (modifyPasswordRequest.getCheckNewPassword().length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码必须设置为6位以上");
        }
        return ResultUtils.success(userService.modifyUserPassword(modifyPasswordRequest,request));
    }

}
