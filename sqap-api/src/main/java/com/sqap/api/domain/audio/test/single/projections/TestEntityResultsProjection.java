package com.sqap.api.domain.audio.test.single.projections;

import com.sqap.api.domain.audio.test.result.TestResultGroupEntity;
import com.sqap.api.domain.audio.test.single.TestEntity;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "results", types = {TestEntity.class})
public interface TestEntityResultsProjection {
    Integer getId();
    Integer getOrderNumber();
    String getName();
    String getDescription();
    List<TestResultGroupEntity> getTestResultGroups();
}
