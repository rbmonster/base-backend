package com.sanwu.origin.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: BasicSecurityProperty
 * @Author: sanwu
 * @Date: 2021/7/9 0:17
 */
@Data
@ConfigurationProperties("jwt")
@Configuration
public class BaseSecurityProperty {

    private String header;

    private int expiresIn;

    private String secret;
}
