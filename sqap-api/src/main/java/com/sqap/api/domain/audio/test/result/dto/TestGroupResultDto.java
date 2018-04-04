package com.sqap.api.domain.audio.test.result.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sqap.api.domain.audio.test.base.TestType;
import lombok.Data;

import javax.validation.constraints.NotNull;

//@Data
/*
Should be replaced with polimorphic jackson dto
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AbxTestGroupResultDto.class, name = "ABX"),

        @JsonSubTypes.Type(value = MushraTestGroupResultDto.class, name = "MUSHRA") }
)
public abstract class TestGroupResultDto {
    @NotNull
    private TestType type;
    @NotNull
    private PrecedingQuestions precedingQuestions;

}

