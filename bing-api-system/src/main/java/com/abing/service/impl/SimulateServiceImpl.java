package com.abing.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.abing.common.ErrorCode;
import com.abing.constant.SearchConstant;
import com.abing.exception.BusinessException;
import com.abing.model.dto.search.QQRequest;
import com.abing.model.enums.AvatarSizeEnum;
import com.abing.service.SimulateService;
import com.abing.utils.ThrowUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.abing.model.enums.AvatarSizeEnum.*;
import static com.abing.model.enums.AvatarSizeEnum.PX_640;

/**
 * @Author CaptainBing
 * @Date 2023/11/24 17:21
 * @Description
 */
@Service
public class SimulateServiceImpl implements SimulateService {

    @Override
    public String convertChinese2Pinyin(String chinese) {
        ThrowUtils.throwIf(chinese == null, ErrorCode.PARAMS_ERROR);
        return PinyinUtil.getPinyin(chinese);
    }

    @Override
    public String fetchQQAvatar(QQRequest qqRequest) {
        ThrowUtils.throwIf(StringUtils.isEmpty(qqRequest.getQq()),ErrorCode.PARAMS_ERROR);
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


}
