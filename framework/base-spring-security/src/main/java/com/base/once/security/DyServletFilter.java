package com.base.once.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: DyServletFilter
 * @Author: sanwu
 * @Date: 2021/1/27 13:56
 */
@Slf4j
@Order(2)
@WebFilter(filterName = "FirstFilter",urlPatterns = "/*")
public class DyServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("FirstFilter  1");
        log.info("before: {} " , servletResponse);
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("after: {}",  servletResponse);
        log.info("First filter 2");
    }

    @Override
    public void destroy() {

    }

    public static void main(String[] args) {
        SecureRandom  secureRandom = new SecureRandom();
        byte[] server_salt = new byte[36];
        secureRandom.nextBytes(server_salt);

        System.out.println(new String(server_salt));
    }
}
