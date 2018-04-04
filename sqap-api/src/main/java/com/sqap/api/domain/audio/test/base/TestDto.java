package com.sqap.api.domain.audio.test.base;

import com.sqap.api.domain.audio.test.sample.TestSampleDto;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class TestDto implements Serializable {
    private Integer orderNumber;
    private String name;
    private String description;
    private List<TestSampleDto> samples;
    private Integer repetitionNumber;
}
