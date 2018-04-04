import com.google.common.collect.Sets
import com.sqap.SqapApiApplication
import com.sqap.api.domain.audio.test.single.abx.AbxTestEntity
import com.sqap.api.domain.audio.test.base.TestGroupEntity
import com.sqap.api.domain.audio.test.group.TestGroupRepository
import com.sqap.api.domain.user.UserEntity
import com.sqap.api.domain.user.UserRepository
import com.sqap.api.domain.user.role.RoleType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@Transactional
@ContextConfiguration(classes = SqapApiApplication)
class JpaAuditTester extends Specification {

    @Autowired
    private TestGroupRepository testGroupRepository

    @Autowired
    private UserRepository userRepository

    UserEntity user

    def setup() {
        user = new UserEntity("user", "password1", true, Sets.newHashSet(RoleType.ROLE_USER), registerStatementConfirmed)
        user = userRepository.save(user)
    }

    def "when user creation should created by empty"() {
        expect:
        userRepository.findByUsername("user").get().getCreatedDate() != null
        userRepository.findByUsername("user").get().getCreatedBy() == null
    }

    @WithMockUser(username="user")
    def "should audit on create and update"() {
        given:
        TestGroupEntity testGroupEntity = new TestGroupEntity("testGroup", "testdesc", Sets.newHashSet(new AbxTestEntity(1, "abx", "desc", null)))

        when:
        user.addTestGroup(testGroupEntity)
        userRepository.save(user)

        def sut = testGroupRepository.findAll()

        then:
        sut.size() == 1
        sut.first().createdBy.equals("user")
        sut.first().createdDate != null
        sut.first().updatedBy.equals("user")
        sut.first().updatedDate != null
    }



}