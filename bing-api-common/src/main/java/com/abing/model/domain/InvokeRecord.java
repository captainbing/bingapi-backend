package com.abing.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 接口调用表（仿postman）
 * @TableName invoke_interface
 */
@TableName(value ="invoke_record")
@Data
public class InvokeRecord implements Serializable {
    /**
     * invokeId
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

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
    private String requestUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 菜单类型 M 目录 F 文件
     */
    private String type;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求头
     */
    private String requestHeader;

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

    /**
     * 标题
     */
    private String title;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}