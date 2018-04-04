package com.sqap.api.domain.audio.test.result.mushra;

import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface MushraTestResultGroupRepository extends SecuredJpaRepository<MushraTestResultGroupEntity, Long> {
    @Override
    default String getTargetType() {
        return MushraTestResultGroupEntity.class.getTypeName();
    }
}
