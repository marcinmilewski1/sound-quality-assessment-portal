package com.sqap.api.domain.audio.test.base;

import lombok.Value;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Value
public class TestGroupDto implements Serializable {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private TestsDto tests;
    @NotNull
    private Formalities formalities;
}

