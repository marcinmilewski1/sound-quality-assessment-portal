package com.sqap.api.domain.audio.test.result.dto;

import com.sqap.api.domain.audio.test.base.Sex;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class PrecedingQuestions {
    @NotNull
    private Boolean isHearingDefect;
    @NotNull
    private Integer age;
    @NotNull
    private Sex sex;
}
