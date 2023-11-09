package com.abing.service;

import com.abing.model.domain.InvokeRecord;
import com.abing.model.request.InvokeRecordRequest;
import com.abing.model.request.InvokeRequest;
import com.abing.model.vo.InvokeMenuVO;
import com.abing.model.vo.InvokeRecordVO;
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
     * 删除接口调用目录
     * @param id
     * @param id
     * @return
     */
    Boolean deleteMenu(String id);

    /**
     * 展示接口分组
     * @param id
     * @return
     */
    List<InvokeMenuVO> selectMenu(String id);

    /**
     * 获取当前用户树形目录结构
     * @return
     */
    List<InvokeMenuVO> getInvokeMenuTree();

    /**
     * 添加菜单目录
     * @param invokeRecord
     * @return
     */
    Boolean addMenu(InvokeRecord invokeRecord);

    /**
     * 修改菜单
     * @param invokeRecord
     * @return
     */
    Boolean editMenu(InvokeRecord invokeRecord);

    /**
     * 保存接口调用记录
     * @param invokeRecordRequest
     * @return
     */
    Boolean saveInvokeRecord(InvokeRecordRequest invokeRecordRequest);

    /**
     * 根据Id获取调用记录
     * @param id
     * @return
     */
    InvokeRecordVO getInvokeRecordById(String id);
}
