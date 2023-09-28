package com.abing.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 定时任务调度表
 * @TableName sys_job
 */
@TableName(value ="sys_job")
@Data
public class SysJob implements Serializable {
    /**
     * 任务ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务分组(DEFAULT,SYSTEM)
     */
    private String jobGroup;

    /**
     * 调用方法
     */
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * 计划执行错误策略（1立即执行 2执行一次 3放弃执行）
     */
    private Integer misfirePolicy;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    private Integer concurrent;

    /**
     * 状态（0正常 1暂停）
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注信息
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}