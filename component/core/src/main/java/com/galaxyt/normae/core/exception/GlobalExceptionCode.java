package com.galaxyt.normae.core.exception;

/**
 * 全局异常状态码
 */
public enum GlobalExceptionCode {


    /**
     * 请求成功
     */
    SUCCESS(0, "SUCCESS"),

    ERROR(1, "系统异常"),

    REQUEST_ARGUMENT_EXCEPTION(2, "请求参数异常"),

    USER_LOGIN_STATUS_EXCEPTION(3, "当前用户登陆信息未进行初始化"),

    NOT_FOUND_ENUM(4, "未找到对应枚举类型"),

    FEIGN_ERROR(5, "Feign 接口请求异常"),

    JEDIS_POOL_INIT_FAIL(6, "Jedis Pool 初始化失败"),

    HTTP_REQUEST_ERROR(7, "网络请求异常"),

    ACCOUNT_IS_DISABLED(1001, "帐号已被禁用"),
    USERNAME_IS_NOT_FOUNT(1002, "帐号不存在"),
    PASSWORD_IS_WRONG(1003, "密码输入错误"),
    USERNAME_ALREADY_EXISTS(1004, "用户名已存在"),


    PHONE_NUMBER_FORMAT_WRONG(2001, "手机号码格式错误"),
    MESSAGE_SEND_ERROR(2002, "短信发送失败"),
    MESSAGE_SEND_FREQUENTLY(2003, "短信发送频繁"),

    FILE_UPLOAD_FAIL(3001, "文件上传失败"),
    ;

    /**
     * 异常代码
     */
    private final int code;

    /**
     * 异常信息
     */
    private final String msg;


    GlobalExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return msg;
    }


    public static GlobalExceptionCode getByCode(int code) {

        GlobalExceptionCode[] values = GlobalExceptionCode.values();

        for (GlobalExceptionCode value : values) {
            if (value.getCode() == code) {
                return value;
            }
        }

        throw new GlobalException(GlobalExceptionCode.NOT_FOUND_ENUM, String.format("未找到 [%s] 对应的异常类型!", code));
    }

}
