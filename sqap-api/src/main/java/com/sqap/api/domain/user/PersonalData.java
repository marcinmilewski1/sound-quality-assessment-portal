package com.sqap.api.domain.user;

import com.sqap.api.domain.audio.test.base.Sex;
import lombok.Value;

@Value
public class PersonalData {
    private Sex sex;
    private Integer age;
    private Boolean isHearingDefect;
}