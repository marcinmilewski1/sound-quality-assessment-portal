package com.sqap.api.domain.audio.test.result.abx;

import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AbxTestResultRepository extends SecuredJpaRepository<AbxTestResultEntity, Long> {
    @Override
    default String getTargetType() {
        return AbxTestResultEntity.class.getTypeName();
    }
}
