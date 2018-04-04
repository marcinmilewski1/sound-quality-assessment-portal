package com.sqap.api.domain.audio.test.result.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
public class AbxTestGroupResultDto extends TestGroupResultDto {
    @NotEmpty
    List<AbxTestResultDto> results;

}
