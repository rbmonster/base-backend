package com.base.once.security;

import com.base.once.security.auth.AccountCredentials;
import com.base.once.security.auth.TokenBasedAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: DyFilter
 * @Author: sanwu
 * @Date: 2021/1/27 12:58
 */
@Slf4j
@Component
public class DyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("this is dy filter");
        List<GrantedAuthority> authorities = new ArrayList<>();
        TokenBasedAuthentication authentication = new TokenBasedAuthentication(new AccountCredentials("ship", authorities));
//        authentication.setToken(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
