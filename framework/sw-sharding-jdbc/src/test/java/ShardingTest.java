import com.sw.shardingjdbc.ShardingJdbcApplication;
import com.sw.shardingjdbc.dao.NotificationEventDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@AutoConfigureMockMvc
@SpringBootTest(classes = ShardingJdbcApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ShardingTest {


    @Autowired
    private NotificationEventDao notificationEventDao;

    @Test
    public void test() {
        System.out.println(notificationEventDao.findUsers());
    }

    @Test
    public void testxml() {
        System.out.println(notificationEventDao.selectByIds());
    }

}
