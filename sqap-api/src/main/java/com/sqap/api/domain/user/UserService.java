package com.sqap.api.domain.user;

public interface UserService {
    UserEntity save(UserDto user);

    UserRepository getRepository();

    UserEntity findRequester();

}
