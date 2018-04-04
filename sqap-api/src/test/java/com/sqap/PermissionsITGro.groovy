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
class PermissionsITGro extends Specification {

    @Autowired
    private TestGroupRepository testGroupRepository

    @Autowired
    private UserRepository userRepository

    UserEntity user

    def setup() {
        user = new UserEntity("user", "password1", true, Sets.newHashSet(RoleType.ROLE_USER), registerStatementConfirmed)
        user = userRepository.save(user)
    }

    @WithMockUser(username="user")
    // TODO BUG - WRONG METHOD INVOKE
    def "when owner permission needed and is owner"() {
        given:
        TestGroupEntity testGroupEntity = new TestGroupEntity("testGroup", "testdesc", Sets.newHashSet(new AbxTestEntity(1, "abx", "desc", null)))
        user.addTestGroup(testGroupEntity)
        user = userRepository.save(user)

        when:
        def entity = testGroupRepository.findAll().iterator().fi
        testGroupRepository.delete(testGroupEntity)
        then:
        testGroupRepository.findAll().size() == 1

        when:
        def sut = testGroupRepository.findAll()
        then:
        sut.size() == 0
    }
}