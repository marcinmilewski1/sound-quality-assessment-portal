package com.sqap.api.domain.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface JpaTypedRepository<T extends AuditedEntity, ID extends Serializable>
        extends JpaRepository<T, ID> {

    String getTargetType();
}
