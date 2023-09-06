package com.abing.mapper;

import com.abing.model.domain.InterfaceInfo;
import com.abing.model.domain.User;
import com.abing.model.dto.interfaceinfo.InterfaceInfoDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【interface_info(接口信息)】的数据库操作Mapper
* @createDate 2023-07-22 17:15:22
* @Entity com.abing.model.domain.InterfaceInfo
*/
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    /**
     * 获取当前用户所有接口的获取情况
     * @param user
     * @return
     */
    List<InterfaceInfoDTO> listInterfaces(User user);


    List<InterfaceInfoDTO> searchInterfacesByName(InterfaceInfoDTO interfaceRequest);

}




