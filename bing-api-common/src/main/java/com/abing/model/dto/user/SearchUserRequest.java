package com.abing.model.dto.user;

import lombok.Data;

/**
 * @Author CaptainBing
 * @Date 2023/9/6 11:14
 * @Description
 */
@Data
public class SearchUserRequest {

    /**
     * 昵称
     */
    private String userName;
    /**
     * 角色
     */
    private String userRole;
    /**
     * 状态
     */
    private String userStatus;

    private Long current;

    private Long size;
}
