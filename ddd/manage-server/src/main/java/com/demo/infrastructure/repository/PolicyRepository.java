package com.demo.infrastructure.repository;

import com.demo.domain.model.Policy;
import org.springframework.stereotype.Repository;

public interface PolicyRepository {

    void create(Policy policy);
}
