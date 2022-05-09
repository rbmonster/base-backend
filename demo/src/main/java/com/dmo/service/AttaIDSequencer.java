package com.dmo.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AttaIDSequencer {

    public String createProductCode() {
        return UUID.randomUUID().toString();
    }
}
