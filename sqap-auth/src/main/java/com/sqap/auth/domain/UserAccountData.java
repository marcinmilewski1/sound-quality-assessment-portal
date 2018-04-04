package com.sqap.auth.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class UserAccountData {
    private final String username;
    private final String password;
    private final boolean enabled;

}
