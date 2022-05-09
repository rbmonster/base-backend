import com.dmo.DemoApplication;
import com.dmo.mapstruct.*;
import io.github.zhaord.mapstruct.plus.IObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class MapStructTest {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private IObjectMapper iObjectMapper;


    
    @Test
    public void testConvert() {
        Car car = new Car( "Morris", CarType.TRUCK,5 );

        //when
        CarDTO carDto = CarMapper.INSTANCE.carToCarDto( car );
        Assert.notNull(carDto);
    }

    @Test
    public void testSpringConvert(){
        CarVO car = new CarVO( "Morris", CarType.TRUCK,5 );
        CarVO convert = conversionService.convert(car, CarVO.class);
        Assert.notNull(convert);
    }

    @Test
    public void testGitHubConvert() {
        CarDTO car = new CarDTO( "Morris", CarType.TRUCK,5 );
        CarVO carVO = iObjectMapper.map(car, CarVO.class);
        Assert.notNull(carVO);
    }
}
