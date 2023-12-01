package com.abing.service.dubbo;

import com.abing.dubbo.service.UserInterfaceInfoDubboService;
import com.abing.exception.BusinessException;
import com.abing.mapper.UserInterfaceInfoMapper;
import com.abing.model.domain.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author CaptainBing
 * @Date 2023/11/29 13:55
 * @Description
 */
@Service
@org.apache.dubbo.config.annotation.Service
@RequiredArgsConstructor
public class UserInterfaceInfoDubboServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements UserInterfaceInfoDubboService {


    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Boolean invokeCountPlusOne(String userId, String interfaceInfoId) {
        if (StringUtils.isAnyEmpty(userId,interfaceInfoId)){
            return false;
        }
        UserInterfaceInfo currentUserInterfaceInfo = this.getOne(new QueryWrapper<UserInterfaceInfo>()
                .lambda()
                .eq(UserInterfaceInfo::getUserId, userId)
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId));
        // 开发新接口时初始化用户接口关系
        if (currentUserInterfaceInfo == null){
            currentUserInterfaceInfo = new UserInterfaceInfo();
            currentUserInterfaceInfo.setUserId(userId);
            currentUserInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
            currentUserInterfaceInfo.setTotalNum(0L);
            currentUserInterfaceInfo.setLeftNum(50);
            currentUserInterfaceInfo.setStatus(0);
            currentUserInterfaceInfo.setCreateTime(new Date());
            currentUserInterfaceInfo.setUpdateTime(new Date());
            this.save(currentUserInterfaceInfo);
        }
        if (currentUserInterfaceInfo.getLeftNum() < 0){
            return false;
        }
        boolean updateInvokeCount = this.update(new UpdateWrapper<UserInterfaceInfo>()
                .lambda()
                .set(UserInterfaceInfo::getTotalNum, currentUserInterfaceInfo.getTotalNum() + 1)
                .set(UserInterfaceInfo::getLeftNum, currentUserInterfaceInfo.getLeftNum() - 1)
                .set(UserInterfaceInfo::getUpdateTime,new Date())
                .eq(UserInterfaceInfo::getUserId, userId)
                .eq(UserInterfaceInfo::getInterfaceInfoId, interfaceInfoId));

        return updateInvokeCount;
    }
}
