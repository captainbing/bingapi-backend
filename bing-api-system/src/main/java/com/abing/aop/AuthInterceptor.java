package com.abing.aop;

import com.abing.annotation.AuthCheck;
import com.abing.common.ErrorCode;
import com.abing.exception.BusinessException;
import com.abing.model.domain.User;
import com.abing.model.enums.UserRoleEnum;
import com.abing.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author abing
 * @Date 2023/7/22 16:22
 * @Description 权限校验
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    public Object checkAuth(ProceedingJoinPoint proceedingJoinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        if (StringUtils.isNotBlank(mustRole)) {
            UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
            if (userRoleEnum == null) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
            String userRole = loginUser.getUserRole();
            if (UserRoleEnum.BAN.getValue().equals(userRole)){
                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR);
            }
            if(UserRoleEnum.ADMIN.equals(userRoleEnum)){
                if (!userRole.equals(mustRole)){
                    throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
                }
            }
        }
        // 通过权限校验，放行
        return proceedingJoinPoint.proceed();
    }

}
