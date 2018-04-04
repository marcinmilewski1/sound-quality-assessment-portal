package com.sqap.api.config.permissions.resolver;

import com.sqap.api.domain.base.AuditedEntity;
import com.sqap.api.domain.base.GenericRepositoryService;
import com.sqap.api.domain.user.UserEntity;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

public class OwnershipPermissionResolver implements PermissionResolver {

    private final ListableBeanFactory beanFactory;
    private final GenericRepositoryService genericRepositoryService;

    public OwnershipPermissionResolver(ListableBeanFactory beanFactory, GenericRepositoryService genericRepositoryService) {
        this.beanFactory = beanFactory;
        this.genericRepositoryService = genericRepositoryService;
    }

    @Override
    public boolean resolve(Authentication authentication, AuditedEntity target) {
        String sid =  ((UserDetails)authentication.getPrincipal()).getUsername();

//        List<QueryMethod> queryMethodList= repositories.getQueryMethodsFor(targetType);
//        AuditedEntity auditedEntity = (AuditedEntity) jpaRepository.findOne(targetId);

        if (target instanceof UserEntity) { // user entity has createdBy field null
            return sid.equals(((UserEntity) target).getUsername());
        }
        else {
            return sid.equals(((AuditedEntity)target).getCreatedBy());
        }
    }

    @Override
    public boolean resolve(String targetType, Authentication authentication, Serializable targetId) {
        String sid =  ((UserDetails)authentication.getPrincipal()).getUsername();

        AuditedEntity auditedEntity = (AuditedEntity) genericRepositoryService.findById(targetType, (Long) targetId);

        if (auditedEntity instanceof UserEntity) {
            return ((UserEntity) auditedEntity).getUsername().equals(sid);
        }
        else {
            return auditedEntity.getCreatedBy().equals(sid);
        }
    }
}
