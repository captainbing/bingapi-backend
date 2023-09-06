package com.abing.model.dto.user;

import lombok.Data;

/**
 * @Author CaptainBing
 * @Date 2023/9/6 15:49
 * @Description
 */
@Data
public class ModifyPasswordRequest {

    private String oldPassword;
    private String newPassword;
    private String checkNewPassword;

}
