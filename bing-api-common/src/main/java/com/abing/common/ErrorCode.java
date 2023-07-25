package com.abing.common;

/**
 * @Author abing
 * @Date 2023/7/21 9:29
 * @Description 错误码
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
    * Description: 
    * @author: 小熊2003
    * @date: 2023/4/26 14:48
    * @param:
    * @return:
    */
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
