package com.sqap.api.domain.audio.test.base;

import lombok.Value;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class Formalities {
    @AssertTrue
    private boolean globalRulesForCreatorAccepted;
    private boolean allowAnonymouse;
    @NotEmpty
    private String purposeOfResearch;
    @NotNull
    @Min(1)
    private Integer durationTime;
}
