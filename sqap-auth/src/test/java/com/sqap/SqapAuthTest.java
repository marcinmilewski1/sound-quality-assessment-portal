package com.sqap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.util.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class SqapAuthTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Test
    public void contextLoads() {
        Assert.notNull(jdbcTemplate);
        Assert.notNull(authenticationManager);
        Assert.notNull(userDetailsService);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowUserNotFoundExceptionWhenThereIsNoUser() {
        userDetailsService.loadUserByUsername("test");
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                    scripts = "classpath:scripts/db_user_insert.sql"),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                    scripts = "classpath:scripts/db_user_delete.sql")
    })
    public void ShouldReturnUserDetailsAndAuthenticateWhenThereIsUser() {
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "users", "username = 'test' and id = -1"), is(1));
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "user_roles", "username = 'test' and id = -1"), is(1));

        UserDetails userDetails = userDetailsService.loadUserByUsername("test");

        assertThat(userDetails.getUsername(), is("test"));
        assertThat(userDetails.getPassword(), is("pass"));
        assertThat(userDetails.isEnabled(), is(true));
        assertThat(userDetails.getAuthorities().size(), is(1));
        assertThat(userDetails.getAuthorities().stream().allMatch(o -> o.getAuthority().equals("ROLE_TEST")), is(true));

        assertThat(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword())).isAuthenticated(), is(true));
    }

}

