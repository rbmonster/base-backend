package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: TradeContract
 * @Author: sanwu
 * @Date: 2021/6/19 23:54
 */
@Data
@TypeAlias("TradeContract")
@Document(
        indexName = "sw_test.trade_contract",
        createIndex = false
)
public class TradeContract {


    @Id
    private String id;

    private Double amount;

    @Field(name = "contract_id")
    private String contractId;

    @Field(name = "created_time", type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd")
    private Date createdTime;

    @Field(name = "modified_time", type = FieldType.Date,format = DateFormat.custom, pattern = "yyyy-MM-dd")
    private Date modifiedTime;

    @Field(name = "custom_id")
    private String customId;

    private boolean deleted;

    @Field(name = "exp_currency")
    private String expCurrency;

    @Field(name = "export_country_code")
    private String exportCountryCode;

    private String extra;

    @Field(name = "invoice_no")
    private String invoiceNo;

    private String note;

    @Field(name = "product_name")
    private String productName;

    @Field(name = "product_quantity")
    private Long productQuantity;

    @Field(name = "product_quantity_unit")
    private String productQuantityUnit;

    private String status;

    @Field(name = "test_add")
    private String testAdd;

}
