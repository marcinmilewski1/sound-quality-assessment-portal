package com.sqap.api.domain.audio.test.group.projections;


import com.sqap.api.domain.audio.test.base.TestGroupEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "general", types = { TestGroupEntity.class })
public interface TestGroupGeneralProjection {
    Long getId();
    String getName();
    String getDescription();
    Boolean getFinished();
    LocalDateTime getCreatedDate();
    String getCreatedBy();
    Integer getEstimatedDurationTime();
    String getResearchPurpose();
    Boolean getAnonymouseAllowed();
    @Value("#{target.tests.size()}")
    Integer getTestsNumber();
}
