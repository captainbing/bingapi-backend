package com.abing.mapper;

import com.abing.model.domain.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 阿炳亿点点帅
* @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Mapper
* @createDate 2023-07-22 17:20:19
* @Entity com.abing.model.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * 接口调用次数分析
     * @param limit
     * @return
     */
    List<UserInterfaceInfo> listTopInterfaceInfo(@Param("limit") long limit);
}




