package com.sqap.api.domain.audio.test.single.mushra;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sqap.api.domain.audio.test.base.AbstractTestDto;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@JsonTypeName("mushra")
public class MushraTestDto extends AbstractTestDto{
    @NotNull
    private MultipartFile sampleA; // reference
    @NotNull
    private MultipartFile sampleB;

    public MushraTestDto() {

    }

    public MushraTestDto(String name, String method, MultipartFile sampleA, MultipartFile sampleB) {
        setName(name);
        setMethod(method);
        this.sampleA = sampleA;
        this.sampleB = sampleB;
    }

    public MultipartFile getSampleA() {
        return sampleA;
    }

    public void setSampleA(MultipartFile sampleA) {
        this.sampleA = sampleA;
    }

    public MultipartFile getSampleB() {
        return sampleB;
    }

    public void setSampleB(MultipartFile sampleB) {
        this.sampleB = sampleB;
    }
}

