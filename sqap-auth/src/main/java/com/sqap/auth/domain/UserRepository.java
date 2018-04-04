package com.sqap.auth.domain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

public interface UserRepository {
    UserDetails loadUserByUsername(String username);
}
