package com.abing.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.abing.common.ErrorCode;
import com.abing.constant.SearchConstant;
import com.abing.exception.BusinessException;
import com.abing.model.domain.InvokeInterface;
import com.abing.model.domain.User;
import com.abing.model.domain.UserInvokeInterface;
import com.abing.model.dto.search.QQRequest;
import com.abing.model.enums.AvatarSizeEnum;
import com.abing.model.enums.RequestMethodEnum;
import com.abing.model.request.InvokeRequest;
import com.abing.model.vo.InvokeMenuVO;
import com.abing.service.InvokeInterfaceService;
import com.abing.service.InvokeService;
import com.abing.service.UserInvokeInterfaceService;
import com.abing.service.UserService;
import com.abing.utils.ThrowUtils;
import com.abing.utils.UUIDUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;

import static com.abing.model.enums.AvatarSizeEnum.*;

/**
 * @Author CaptainBing
 * @Date 2023/8/17 16:48
 * @Description
 */
@Service
public class InvokeServiceImpl implements InvokeService {

    @Resource
    private InvokeInterfaceService invokeInterfaceService;

    @Resource
    private UserInvokeInterfaceService userInvokeInterfaceService;
    
    @Resource
    private UserService userService;

    /**
     * @param invokeRequest TODO GET POST
     * @return
     */
    @Override
    public InvokeRequest invokeAnotherInterface(InvokeRequest invokeRequest) {
        String method = invokeRequest.getMethod().toUpperCase();
        RequestMethodEnum requestMethodEnum = RequestMethodEnum.getEnum(method);
        switch (requestMethodEnum){
            case Get:

                String getBody = HttpRequest.of(invokeRequest.getUrl())
                        .setMethod(Method.GET)
                        .execute()
                        .body();
                System.out.println(getBody);
                InvokeRequest getRequest = new InvokeRequest();
                getRequest.setBaseResponse(getBody);
                return getRequest;
            case POST:
                Map<String, String> requestParams = invokeRequest.getRequestParams();
                String body = "";
                if (requestParams != null){
                    Gson gson = new Gson();
                    body = gson.toJson(requestParams);
                }
                String postBody = HttpRequest.of(invokeRequest.getUrl())
                        .setMethod(Method.POST)
                        .body(body)
                        .execute()
                        .body();
                System.out.println(postBody);
                InvokeRequest postRequest = new InvokeRequest();
                postRequest.setBaseResponse(postBody);
                return postRequest;
            case PUT:
                break;
            case DELETE:
                break;
            case OPTIONS:
                break;
            case PATCH:
                break;
            default:{
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        return null;
    }

    /**
     * @param qqRequest
     * @return qq头像url
     */
    @Override
    public String fetchQQAvatar(QQRequest qqRequest) {
        String qq = qqRequest.getQq();
        String size = qqRequest.getSize();
        AvatarSizeEnum sizeEnum = AvatarSizeEnum.getEnumByValue(size);
        if (size == null){
            size = "40";
            return String.format(SearchConstant.QQ_AVATAR_URL, qq,size);
        }
        if (!(PX_40.equals(sizeEnum) || PX_60.equals(sizeEnum) || PX_140.equals(sizeEnum) || PX_640.equals(sizeEnum))){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return String.format(SearchConstant.QQ_AVATAR_URL, qq,size);
    }

    @Override
    public List<InvokeMenuVO> getInvokeMenuTree(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        // TODO 跟用户ID绑定
        List<InvokeInterface> source = invokeInterfaceService.listInvokeMenu(loginUser);
        ThrowUtils.throwIf(source == null,ErrorCode.OPERATION_ERROR);
        List<InvokeMenuVO> invokeMenuVOList = source.stream().map(item -> {
            InvokeMenuVO invokeMenuVO = new InvokeMenuVO();
            invokeMenuVO.setTitle(item.getTitle());
            invokeMenuVO.setKey(item.getId());
            invokeMenuVO.setParentId(item.getParentId());
            invokeMenuVO.setIsLeaf("F".equals(item.getType()));
            return invokeMenuVO;
        }).collect(Collectors.toList());


        Map<Integer, List<InvokeMenuVO>> parentIdMap = invokeMenuVOList.stream()
                .collect(Collectors.groupingBy(InvokeMenuVO::getParentId));
        invokeMenuVOList.forEach(menu->{
            menu.setChildren(parentIdMap.get(menu.getKey()));
        });
        invokeMenuVOList = invokeMenuVOList.stream()
                .filter(menu->menu.getParentId() == 0)
                .collect(Collectors.toList());
        return invokeMenuVOList;
    }

    @Override
    @Transactional
    public Boolean addMenu(HttpServletRequest request,String title) {

        User loginUser = userService.getLoginUser(request);
        String ID = UUIDUtils.simpleID();
        UserInvokeInterface userInvokeInterface = new UserInvokeInterface();
        userInvokeInterface.setInvokeId(ID);
        userInvokeInterface.setUserId(loginUser.getId());

        InvokeInterface invokeInterface = new InvokeInterface();
        invokeInterface.setId(ID);
        invokeInterface.setTitle(title);
        invokeInterface.setParentId(0);
        invokeInterface.setType("M");
        return userInvokeInterfaceService.save(userInvokeInterface) && invokeInterfaceService.save(invokeInterface);
    }
}
