package com.dmo.utils;

import com.dmo.service.AttaIDSequencer;

/**
 * @author songtao
 * @date 2021/11/20
 */
public class AttaIdUtils {

    public static final AttaIDSequencer ID_SEQUENCER = SpringUtil.getBean(AttaIDSequencer.class);

    private AttaIdUtils() {
        throw new UnsupportedOperationException();
    }
    public static String getByPrgetByProdCodeodCode(String prodCode) {
        return ID_SEQUENCER.createProductCode();
    }

    public static class DomainCode{
        public static final String MARKETING = "MARKETING";
    }



}
