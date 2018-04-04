import com.google.common.collect.Sets
import com.sqap.api.domain.audio.test.single.abx.AbxTestEntity
import com.sqap.api.domain.audio.test.base.TestGroupEntity
import com.sqap.api.domain.user.UserEntity
import com.sqap.api.domain.user.role.RoleType

class ObjectMother {
    def static UserEntity user() {
        return new UserEntity("user", "password1", true, Sets.newHashSet(RoleType.ROLE_USER), true)
    }
    def static TestGroupEntity testGroupEntity() {
        return new TestGroupEntity("testGroup", "testdesc", Sets.newHashSet(new AbxTestEntity(1, "abx", "desc", null)))
    }
    def static TestGroupEntity testGroupWithoutTests() {
        return new TestGroupEntity("testGroup", "testdesc", Sets.newHashSet())
    }
}