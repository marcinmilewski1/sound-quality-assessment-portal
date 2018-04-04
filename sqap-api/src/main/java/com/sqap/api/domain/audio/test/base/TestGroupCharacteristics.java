package com.sqap.api.domain.audio.test.base;

import lombok.Value;

@Value
public class TestGroupCharacteristics {
    boolean creatorStatementAccepted;
    boolean anonymouseAllowed;
    String researchPurpose;
    Integer estimatedDurationTime;
}
