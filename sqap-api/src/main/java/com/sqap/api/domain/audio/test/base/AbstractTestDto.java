package com.sqap.api.domain.audio.test.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sqap.api.domain.audio.test.single.abx.AbxTestDto;
import com.sqap.api.domain.audio.test.single.mushra.MushraTestDto;

import javax.validation.constraints.NotNull;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
        include=JsonTypeInfo.As.PROPERTY,
        property="method")
@JsonSubTypes({
        @JsonSubTypes.Type(value=AbxTestDto.class, name="ABX"),
        @JsonSubTypes.Type(value=MushraTestDto.class, name="MUSHRA"),
})
public abstract class AbstractTestDto {
    @NotNull
    protected String method;
    @NotNull
    protected String name;

    public void setMethod(String method) {
        this.method = method;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public String getName() {
        return name;
    }


}
