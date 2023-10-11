package com.abing.service;

import com.abing.model.domain.InvokeRecord;
import com.abing.model.request.InvokeRequest;
import com.abing.model.vo.InvokeMenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【invoke_interface(接口调用表（仿postman）)】的数据库操作Service
* @createDate 2023-10-07 22:17:39
*/
public interface InvokeRecordService extends IService<InvokeRecord> {

    /**
     * 获取当前用户的接口调用目录
     * @param id
     * @return
     */
    List<InvokeRecord> listInvokeMenu(String id);

    /**
     * 删除接口调用目录
     * @param request
     * @param id
     * @return
     */
    Boolean deleteMenu(HttpServletRequest request,String id);

    /**
     * 展示接口分组
     * @param id
     * @return
     */
    List<InvokeMenuVO> selectMenu(String id);

    /**
     * 获取树形目录结构
     * @return
     */
    List<InvokeMenuVO> getInvokeMenuTree(HttpServletRequest request);

    /**
     * 添加菜单目录
     * @param request
     * @param title
     * @return
     */
    Boolean addMenu(HttpServletRequest request,String title,String parentId);
}
