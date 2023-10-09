package com.abing.service.impl;

import com.abing.common.ErrorCode;
import com.abing.constant.UserConstant;
import com.abing.exception.BusinessException;
import com.abing.model.dto.user.ModifyPasswordRequest;
import com.abing.model.dto.user.SearchUserRequest;
import com.abing.model.enums.UserRoleEnum;
import com.abing.model.vo.LoginUserVO;
import com.abing.model.vo.UserVO;
import com.abing.utils.CaptchaUtils;
import com.abing.utils.EncryptUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abing.model.domain.User;
import com.abing.service.UserService;
import com.abing.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.abing.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 阿炳亿点点帅
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-07-20 19:11:19
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    @Resource
    private UserMapper userMapper;


    /**
     * @param user 用户
     * @param request
     * @return
     */
    @Override
    public UserVO userLogin(User user, HttpServletRequest request) {
        String encryptPassword = EncryptUtils.enCryptPasswordMd5(user.getUserPassword());
        User existUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUserAccount, user.getUserAccount())
                .eq(User::getUserPassword,encryptPassword));
        if (existUser == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"账号或密码错误，请重新输入");
        }
        request.getSession().setAttribute(USER_LOGIN_STATE,existUser);
        return getUserVO(existUser);

    }
    /**
     * 邮箱验证码登录
     * @param userAccount
     * @param captcha
     * @return
     */
    @Override
    public UserVO captchaLogin(String userAccount, String captcha,HttpServletRequest request) {
        String sessionCaptcha = (String)request.getSession().getAttribute(userAccount);
        if (!captcha.equals(sessionCaptcha)){
            throw new BusinessException(ErrorCode.SUCCESS,"验证码不正确，请重新输入");
        }
        // 邮箱已经存在，直接登录返回当前用户信息
        User existUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUserAccount, userAccount));
        if (existUser != null){
            return getUserVO(existUser);
        }
        // 不存在当前用户先注册
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(EncryptUtils.enCryptPasswordMd5(sessionCaptcha));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int count = userMapper.insert(user);
        if (count == 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return getUserVO(user);
    }

    /**
     * @param userAccount
     * @param request
     * @return
     */
    @Override
    public String sendCaptcha(String userAccount, HttpServletRequest request) {

        User existUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUserAccount, userAccount));
        if (existUser != null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"邮箱账号已存在");
        }
        String captcha = CaptchaUtils.random6Captcha();
        request.getSession().setAttribute(userAccount,captcha);
        // TODO  邮箱发送验证码
        return captcha;
    }

    /**
     * 邮箱验证码登录
     * @param userAccount 账号
     * @param captcha 验证码
     * @return
     */
    @Override
    public Integer userRegister(String userAccount,String captcha,HttpServletRequest request) {
        User existUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUserAccount, userAccount));
        if (existUser != null){
            throw new BusinessException(ErrorCode.SUCCESS,"账号已存在");
        }
        String sessionCaptcha = (String) request.getSession().getAttribute(userAccount);
        if (sessionCaptcha == null){
            throw new BusinessException(ErrorCode.SUCCESS,"验证码已失效，请重新获取");
        }
        if (!captcha.equals(sessionCaptcha)){
            throw new BusinessException(ErrorCode.SUCCESS,"验证码错误，请重新输入");
        }
        User user = new User();
        user.setUserAccount(userAccount);
        String encryptPassword = EncryptUtils.enCryptPasswordMd5(sessionCaptcha);
        user.setUserPassword(encryptPassword);
        int count = userMapper.insert(user);
        return count;
    }

    @Override
    public String userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return this.getLoginUserVO(user);
    }


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        String userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        String userId = currentUser.getId();
        return this.getById(userId);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }

    @Override
    public boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    /**
     * @param searchUserRequest
     * @return
     */
    @Override
    public IPage<UserVO> searchUser(SearchUserRequest searchUserRequest) {

        IPage<User> page = new Page<>();
        if (searchUserRequest.getCurrent() != null){
            page.setCurrent(searchUserRequest.getCurrent());
        }
        if (searchUserRequest.getSize() != null){
            page.setSize(searchUserRequest.getSize());
        }
        IPage<User> userPage = userMapper.selectPage(page, new QueryWrapper<User>()
                .lambda()
                .like(StringUtils.isNotEmpty(searchUserRequest.getUserName()), User::getUserName, searchUserRequest.getUserName())
                .eq(StringUtils.isNotEmpty(searchUserRequest.getUserRole()), User::getUserRole, searchUserRequest.getUserRole())
                .eq(StringUtils.isNotEmpty(searchUserRequest.getUserStatus()), User::getUserStatus, searchUserRequest.getUserStatus()));
        List<UserVO> userVOList = getUserVO(userPage.getRecords());
        IPage<UserVO> userVOPage = new Page<>();
        BeanUtils.copyProperties(userPage,userVOPage);
        userVOPage.setRecords(userVOList);
        return userVOPage;
    }


    /**
     * @param modifyPasswordRequest
     * @return
     */
    @Override
    public Integer modifyUserPassword(ModifyPasswordRequest modifyPasswordRequest,HttpServletRequest request) {
        String oldPassword = EncryptUtils.enCryptPasswordMd5(modifyPasswordRequest.getOldPassword());
        User loginUser = getLoginUser(request);
        User existUser = userMapper.selectOne(new QueryWrapper<User>()
                .lambda()
                .eq(User::getUserAccount, loginUser.getUserAccount())
                .eq(User::getUserPassword, oldPassword));
        if (existUser == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"用户不存在或密码错误");
        }
        if (!modifyPasswordRequest.getNewPassword().equals(modifyPasswordRequest.getCheckNewPassword())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"密码不一致，请稍后再试");
        }
        String newPassword = EncryptUtils.enCryptPasswordMd5(modifyPasswordRequest.getCheckNewPassword());
        loginUser.setUserPassword(newPassword);
        int count = userMapper.updateById(loginUser);
        // TODO 发送邮件
        return count;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public UserVO getUserVOById(User user) {
        User originUser = userMapper.selectById(user.getId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(originUser,userVO);
        return userVO;
    }
}




