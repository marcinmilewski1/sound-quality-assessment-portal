import com.google.common.collect.Sets
import com.sqap.SqapApiApplication
import com.sqap.api.domain.audio.test.single.abx.AbxTestEntity
import com.sqap.api.domain.audio.test.base.TestGroupEntity
import com.sqap.api.domain.audio.test.group.TestGroupRepository
import com.sqap.api.domain.user.UserEntity
import com.sqap.api.domain.user.UserRepository
import com.sqap.api.domain.user.role.RoleType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DataJpaTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@ContextConfiguration(classes = SqapApiApplication)
class TestGroupRepositoryIT extends Specification {

    @Autowired
    private TestGroupRepository testGroupRepository

    @Autowired
    private UserRepository userRepository

    def "should find unparticipated by user"() {

        given:
        UserEntity user = new UserEntity("user", "password1", true, Sets.newHashSet(RoleType.ROLE_USER), registerStatementConfirmed)
        TestGroupEntity testGroupEntity = new TestGroupEntity("testGroup", "testdesc", Sets.newHashSet(new AbxTestEntity(1, "abx", "desc", null)))
        user.addTestGroup(testGroupEntity)
        userRepository.save(user)

        when:
        def unfinishedTestGroupList = testGroupRepository.findNotParticipatedAndNotFinished(user.getId())

        then:
        unfinishedTestGroupList.size() == 1
        unfinishedTestGroupList.first().owner.username.equals("user")

        when:
        unfinishedTestGroupList = testGroupRepository.findNotParticipatedAndNotFinished(500)

        then:
        unfinishedTestGroupList.size() == 1

        when:
        testGroupEntity.setFinished(true)
        testGroupRepository.save(testGroupEntity)
        unfinishedTestGroupList = testGroupRepository.findNotParticipatedAndNotFinished(user.getId())

        then:
        unfinishedTestGroupList.size() == 0

        when: // bidirectional many to many
        testGroupEntity.setFinished(false)
        testGroupRepository.save(testGroupEntity)
        user.addParticipated(testGroupEntity)
        userRepository.save(user)

        def participatedUser = userRepository.findOne(user.getId())
        unfinishedTestGroupList = testGroupRepository.findNotParticipatedAndNotFinished(user.getId())

        then:
        participatedUser.getParticipated().size() == 1
        unfinishedTestGroupList.size() == 0




    }

}