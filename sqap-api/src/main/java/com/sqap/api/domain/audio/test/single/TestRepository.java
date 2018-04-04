package com.sqap.api.domain.audio.test.single;

import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "testGroups", path="groups")
@RepositoryRestResource(collectionResourceRel = "tests", path="tests")
//@Repository
public interface TestRepository extends SecuredJpaRepository<TestEntity, Long> {
    @Override
    default String getTargetType() {
        return TestEntity.class.getTypeName();
    }

}
