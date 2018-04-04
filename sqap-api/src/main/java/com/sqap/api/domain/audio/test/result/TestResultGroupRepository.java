package com.sqap.api.domain.audio.test.result;


import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "resultGroups", path="resultGroups", exported = false)
public interface TestResultGroupRepository extends SecuredJpaRepository<TestResultGroupEntity, Long> {
    @Override
    default String getTargetType() {
        return TestResultGroupEntity.class.getTypeName();
    }

    @Override
    @RestResource(exported = false)
    TestResultGroupEntity save(TestResultGroupEntity s);
}
