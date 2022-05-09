package com.demo.domain.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class Policy {


    private Long id;
    private PolicyProduct product;
    private Date issueTime;

    /**
     * 工厂方法
     * @param product
     * @return
     */
    public static Policy create(PolicyProduct product){
        Policy policy = new Policy();
        policy.product = product;
        return policy;
    }

    /**
     * 保单出单
     */
    public void issue(Long id) {
        this.id = id;
        this.issueTime = new Date();
    }
}
