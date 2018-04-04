package com.sqap.api.domain.audio.test.result.dto;

import lombok.Data;

import java.util.List;

@Data
public class MushraTestResultDto extends TestResultDto {
    private List<MushraTestSampleResultDto> sampleResults;
}
