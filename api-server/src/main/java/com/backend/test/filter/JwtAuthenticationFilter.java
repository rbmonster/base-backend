//package com.backend.test.filter;
//
//import com.backend.test.exception.StandardAuthenticationException;
//import com.backend.test.security.PermissionGrantedAuthority;
//import com.backend.test.security.JwtAuthentication;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * <pre>
// * @Description:
// *
// * </pre>
// *
// * @version v1.0
// * @ClassName: JwtAuthenticationFilter
// * @Author: sanwu
// * @Date: 2021/7/12 23:14
// */
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private RequestMatcher requiresAuthenticationRequestMatcher;
//
//    public JwtAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher){
//
//    }
//  @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader(headerName);
//        if (StringUtils.isEmpty(token)) {
//            throw new StandardAuthenticationException("unAuth missing the header");
//        }
//        List<PermissionGrantedAuthority> permissionGrantedAuthorityList = Arrays.asList(new PermissionGrantedAuthority("USER"));
//        JwtAuthentication standardAuthentication = new JwtAuthentication(token, permissionGrantedAuthorityList);
//        return this.getAuthenticationManager().authenticate(standardAuthentication);
//    }
//
//
//}
