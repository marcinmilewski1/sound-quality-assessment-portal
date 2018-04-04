package com.sqap.auth.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String SELECT_USER_SQL = "select username, password, enabled from users where username = ?";
    private final String SELECT_USER_AUTHORITIES_SQL = "select username, role from user_roles where username= ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccountData userAccountData = this.jdbcTemplate.queryForObject(SELECT_USER_SQL, new Object[]{username}, (rs, rowNum) ->
                new UserAccountData(rs.getString("username"), rs.getString("password"), rs.getBoolean("enabled"))
        );

        List<SimpleGrantedAuthority> grantedAuthorities = this.jdbcTemplate.query(SELECT_USER_AUTHORITIES_SQL, new Object[]{username},
                (rs, rowNum) -> new SimpleGrantedAuthority(rs.getString("role"))
        );

        return new User(userAccountData.getUsername(), userAccountData.getPassword(), userAccountData.isEnabled(), true, true, true, grantedAuthorities);
    }
}
