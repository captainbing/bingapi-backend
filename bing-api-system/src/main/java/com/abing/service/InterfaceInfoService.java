package com.abing.service;

import com.abing.model.domain.InterfaceInfo;
import com.abing.model.domain.User;
import com.abing.model.dto.interfaceinfo.InterfaceInfoDTO;
import com.abing.model.dto.interfaceinfo.SearchInterfaceRequest;
import com.abing.model.vo.InterfaceInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-07-22 17:15:22
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 展示接口调用情况
     * @param user
     * @return
     */
    List<InterfaceInfoVO> listInterfaces(User user);

    /**
     * 根据名字查询接口名称
     * @param interfaceInfoDTO
     * @return
     */
    List<InterfaceInfoVO>searchInterfacesByName(InterfaceInfoDTO interfaceInfoDTO);

    /**
     * 查询接口 根据 name status method
     * @param searchInterfaceRequest
     * @return
     */
    IPage<InterfaceInfo> searchInterface(SearchInterfaceRequest searchInterfaceRequest);

    /**
     * 批量删除接口
     * @param ids
     * @return
     */
    Boolean removeInterfaceInfoBatch(List<Long> ids);
}
