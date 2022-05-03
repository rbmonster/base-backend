package com.demo.application.impl;

import com.demo.application.ManageProductService;
import com.demo.application.pojo.request.ManageBaseRequest;
import com.demo.domain.service.factory.PolicyFactory;
import com.demo.domain.model.Policy;
import com.demo.domain.service.PolicyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ManageProductServiceImpl implements ManageProductService {

    private final PolicyFactory policyFactory;
    private final PolicyService policyService;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public boolean isAvailable(ManageBaseRequest request) {
        Policy policy = policyFactory.createPolicy(request.getManageId());

        //出单流程控制
        policyService.issue(policy);

//        PolicyIssuedMessage message = new PolicyIssuedMessage();
//        message.setPolicyId(policy.getId());
//        MQPublisher.publish(MQConstants.INSURANCE_TOPIC, MQConstants.POLICY_ISSUED_TAG, message);

        return policy.getId().toString().isEmpty();
    }

}

