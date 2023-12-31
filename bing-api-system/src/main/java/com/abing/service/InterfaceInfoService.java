package com.abing.service;

import com.abing.model.domain.InterfaceInfo;
import com.abing.model.domain.User;
import com.abing.model.dto.interfaceinfo.InterfaceInfoDTO;
import com.abing.model.dto.interfaceinfo.SearchInterfaceRequest;
import com.abing.model.vo.interfaceinfo.InterfaceInfoAnalysisVO;
import com.abing.model.vo.interfaceinfo.InterfaceInfoDrawer;
import com.abing.model.vo.interfaceinfo.InterfaceInfoVO;
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
     * @return
     */
    List<InterfaceInfoDTO> listInterfaceFromIndex();

    /**
     * 根据名字查询接口名称
     * @param interfaceInfoDTO
     * @return
     */
    List<InterfaceInfoDTO>searchInterfacesByName(InterfaceInfoDTO interfaceInfoDTO);

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

    /**
     * 根据id获取接口信息
     * @param id
     * @return
     */
    InterfaceInfoDrawer getInterfaceInfoById(Long id);

    /**
     * 接口调用前几分析
     * @return
     */
    List<InterfaceInfoAnalysisVO> listTopInterfaceInfo();
}
