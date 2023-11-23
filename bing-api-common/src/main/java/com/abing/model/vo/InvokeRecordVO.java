package com.abing.model.vo;

import com.abing.model.domain.InvokeRecord;
import com.abing.model.request.RequestField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author CaptainBing
 * @Date 2023/11/9 17:34
 * @Description
 */
@Data
public class InvokeRecordVO implements Serializable {


    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * parentId
     */
    private String parentId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 菜单类型 M 目录 F 文件
     */
    private String type;


    /**
     * 请求参数
     */
    private List<RequestField> requestParam;
    /**
     * 请求头
     */
    private List<RequestField> requestHeader;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 响应体
     */
    private String responseBody;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
