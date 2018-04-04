package com.sqap.api.domain.audio.test.sample;

import lombok.Value;

import java.io.Serializable;

@Value
public class TestSampleDto implements Serializable {
    private String key;
    private String fileDesc;
}
