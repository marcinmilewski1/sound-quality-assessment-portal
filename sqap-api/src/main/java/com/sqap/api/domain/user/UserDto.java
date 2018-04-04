package com.sqap.api.domain.user;

import com.sqap.api.domain.audio.test.base.Sex;
import lombok.Value;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class UserDto {
    @NotNull
    @Size(min = 4)
    String username;

    @NotNull
    @Size(min = 6)
    String password;

    @NotNull
    String passwordConfirmation;

    @NotNull
    @Email
    String email;

    @AssertTrue
    private Boolean registerStatementConfirmed;

    @NotNull
    private Boolean isHearingDefect;

    @NotNull
    private Sex sex;

    @NotNull
    private Integer age;
}
