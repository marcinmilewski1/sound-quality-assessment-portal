import com.google.common.collect.Sets
import com.sqap.SqapApiApplication
import com.sqap.api.domain.audio.test.single.abx.AbxTestEntity
import com.sqap.api.domain.audio.test.single.TestEntity
import com.sqap.api.domain.audio.test.group.TestGroupRepository
import com.sqap.api.domain.audio.test.single.TestRepository
import com.sqap.api.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@ContextConfiguration(classes = SqapApiApplication)
class TestRepositoryIT extends Specification {

    @Autowired
    private TestRepository testRepository

    @Autowired
    private TestGroupRepository testGroupRepository
    @Autowired
    private UserRepository userRepository


    def "Should autowire generic class repository"() {
        expect:
        testRepository != null
    }

    def "Shuld get tests"() {
        given:
        def user = ObjectMother.user()
        def testGroupEntity = ObjectMother.testGroupWithoutTests()
        testGroupEntity.addTest(new AbxTestEntity(1, "abx", "desc", Sets.newHashSet()))
        user.addTestGroup(testGroupEntity)
        userRepository.save(user)

        when:
        def Collection<TestEntity> tests = testRepository.findAll()

        then:
        tests.size() == 1
    }

    


}

