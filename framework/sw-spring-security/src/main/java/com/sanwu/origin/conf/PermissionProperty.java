package com.sanwu.origin.conf;

import com.sanwu.origin.model.UrlPermissionModel;
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
@ConfigurationProperties("base")
public class PermissionProperty {

    String[] permitAllList;

    List<UrlPermissionModel> permissionList;

}
