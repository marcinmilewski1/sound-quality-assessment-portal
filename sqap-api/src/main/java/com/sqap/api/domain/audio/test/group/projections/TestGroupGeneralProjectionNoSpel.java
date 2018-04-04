package com.sqap.api.domain.audio.test.group.projections;


import java.time.LocalDateTime;

public interface TestGroupGeneralProjectionNoSpel {
    Long getId();
    String getName();
    String getDescription();
    Boolean getFinished();
    LocalDateTime getCreatedDate();

    String getOwner();
//
    Integer getTestsNumber();
}
