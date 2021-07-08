package com.backend.test.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: PermissionProperty
 * @Author: sanwu
 * @Date: 2021/7/9 0:00
 */
@Data
@Configuration
@ConfigurationProperties("standard")
public class PermissionProperty {

    String[] permitAllList;

}
