package com.sqap.api.config.permissions;

import com.sqap.api.config.permissions.factory.PermissionResolverFactory;
import com.sqap.api.domain.PermissionType;
import com.sqap.api.domain.base.AuditedEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.Serializable;

@Slf4j
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final PermissionResolverFactory permissionResolverFactory;

    @Autowired
    public CustomPermissionEvaluator(PermissionResolverFactory permissionResolverFactory) {
        this.permissionResolverFactory = permissionResolverFactory;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Assert.notNull(userDetails, "User details cannot be null");
        Assert.notNull(targetDomainObject, "Target object cannot be null");
        Assert.notNull(permission, "permission cannot be null");
        log.debug("Permmission: " + permission + " check on: " + targetDomainObject + " for user: " + userDetails.getUsername());

        PermissionType permissionType = PermissionType.valueOf(((String) permission).toUpperCase());
        return permissionResolverFactory.getPermissionResolver(permissionType).resolve(authentication, (AuditedEntity) targetDomainObject);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Assert.notNull(userDetails, "User details cannot be null");
        Assert.notNull(targetType, "Target object cannot be null");
        Assert.notNull(targetId, "Target id cannot be null");
        log.debug("Permmission: " + permission + " check on: " + targetType + "id: " + targetId + " for user: " + userDetails.getUsername());

        PermissionType permissionType = PermissionType.valueOf(((String) permission).toUpperCase());
        return permissionResolverFactory.getPermissionResolver(permissionType).resolve(targetType, authentication, targetId);
    }
}

//    private boolean hasPrivilege(Authentication auth, String targetType, PermissionType permission) {
//
//
////        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
////            if (grantedAuth.getAuthority().startsWith(targetType)) {
////                if (grantedAuth.getAuthority().contains(permission)) {
////                    return true;
////                }
////            }
////        }
//        return false;
//    }
//}
//
////class ClassTypeResolver {
////    public static String resolve(Object object) {
////        if (AopUtils.isJdkDynamicProxy(object)) {
////            try {
////                return ((SimpleJpaRepository) ((Advised)object).getTargetSource().getTarget()).getDomainClass().getCanonicalName();
////            } catch (Exception e) {
////                return null;
////            }
////        } else {
////            return ((SimpleJpaRepository) object).getDomainClass().getCanonicalName();
////        }
////    }
////}
