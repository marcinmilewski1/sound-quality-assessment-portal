package com.sqap.api.domain.audio.test.result.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class MushraTestSampleResultDto {
    private String key;
    @Min(value = 0)
    @Max(value = 100)
    private Integer value;
}
