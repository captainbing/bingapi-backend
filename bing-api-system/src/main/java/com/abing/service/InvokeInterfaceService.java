package com.abing.service;

import com.abing.model.domain.InvokeInterface;
import com.abing.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【invoke_interface(接口调用表（仿postman）)】的数据库操作Service
* @createDate 2023-10-07 22:17:39
*/
public interface InvokeInterfaceService extends IService<InvokeInterface> {

    /**
     * 获取当前用户的接口调用目录
     * @param loginUser
     * @return
     */
    List<InvokeInterface> listInvokeMenu(User loginUser);

    /**
     * 删除接口调用目录
     * @param id
     * @return
     */
    Boolean deleteMenu(HttpServletRequest request,String id);
}
