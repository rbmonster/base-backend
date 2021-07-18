package com.sanwu.origin.service;

import com.sanwu.origin.model.BaseTokenAuthentication;
import org.springframework.security.core.Authentication;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: ITokenAuthenticationService
 * @Author: sanwu
 * @Date: 2021/7/15 0:55
 */
public interface ITokenAuthenticationService {

    String generateToken(String subject);

    Authentication authenticate(BaseTokenAuthentication baseTokenAuthentication);
}
