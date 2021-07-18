package com.sanwu.origin.authentication.token;

import com.sanwu.origin.model.BaseTokenAuthentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: ITokenAuthenticationConverter
 * @Author: sanwu
 * @Date: 2021/7/18 15:59
 */
public interface ITokenAuthenticationConverter extends AuthenticationConverter {

    BaseTokenAuthentication convert(HttpServletRequest request);
}
