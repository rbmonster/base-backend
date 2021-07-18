package com.sanwu.origin.authentication.handler;

import com.alibaba.fastjson.JSON;
import com.sanwu.origin.model.TokenResponse;
import com.sanwu.origin.service.ITokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: TestHandler
 * @Author: sanwu
 * @Date: 2021/7/9 1:57
 */
@Component
public class SignInAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ITokenAuthenticationService tokenAuthenticationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = tokenAuthenticationService.generateToken(String.valueOf(authentication.getPrincipal()));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        TokenResponse tokenResponse = new TokenResponse(token);
        try (PrintWriter writer = response.getWriter()){
            writer.write(JSON.toJSONString(tokenResponse));
        }
    }
}
