package com.sqap.api.domain.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void properInjectTest() throws Exception {
        assertThat(userService, notNullValue());
    }

    @Test
    public void lookUpIfEmptyDb() throws Exception {
        List<UserEntity> users = (List<UserEntity>) userService.getRepository().findAll();
        assertThat(users.size(), is(0));
    }

}

