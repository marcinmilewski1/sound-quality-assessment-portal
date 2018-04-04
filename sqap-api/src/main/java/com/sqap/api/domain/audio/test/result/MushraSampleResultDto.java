package com.sqap.api.domain.audio.test.result;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class MushraSampleResultDto {
    private String key;
    @Min(value = 0)
    @Max(value = 100)
    private Integer value;
}
