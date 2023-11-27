package com.abing.service.dubbo;

import com.abing.dubbo.service.InterfaceInfoDubboService;
import com.abing.mapper.InterfaceInfoMapper;
import com.abing.model.domain.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author CaptainBing
 * @Date 2023/11/27 22:09
 * @Description
 */
@Service
@org.apache.dubbo.config.annotation.Service
public class InterfaceInfoDubboServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoDubboService {
}
