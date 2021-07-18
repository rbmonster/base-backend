package com.sanwu.origin.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: ISignInAuthentication
 * @Author: sanwu
 * @Date: 2021/7/15 0:30
 */
public interface ISignInAuthenticationService {

    Authentication authenticate(UsernamePasswordAuthenticationToken authenticationToken);
}
