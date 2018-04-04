package com.sqap.api.domain.audio.test.result.dto;

import lombok.Data;

@Data
public class AbxTestResultDto extends TestResultDto{
    private String abxResult;
    private String abxBlindSampleKey;
}
