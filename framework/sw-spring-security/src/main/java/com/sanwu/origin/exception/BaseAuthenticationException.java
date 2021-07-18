package com.sanwu.origin.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: StandardAuthenticationException
 * @Author: sanwu
 * @Date: 2021/7/9 0:38
 */
public class BaseAuthenticationException extends AuthenticationException {

    public BaseAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public BaseAuthenticationException(String msg) {
        super(msg);
    }
}
