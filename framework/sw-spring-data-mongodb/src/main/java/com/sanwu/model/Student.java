package com.sanwu.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: Student
 * @Author: sanwu
 * @Date: 2021/7/23 1:46
 */
@Document
@Data
public class Student {

    @Id
    private String id;

    private String studentName;

    private String studentNo;

    private String hometown;
}
