package com.demo.domain.service;

import com.demo.domain.model.Policy;
import com.demo.infrastructure.repository.PolicyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;

@AllArgsConstructor
@Service
public class PolicyService {

    private final PolicyRepository policyRepository;



    public void issue(Policy policy) {
//        if(!insureUnderwriteService.underwrite(policy)){
//            throw new BizException("核保失败");
//        }
//        policy.issue(IdGenerator());
        //保存信息
        //policyRepository.save(policy);
        policyRepository.create(policy);
    }
}
