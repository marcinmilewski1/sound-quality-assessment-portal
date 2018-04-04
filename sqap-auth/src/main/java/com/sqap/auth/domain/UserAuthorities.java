package com.sqap.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAuthorities {
    private final String username;
    private final String role;
}
