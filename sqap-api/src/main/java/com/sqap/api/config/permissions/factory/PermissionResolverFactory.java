package com.sqap.api.config.permissions.factory;

import com.sqap.api.config.permissions.resolver.OwnershipPermissionResolver;
import com.sqap.api.config.permissions.resolver.PermissionResolver;
import com.sqap.api.domain.PermissionType;
import com.sqap.api.domain.base.GenericRepositoryService;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionResolverFactory {

    @Autowired
    private ListableBeanFactory listableBeanFactory;

    @Autowired
    private GenericRepositoryService genericRepositoryService;

    public PermissionResolver getPermissionResolver(PermissionType permissionType) {
        switch (permissionType) {
            case OWNER:
                return new OwnershipPermissionResolver(listableBeanFactory, genericRepositoryService);
            default:
                throw new IllegalArgumentException("No permission resolver for " + permissionType);
        }
    }
}
