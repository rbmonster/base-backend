package com.example.demo.repository;

import com.example.demo.entity.TradeContract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: TradeContractRepository
 * @Author: sanwu
 * @Date: 2021/6/20 0:01
 */

@Repository
public interface TradeContractRepository extends ElasticsearchRepository<TradeContract, String> {

    @Query("{\"match\": {\"status\": {\"query\": \"?0\"}}}")
    Page<TradeContract> findByName(String status, Pageable pageable);


    List<TradeContract> findByStatus(String status);
}
