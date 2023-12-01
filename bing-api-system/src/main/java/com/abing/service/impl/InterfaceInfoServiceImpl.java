package com.abing.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.abing.common.ErrorCode;
import com.abing.exception.BusinessException;
import com.abing.mapper.UserInterfaceInfoMapper;
import com.abing.model.domain.UserInterfaceInfo;
import com.abing.model.vo.interfaceinfo.*;
import com.abing.service.InterfaceInfoService;
import com.abing.model.domain.User;
import com.abing.model.dto.interfaceinfo.InterfaceInfoDTO;
import com.abing.model.dto.interfaceinfo.SearchInterfaceRequest;
import com.abing.model.enums.InterfaceInfoEnum;
import com.abing.service.UserInterfaceInfoService;
import com.abing.utils.ThrowUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.abing.model.domain.InterfaceInfo;
import com.abing.mapper.InterfaceInfoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
* @author 阿炳亿点点帅
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-07-22 17:15:22
*/
@Service
@RequiredArgsConstructor
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {


    private final UserInterfaceInfoService userInterfaceInfoService;

    private final InterfaceInfoMapper interfaceInfoMapper;

    private final UserInterfaceInfoMapper userInterfaceInfoMapper;
    /**
     * @return
     */
    @Override
    public List<InterfaceInfoVO> listInterfaces(User user) {
        List<InterfaceInfoDTO> interfaceInfoDTOS = interfaceInfoMapper.listInterfaces(user);
        return interfaceInfoDTOS.stream().map(interfaceInfoDTO -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            interfaceInfoVO.setId(interfaceInfoDTO.getId());
            interfaceInfoVO.setName(interfaceInfoDTO.getName());
            interfaceInfoVO.setDesc(interfaceInfoDTO.getDescription());
            List<ProList> content = IntStream.range(0, 3).boxed().map(i -> {
                ProList proList = new ProList();
                switch (i){
                    case 0:
                        proList.setLabel("接口状态");
                        proList.setValue(InterfaceInfoEnum.AVAILABLE.getStatus().equals(interfaceInfoDTO.getStatus())
                                ? InterfaceInfoEnum.AVAILABLE.getDescription()
                                : InterfaceInfoEnum.CLOSE.getDescription());
                        proList.setStatus(InterfaceInfoEnum.AVAILABLE.getStatus().equals(interfaceInfoDTO.getStatus())
                                ? InterfaceInfoEnum.AVAILABLE.getDescription()
                                : InterfaceInfoEnum.CLOSE.getDescription());
                        break;
                    case 1:
                        proList.setLabel("总调用次数");
                        proList.setValue(String.valueOf(interfaceInfoDTO.getInvokeTotal()));
                        proList.setStatus(InterfaceInfoEnum.AVAILABLE.getStatus().equals(interfaceInfoDTO.getStatus())
                                ? InterfaceInfoEnum.AVAILABLE.getDescription()
                                : InterfaceInfoEnum.CLOSE.getDescription());
                        break;
                    case 2:
                        proList.setLabel("剩余调用次数");
                        proList.setValue(String.valueOf(interfaceInfoDTO.getLeftNum()));
                        proList.setStatus(InterfaceInfoEnum.AVAILABLE.getStatus().equals(interfaceInfoDTO.getStatus())
                                ? InterfaceInfoEnum.AVAILABLE.getDescription()
                                : InterfaceInfoEnum.CLOSE.getDescription());
                        break;
                    default:{}
                }
                return proList;
            }).collect(Collectors.toList());
            interfaceInfoVO.setContent(content);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
    }

    /**
     * @param interfaceRequest
     * @return
     */
    @Override
    public List<InterfaceInfoVO> searchInterfacesByName(InterfaceInfoDTO interfaceRequest) {
        List<InterfaceInfoDTO> interfaceInfoDTOS = interfaceInfoMapper.searchInterfacesByName(interfaceRequest);
        return interfaceInfoDTOS.stream().map(interfaceInfoDTO -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            interfaceInfoVO.setName(interfaceInfoDTO.getName());
            interfaceInfoVO.setId(interfaceInfoDTO.getId());
            interfaceInfoVO.setDesc(interfaceInfoDTO.getDescription());
            List<ProList> content = IntStream.range(0, 3).boxed().map(i -> {
                ProList proList = new ProList();
                switch (i){
                    case 0:
                        proList.setLabel("接口状态");
                        proList.setValue(InterfaceInfoEnum.AVAILABLE.getStatus().equals(interfaceInfoDTO.getStatus())
                                ? InterfaceInfoEnum.AVAILABLE.getDescription()
                                : InterfaceInfoEnum.CLOSE.getDescription());
                        proList.setStatus(InterfaceInfoEnum.AVAILABLE.getStatus().equals(interfaceInfoDTO.getStatus())
                                ? InterfaceInfoEnum.AVAILABLE.getDescription()
                                : InterfaceInfoEnum.CLOSE.getDescription());
                        break;
                    case 1:
                        proList.setLabel("总调用次数");
                        proList.setValue(String.valueOf(interfaceInfoDTO.getInvokeTotal()));
                        proList.setStatus(InterfaceInfoEnum.AVAILABLE.getStatus().equals(interfaceInfoDTO.getStatus())
                                ? InterfaceInfoEnum.AVAILABLE.getDescription()
                                : InterfaceInfoEnum.CLOSE.getDescription());
                        break;
                    case 2:
                        proList.setLabel("剩余调用次数");
                        proList.setValue(String.valueOf(interfaceInfoDTO.getLeftNum()));
                        proList.setStatus(InterfaceInfoEnum.AVAILABLE.getStatus().equals(interfaceInfoDTO.getStatus())
                                ? InterfaceInfoEnum.AVAILABLE.getDescription()
                                : InterfaceInfoEnum.CLOSE.getDescription());
                        break;
                    default:{}
                }
                return proList;
            }).collect(Collectors.toList());
            interfaceInfoVO.setContent(content);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
    }

    /**
     * @param searchInterfaceRequest
     * @return
     */
    @Override
    public IPage<InterfaceInfo> searchInterface(SearchInterfaceRequest searchInterfaceRequest) {
        IPage<InterfaceInfo> page = new Page<>();
        if (searchInterfaceRequest.getCurrent() != null){
            page.setCurrent(searchInterfaceRequest.getCurrent());
        }
        if (searchInterfaceRequest.getSize() != null){
            page.setSize(searchInterfaceRequest.getSize());
        }
        return interfaceInfoMapper.selectPage(page,new QueryWrapper<InterfaceInfo>()
                .lambda()
                .like(StringUtils.isNotEmpty(searchInterfaceRequest.getName()),InterfaceInfo::getName,searchInterfaceRequest.getName())
                .eq(StringUtils.isNotEmpty(searchInterfaceRequest.getStatus()),InterfaceInfo::getStatus,searchInterfaceRequest.getStatus())
                .eq(StringUtils.isNotEmpty(searchInterfaceRequest.getMethod()),InterfaceInfo::getMethod,searchInterfaceRequest.getMethod()));
    }


    /**
     * @param ids
     * @return
     */
    @Override
    public Boolean removeInterfaceInfoBatch(List<Long> ids) {
        return this.removeBatchByIds(ids);
    }

    @Override
    public InterfaceInfoDrawer getInterfaceInfoById(Long id) {

        ThrowUtils.throwIf(id == null, ErrorCode.PARAMS_ERROR);

        InterfaceInfo interfaceInfo = this.getById(id);

        ThrowUtils.throwIf(interfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);

        InterfaceInfoDrawer interfaceInfoDrawer = new InterfaceInfoDrawer();
        BeanUtils.copyProperties(interfaceInfo,interfaceInfoDrawer);

        String requestParam = interfaceInfo.getRequestParam();
        String responseParam = interfaceInfo.getResponseParam();
        List<RequestParam> requestParams = JSONUtil.toList(requestParam, RequestParam.class);
        List<ResponseParam> responseParams = JSONUtil.toList(responseParam, ResponseParam.class);

        interfaceInfoDrawer.setRequestParam(requestParams);
        interfaceInfoDrawer.setResponseParam(responseParams);

        return interfaceInfoDrawer;
    }

    @Override
    public List<InterfaceInfoAnalysisVO> listTopInterfaceInfo() {

        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInterfaceInfo(3L);
        Map<String, List<UserInterfaceInfo>> userInterfaceInfoMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        List<InterfaceInfo> list = this.list(new QueryWrapper<InterfaceInfo>()
                .lambda()
                .in(InterfaceInfo::getId, userInterfaceInfoMap.keySet()));
        if (CollectionUtil.isEmpty(list)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        List<InterfaceInfoAnalysisVO> analysisVOList = list.stream().map(interfaceInfo -> {
            InterfaceInfoAnalysisVO interfaceInfoAnalysisVO = new InterfaceInfoAnalysisVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoAnalysisVO);
            interfaceInfoAnalysisVO.setTotalNum(userInterfaceInfoMap.get(String.valueOf(interfaceInfo.getId())).get(0).getTotalNum());
            return interfaceInfoAnalysisVO;
        }).collect(Collectors.toList());
        return analysisVOList;
    }

}




