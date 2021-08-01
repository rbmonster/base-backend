package com.sw.rpc.register;

import com.sw.rpc.domain.ServiceMateData;
import org.springframework.context.ApplicationContext;

public interface Register {

    void register(ServiceMateData serviceMateData);

    void checkBeforeRegister();
}
