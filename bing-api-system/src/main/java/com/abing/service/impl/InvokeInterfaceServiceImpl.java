package com.abing.service.impl;

import com.abing.mapper.InvokeInterfaceMapper;
import com.abing.model.domain.InvokeInterface;
import com.abing.model.domain.User;
import com.abing.model.domain.UserInvokeInterface;
import com.abing.service.InvokeInterfaceService;
import com.abing.service.UserInvokeInterfaceService;
import com.abing.service.UserService;
import com.abing.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【invoke_interface(接口调用表（仿postman）)】的数据库操作Service实现
* @createDate 2023-10-07 22:17:39
*/
@Service
public class InvokeInterfaceServiceImpl extends ServiceImpl<InvokeInterfaceMapper, InvokeInterface>
    implements InvokeInterfaceService{

    @Resource
    private InvokeInterfaceMapper invokeInterfaceMapper;

    @Resource
    private UserInvokeInterfaceService userInvokeInterfaceService;

    @Resource
    private UserService userService;

    @Override
    public List<InvokeInterface> listInvokeMenu(User loginUser) {
        return invokeInterfaceMapper.listInvokeMenu(loginUser.getId());
    }

    @Override
    public Boolean deleteMenu(HttpServletRequest request,String id) {
        User loginUser = userService.getLoginUser(request);
        String userId = loginUser.getId();

        boolean userInvokeInterface = userInvokeInterfaceService.remove(new QueryWrapper<UserInvokeInterface>()
                .lambda()
                .eq(UserInvokeInterface::getUserId, userId)
                .eq(UserInvokeInterface::getInvokeId, id));

        boolean invokeInterface = this.removeById(id);
        return userInvokeInterface && invokeInterface;
    }
}




