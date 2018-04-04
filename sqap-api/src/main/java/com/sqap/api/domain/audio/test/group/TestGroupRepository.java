package com.sqap.api.domain.audio.test.group;

import com.sqap.api.domain.audio.test.base.TestGroupEntity;
import com.sqap.api.domain.audio.test.group.projections.TestGroupGeneralProjection;
import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "testGroups", path="groups")
//@PreAuthorize("hasRole('ROLE_USER')")
public interface TestGroupRepository extends SecuredJpaRepository<TestGroupEntity, Long> {

    @Override
    default String getTargetType() {
        return TestGroupEntity.class.getTypeName();
    }

    @RestResource(exported = false)
    Page<TestGroupEntity> findByOwnerId(Long userId, Pageable pageable);

    @RestResource(exported = false)
    Page<TestGroupGeneralProjection> findGeneralProjectedByOwnerId(Long userId, Pageable pageable);

    @Query(value = "SELECT DISTINCT *" +
            "FROM test_groups tg  \n" +
            "inner join users u on u.id = tg.owner \n" +
            "WHERE tg.finished = false AND NOT EXISTS(SELECT 1 \n" +
            "                 FROM users_participated_tests upt \n" +
            "                 WHERE upt.test_group_id = tg.id AND upt.user_id = ?1)\n",
            nativeQuery = true)
    @RestResource(exported = false)
    List<TestGroupEntity> findNotParticipatedAndNotFinished(Long userId);

    Page<TestGroupEntity> findByAnonymouseAllowedTrue(Pageable pageable);

    Page<TestGroupEntity> findByFinished(boolean finished, Pageable pageable);
}
