import com.sqap.SqapApiApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@Transactional
@ContextConfiguration(classes = SqapApiApplication)
class AdHocIT extends Specification {


}