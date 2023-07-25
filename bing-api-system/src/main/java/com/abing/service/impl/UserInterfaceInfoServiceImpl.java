package com.abing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abing.model.domain.UserInterfaceInfo;
import com.abing.service.UserInterfaceInfoService;
import com.abing.mapper.UserInterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 阿炳亿点点帅
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service实现
* @createDate 2023-07-22 17:20:19
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

}




