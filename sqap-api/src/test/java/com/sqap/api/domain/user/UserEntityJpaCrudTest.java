package com.sqap.api.domain.user;

import com.google.common.collect.Sets;
import com.sqap.api.domain.audio.test.base.Sex;
import com.sqap.api.domain.user.role.RoleType;
import com.sqap.base.JpaEntityTest;
import org.springframework.cache.annotation.Cacheable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserEntityJpaCrudTest extends JpaEntityTest<UserEntity, UserRepository, Long> {

    @Override
    @Cacheable
    public UserEntity getEntity() {
        PersonalData personalData = new PersonalData(Sex.MALE, 12, false);
        return new UserEntity("test", "pass", true, Sets.newHashSet(RoleType.ROLE_TEST), true, personalData);
    }

    @Override
    public void _afterSave(UserEntity entity) {
        assertThat(entity.getCreatedDate(), is(notNullValue()));
        assertThat(entity.getVersion(), is(notNullValue()));
        assertThat(entity.getRoles().size(), is(1));
        assertThat(entity.getRoles().stream().findFirst().get().getUser().getUuid(), is(entity.getUuid()));
    }

    @Override
    public void _update(UserEntity entity) {
        entity.setPassword("newPass");
        entity.setEnabled(false);
    }

    @Override
    public void _afterUpdate(UserEntity entity) {
        assertThat(entity.getPassword(), is("newPass"));
        assertThat(entity.getEnabled(), is(false));
    }

}
