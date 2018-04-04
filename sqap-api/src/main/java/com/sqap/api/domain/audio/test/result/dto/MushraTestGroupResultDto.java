package com.sqap.api.domain.audio.test.result.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MushraTestGroupResultDto extends TestGroupResultDto {
    List<MushraTestResultDto> results;
    @NotNull
    private PrecedingQuestions precedingQuestions;
}
