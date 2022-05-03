package com.demo.domain.service.factory;

import com.demo.domain.anticorruption.product.ProductService;
import com.demo.domain.model.Policy;
import com.demo.domain.model.PolicyProduct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PolicyFactory {

    private final ProductService productService;

    /**
     * 从各种数据来源查询直接能查到的前置数据，填充到 policy 中
     * @param productId
     * @return
     */
    public Policy createPolicy(String productId) {
        PolicyProduct product = productService.getById(productId);
        //其他填充数据，这里调用了聚合自身的静态工厂方法
        Policy policy = Policy.create(product);
        return policy;
    }
}
