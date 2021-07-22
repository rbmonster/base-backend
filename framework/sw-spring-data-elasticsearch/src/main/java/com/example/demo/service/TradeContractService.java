package com.example.demo.service;

import com.example.demo.entity.TradeContract;
import com.example.demo.repository.TradeContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: TradeContractService
 * @Author: sanwu
 * @Date: 2021/6/20 0:02
 */

@Service
public class TradeContractService {

    @Autowired
    private TradeContractRepository tradeContractRepository;


    public Page<TradeContract> find(String status) {
        Pageable pageable = PageRequest.of(1, 10);
        return tradeContractRepository.findByName(status, pageable);
    }

}
