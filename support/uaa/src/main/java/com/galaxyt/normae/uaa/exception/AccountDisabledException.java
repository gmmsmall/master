package com.galaxyt.normae.uaa.exception;

/**
 * 帐号被禁用时抛出的异常
 *
 * @author zhouqi
 * @version v1.0.0
 * @date 2020/5/21 11:29
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/5/21 11:29     zhouqi          v1.0.0           Created
 */
public class AccountDisabledException extends Exception {


    public AccountDisabledException() {
        super("帐号已被禁用");
    }

    public AccountDisabledException(String msg) {
        super(msg);
    }

}
