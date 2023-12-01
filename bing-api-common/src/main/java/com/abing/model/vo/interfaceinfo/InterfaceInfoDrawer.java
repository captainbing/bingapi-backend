package com.abing.model.vo.interfaceinfo;

import com.abing.model.domain.InterfaceInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/12/1 21:01
 * @Description 接口描述 抽屉 TODO 改一个好听的名字
 */
@Data
public class InterfaceInfoDrawer {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求参数
     */
    private List<RequestParam> requestParam;



    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 返回参数
     */
    private List<ResponseParam> responseParam;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 创建人
     */

    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 总调用次数
     */
    private Long invokeTotal;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    private Integer deleted;


}
