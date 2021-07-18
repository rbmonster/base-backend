package com.sanwu.origin.authentication.token;

import com.sanwu.origin.model.BaseTokenAuthentication;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: DefaultAuthenticationConverter
 * @Author: sanwu
 * @Date: 2021/7/18 15:22
 */
public class DefaultAuthenticationConverter implements ITokenAuthenticationConverter {

    public static final String AUTHENTICATION_SCHEME_BEARER = "Bearer";

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;

    private Charset credentialsCharset = StandardCharsets.UTF_8;

    public DefaultAuthenticationConverter(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    @Override
    public BaseTokenAuthentication convert(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header == null) {
            return null;
        }

        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BEARER)) {
            return null;
        }

        if (header.equalsIgnoreCase(AUTHENTICATION_SCHEME_BEARER)) {
            throw new BadCredentialsException("Empty basic authentication token");
        }

        String token = header.substring(7);

        if (token.split("\\.").length !=3) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        BaseTokenAuthentication result  = new BaseTokenAuthentication(token);
        result.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return result;
    }
}
