package com.demo.application;

import com.demo.application.pojo.request.ManageBaseRequest;

public interface ManageProductService {

    boolean isAvailable(ManageBaseRequest manageBaseRequest);
}
