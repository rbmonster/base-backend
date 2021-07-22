package com.sanwu.repository;

import com.sanwu.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: StudentRepository
 * @Author: sanwu
 * @Date: 2021/7/23 1:44
 */
public interface StudentRepository extends MongoRepository<Student, String> {
}
