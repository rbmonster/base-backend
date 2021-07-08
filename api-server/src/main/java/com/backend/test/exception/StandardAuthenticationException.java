package com.backend.test.exception;

import org.springframework.security.core.Authentication;
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
public class StandardAuthenticationException extends AuthenticationException {
    public StandardAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public StandardAuthenticationException(String msg) {
        super(msg);
    }
}
