package com.manage.client;

import com.manage.facade.ManageInterface;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "manage")
public interface ManageClient extends ManageInterface {
}
