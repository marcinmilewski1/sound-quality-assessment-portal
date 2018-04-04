package com.sqap.api.domain.audio.test.result;

import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "results", path="results", exported = false)
public interface TestResultRepository extends SecuredJpaRepository<TestResultEntity, Long> {
    @Override
    default String getTargetType() {
       return TestResultEntity.class.getTypeName();
    }

    @Override
    @RestResource(exported = false)
    TestResultEntity save(TestResultEntity s);

}
