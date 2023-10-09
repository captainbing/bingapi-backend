package com.abing.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户接口记录关系表（接口调用）
 * @TableName user_invoke_interface
 */
@TableName(value ="user_invoke_interface")
@Data
public class UserInvokeInterface implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 调用接口记录ID
     */
    private String invokeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}