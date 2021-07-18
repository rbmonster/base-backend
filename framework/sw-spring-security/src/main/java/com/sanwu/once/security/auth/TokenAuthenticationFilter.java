package com.sanwu.once.security.auth;

import com.sanwu.once.security.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private TokenHelper tokenHelper;

//    @Autowired
//    private IPermissionService permissionService;

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String userInfo;
        String authToken = tokenHelper.getToken(request);

        if (authToken != null) {
            // get username from token
            userInfo = tokenHelper.getUserInfoFromToken(authToken);
            System.out.println(userInfo);
            if (userInfo != null) {
                if (tokenHelper.validateToken(authToken)) {
                    String[] userInfos = userInfo.split("~");

                    String refreshToken = tokenHelper.refreshToken(authToken);
                    response.addHeader("refreshtoken", refreshToken);
                    // create authentication
                    String userName = userInfos[0];
                    List<GrantedAuthority> authorities = buildAuthorities(userName);
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(new AccountCredentials(userName, authorities));
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //保存session 信息
//                    if (userInfos.length > 3 && "user".equalsIgnoreCase(userInfos[3])) {
//                        // 普通用户登录
//                        User user = new User();
//                        user.setUserId(Integer.parseInt(userInfos[2]));
//                        user.setUsername(userName);
//                        SessionUtil.setUser(request, user);
//                    } else {
//                        AuthenticatedUser user = new AuthenticatedUser();
//                        user.setToken(authToken);
//                        user.setUserId(Integer.parseInt(userInfos[2]));
//                        user.setRoleId(Integer.parseInt(userInfos[1]));
//                        user.setUsername(userName);
//                        if (userInfos.length > 3 && StringUtils.isNotBlank(userInfos[3])) {
//                            user.setChiefId(Integer.valueOf(userInfos[3]));
//                        }
//                        SessionUtil.setAuthenticatedUser(request, user);
//                    }

                }
            }
        }
        chain.doFilter(request, response);
    }

    private List<GrantedAuthority> buildAuthorities(String userName) {
//        List<String> permissionNames = permissionService.getPermissionNames(userName);
        List<String> permissionNames = new ArrayList<>();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permissionName : permissionNames) {
            GrantedAuthority authority = new PermissionGrantedAuthority(permissionName);
            authorities.add(authority);
        }

        return authorities;
    }

}
