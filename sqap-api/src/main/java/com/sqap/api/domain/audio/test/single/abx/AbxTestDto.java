package com.sqap.api.domain.audio.test.single.abx;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sqap.api.domain.audio.test.base.AbstractTestDto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonTypeName("abx")
public class AbxTestDto extends AbstractTestDto {

    @NotNull
    private UUID sampleA; // reference
    @NotNull
    private UUID sampleB;

    public AbxTestDto(String name, String method, UUID sampleA, UUID sampleB) {
        setName(name);
        setMethod(method);
        this.sampleA = sampleA;
        this.sampleB = sampleB;
    }

    public AbxTestDto() {
    }

    public UUID getSampleA() {
        return sampleA;
    }

    public void setSampleA(UUID sampleA) {
        this.sampleA = sampleA;
    }

    public UUID getSampleB() {
        return sampleB;
    }

    public void setSampleB(UUID sampleB) {
        this.sampleB = sampleB;
    }
}
