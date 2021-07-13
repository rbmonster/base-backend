package com.backend.test.service;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: ISignInService
 * @Author: sanwu
 * @Date: 2021/7/12 23:46
 */
public interface ISignInService {

    boolean signIn(String username, String password);
}
