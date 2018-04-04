package com.sqap.api.domain.user.role;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleType {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_TEST;

    @JsonCreator
    public static RoleType forValue(String value) {
        return RoleType.valueOf(value);
    }
}
