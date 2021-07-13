package com.backend.test.service.impl;

import com.backend.test.service.ISignInService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: DefaultSignInServiceImpl
 * @Author: sanwu
 * @Date: 2021/7/12 23:48
 */
@Service
public class DefaultSignInServiceImpl implements ISignInService {

    @Override
    public boolean signIn(String username, String password) {
        if ("username".equals(username) && "112233".equals(password)) {
            return true;
        }
        return false;
    }
}
