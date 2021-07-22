import com.sanwu.MongoSpringApplication;
import com.sanwu.model.Student;
import com.sanwu.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <pre>
 * @Description:
 *
 * </pre>
 *
 * @version v1.0
 * @ClassName: TestMongodb
 * @Author: sanwu
 * @Date: 2021/7/23 1:49
 */
@Slf4j
@SpringBootTest(classes = MongoSpringApplication.class)
@RunWith(SpringRunner.class)
public class TestMongodb {


    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void insert() {
        Student student = new Student();
        student.setHometown("shanghai");
        student.setStudentName("xiaoming");
        student.setStudentNo("20191012012");
        Student insert = studentRepository.insert(student);
        log.info("result: {}",insert);

    }
}
