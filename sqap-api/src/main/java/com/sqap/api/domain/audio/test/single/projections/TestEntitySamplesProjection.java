package com.sqap.api.domain.audio.test.single.projections;

import com.sqap.api.domain.audio.test.sample.TestSampleEntity;
import com.sqap.api.domain.audio.test.single.TestEntity;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "samples", types = {TestEntity.class})
public interface TestEntitySamplesProjection {
    Integer getId();
    Integer getOrderNumber();
    String getName();
    String getDescription();
    //    @Value("#{target.samples}")
    List<TestSampleEntity> getSamples();
}
