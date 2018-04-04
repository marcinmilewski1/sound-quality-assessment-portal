package com.sqap.api.config.permissions.resolver;


import com.sqap.api.domain.base.AuditedEntity;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public interface PermissionResolver {
    boolean resolve(Authentication authentication, AuditedEntity target);

    boolean resolve(String targetType, Authentication authentication, Serializable targetId);
}
