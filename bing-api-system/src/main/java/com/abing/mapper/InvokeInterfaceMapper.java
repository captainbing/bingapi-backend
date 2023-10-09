package com.abing.mapper;

import com.abing.model.domain.InvokeInterface;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【invoke_interface(接口调用表（仿postman）)】的数据库操作Mapper
* @createDate 2023-10-07 22:17:39
* @Entity com.abing.model.domain.InvokeInterface
*/
public interface InvokeInterfaceMapper extends BaseMapper<InvokeInterface> {

    /**
     * 查询当前用户的调用接口记录目录
     * @param id
     * @return
     */
    List<InvokeInterface> listInvokeMenu(@Param("userId") String id);
}




