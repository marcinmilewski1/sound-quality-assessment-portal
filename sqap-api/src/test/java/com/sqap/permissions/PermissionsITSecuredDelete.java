package com.sqap.permissions;

import com.google.common.collect.Sets;
import com.sqap.SqapApiApplication;
import com.sqap.api.domain.audio.test.base.Sex;
import com.sqap.api.domain.audio.test.base.TestGroupCharacteristics;
import com.sqap.api.domain.audio.test.single.abx.AbxTestEntity;
import com.sqap.api.domain.audio.test.base.TestGroupEntity;
import com.sqap.api.domain.audio.test.group.TestGroupRepository;
import com.sqap.api.domain.user.PersonalData;
import com.sqap.api.domain.user.UserEntity;
import com.sqap.api.domain.user.UserRepository;
import com.sqap.api.domain.user.role.RoleType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SqapApiApplication.class)
public class PermissionsITSecuredDelete {

    @Autowired
    private TestGroupRepository testGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "user")
    public void shouldDeleteWhenIsOwner() throws Exception {
        PersonalData personalData = new PersonalData(Sex.MALE, 12, false);
        UserEntity user = new UserEntity("user", "password1", true, Sets.newHashSet(RoleType.ROLE_USER), true, personalData);
        user = userRepository.save(user);
        TestGroupCharacteristics testGroupCharacteristics = new TestGroupCharacteristics(true, true, "res", 12);
        TestGroupEntity testGroupEntity = new TestGroupEntity("testGroup", "testdesc", testGroupCharacteristics, Sets.newHashSet(new AbxTestEntity(1, "abx", "desc", 1, null)));
        user.addTestGroup(testGroupEntity);
        user = userRepository.save(user);
        TestGroupEntity createdEntity = testGroupRepository.findAll().iterator().next();
        testGroupRepository.doDeleteSecured(createdEntity);
        Assert.assertTrue(testGroupRepository.count() == 0);
    }

}
