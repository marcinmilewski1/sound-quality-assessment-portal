package com.sqap.api.domain.audio.test.result.mushra;

import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface MushraTestResultRepository extends SecuredJpaRepository<MushraTestResultEntity, Long> {
    @Override
    default String getTargetType() {
        return MushraTestResultEntity.class.getTypeName();
    }
}
