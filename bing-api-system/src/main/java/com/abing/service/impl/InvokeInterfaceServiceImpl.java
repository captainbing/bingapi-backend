package com.abing.service.impl;

import com.abing.common.ErrorCode;
import com.abing.mapper.InvokeInterfaceMapper;
import com.abing.model.domain.InvokeRecord;
import com.abing.model.domain.User;
import com.abing.model.domain.UserInvokeInterface;
import com.abing.model.vo.InvokeMenuVO;
import com.abing.service.InvokeRecordService;
import com.abing.service.UserInvokeInterfaceService;
import com.abing.service.UserService;
import com.abing.utils.ThrowUtils;
import com.abing.utils.UUIDUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 阿炳亿点点帅
* @description 针对表【invoke_interface(接口调用表（仿postman）)】的数据库操作Service实现
* @createDate 2023-10-07 22:17:39
*/
@Service
public class InvokeInterfaceServiceImpl extends ServiceImpl<InvokeInterfaceMapper, InvokeRecord>
    implements InvokeRecordService {

    @Resource
    private InvokeInterfaceMapper invokeInterfaceMapper;

    @Resource
    private UserInvokeInterfaceService userInvokeInterfaceService;

    @Resource
    private UserService userService;

    @Override
    public List<InvokeRecord> listInvokeMenu(String id) {
        return invokeInterfaceMapper.listInvokeMenu(id);
    }

    @Override
    public Boolean deleteMenu(HttpServletRequest request,String id) {
        User loginUser = userService.getLoginUser(request);
        String userId = loginUser.getId();

        boolean userInvokeInterface = userInvokeInterfaceService.remove(new QueryWrapper<UserInvokeInterface>()
                .lambda()
                .eq(UserInvokeInterface::getUserId, userId)
                .eq(UserInvokeInterface::getInvokeId, id));

        boolean invokeInterface = this.removeById(id);
        return userInvokeInterface && invokeInterface;
    }

    @Override
    public List<InvokeMenuVO> selectMenu(String id) {

        List<InvokeRecord> source = this.listInvokeMenu(id);
        ThrowUtils.throwIf(source == null, ErrorCode.OPERATION_ERROR);
        // 筛选出只是目录的菜单
        List<InvokeMenuVO> selectMenuVOList = source.stream().map(item -> {
            InvokeMenuVO invokeMenuVO = new InvokeMenuVO();
            invokeMenuVO.setKey(item.getId());
            invokeMenuVO.setTitle(item.getTitle());
            invokeMenuVO.setValue(item.getId());
            invokeMenuVO.setParentId(item.getParentId());
            invokeMenuVO.setIsLeaf("F".equals(item.getType()));
            return invokeMenuVO;
        })
                .filter(item->!item.getIsLeaf())
                .collect(Collectors.toList());
        // 生成树形结构
        Map<String, List<InvokeMenuVO>> parentIdMap = selectMenuVOList.stream()
                .collect(Collectors.groupingBy(InvokeMenuVO::getParentId));
        selectMenuVOList.forEach(menu->{
            menu.setChildren(parentIdMap.get(menu.getKey()));
        });
        selectMenuVOList = selectMenuVOList.stream()
                .filter(menu->"0".equals(menu.getParentId()))
                .collect(Collectors.toList());
        InvokeMenuVO invokeMenuVO = new InvokeMenuVO();
        invokeMenuVO.setTitle("根目录");
        invokeMenuVO.setValue("0");
        selectMenuVOList.add(invokeMenuVO);
        return selectMenuVOList;
    }


    @Override
    public List<InvokeMenuVO> getInvokeMenuTree(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<InvokeRecord> source = this.listInvokeMenu(loginUser.getId());
        ThrowUtils.throwIf(source == null,ErrorCode.OPERATION_ERROR);
        List<InvokeMenuVO> invokeMenuVOList = source.stream().map(item -> {
            InvokeMenuVO invokeMenuVO = new InvokeMenuVO();
            invokeMenuVO.setTitle(item.getTitle());
            invokeMenuVO.setKey(item.getId());
            invokeMenuVO.setParentId(item.getParentId());
            invokeMenuVO.setIsLeaf("F".equals(item.getType()));
            return invokeMenuVO;
        }).collect(Collectors.toList());


        Map<String, List<InvokeMenuVO>> parentIdMap = invokeMenuVOList.stream()
                .collect(Collectors.groupingBy(InvokeMenuVO::getParentId));
        invokeMenuVOList.forEach(menu->{
            menu.setChildren(parentIdMap.get(menu.getKey()));
        });
        invokeMenuVOList = invokeMenuVOList.stream()
                .filter(menu->"0".equals(menu.getParentId()))
                .collect(Collectors.toList());
        return invokeMenuVOList;
    }

    @Override
    @Transactional
    public Boolean addMenu(HttpServletRequest request,String title,String parentId) {

        User loginUser = userService.getLoginUser(request);
        String ID = UUIDUtils.simpleID();
        UserInvokeInterface userInvokeInterface = new UserInvokeInterface();
        userInvokeInterface.setInvokeId(ID);
        userInvokeInterface.setUserId(loginUser.getId());

        InvokeRecord invokeRecord = new InvokeRecord();
        invokeRecord.setId(ID);
        invokeRecord.setTitle(title);
        invokeRecord.setParentId(parentId);
        invokeRecord.setType("M");
        return userInvokeInterfaceService.save(userInvokeInterface) && this.save(invokeRecord);
    }
}




