package com.sqap.api.domain.user;

import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(collectionResourceRel = "users", path = "users", exported = false)
public interface UserRepository extends SecuredJpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String userName);

    @Override
    default String getTargetType() {
        return UserEntity.class.getTypeName();
    }
}
