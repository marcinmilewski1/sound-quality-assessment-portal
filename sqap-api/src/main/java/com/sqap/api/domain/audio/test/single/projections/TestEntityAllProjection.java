package com.sqap.api.domain.audio.test.single.projections;

import com.sqap.api.domain.audio.test.result.TestResultGroupEntity;
import com.sqap.api.domain.audio.test.sample.TestSampleEntity;
import com.sqap.api.domain.audio.test.single.TestEntity;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.List;

@Projection(name = "all", types = {TestEntity.class})
public interface TestEntityAllProjection {
    Integer getId();
    Integer getOrderNumber();
    LocalDateTime getCreatedDate();
    LocalDateTime getUpdatedDate();
    String getName();
    String getType();
    String getDescription();
    //    @Value("#{target.samples}")
    List<TestSampleEntity> getSamples();
    //    @Value("#{target.testResults}")
//    List<TestResultEntity> getTestResults();
    List<TestResultGroupEntity> getTestResultGroups();
}
