package com.sqap.api.domain.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.Serializable;

@NoRepositoryBean
public interface SecuredJpaRepository<T extends AuditedEntity, ID extends Serializable>
        extends JpaTypedRepository<T, ID> {

    @PreAuthorize("hasPermission(#id, #targetType, 'owner')")
    default void doDeleteSecured(@Param("id") ID id, @Param("targetType") String targetType) {
        this.delete(id);
    }

    @PreAuthorize("hasPermission(#entity, 'owner')")
    default void doDeleteSecured(@Param("entity") T entity) {
        this.delete(entity);
    }

    @PreAuthorize("hasPermission(#entities, 'owner')")
    default void doDeleteSecured(@Param("entities") Iterable<? extends T> entities) {
        this.delete(entities);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    default void doDeleteAllSecured() {
        this.deleteAll();
    }

    @Override
    @RestResource(exported = false)
    void delete(ID id);

    @Override
    @RestResource(exported = false)
    void delete(T entity);

    @Override
    @RestResource(exported = false)
    void delete(Iterable<? extends T> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();

}
