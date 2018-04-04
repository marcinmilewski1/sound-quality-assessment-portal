package com.sqap.api.domain.audio.test.sample;

import com.sqap.api.domain.base.SecuredJpaRepository;
import com.sqap.api.domain.file.resource.ResourceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(collectionResourceRel = "testSamples", path="samples")
public interface TestSampleRepository extends SecuredJpaRepository<TestSampleEntity, Long> {
    @Override
    default String getTargetType() {
        return TestSampleEntity.class.getTypeName();
    }

    @Query(value = "select ts.resource from TestSampleEntity ts join ts.resource where ts.id = :sampleId")
    @Transactional
    ResourceEntity getFile(@Param("sampleId") Long sampleId);

}
