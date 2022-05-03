package com.demo.domain.anticorruption.product;

import com.demo.domain.model.PolicyProduct;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


    public PolicyProduct getById(String productId) {
        return new PolicyProduct();
    }

}
