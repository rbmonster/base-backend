import com.example.demo.EsDemoApplication;
import com.example.demo.entity.TradeContract;
import com.example.demo.repository.TradeContractRepository;
import com.example.demo.service.TradeContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = EsDemoApplication.class)
@RunWith(SpringRunner.class)
public class EsDemoApplicationTests {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    TradeContractService tradeContractService;

    @Autowired
    TradeContractRepository tradeContractRepository;


    @Test
    public void trade() {
        List<TradeContract> closed = tradeContractRepository.findByStatus("Closed");
        System.out.println(closed);
    }
}
