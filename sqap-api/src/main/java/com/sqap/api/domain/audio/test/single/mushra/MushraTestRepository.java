package com.sqap.api.domain.audio.test.single.mushra;

import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MushraTestRepository extends SecuredJpaRepository<MushraTestEntity, Long> {
    @Override
    default String getTargetType() {
        return MushraTestRepository.class.getTypeName();
    }

    @Query(value = "SELECT\n" +
            "  gr.test_id              AS test_id,\n" +
            "  mtsr.key                AS sample_key,\n" +
            "  count(mtr.id)           AS total_answers,\n" +
            "  avg(mtsr.value)         AS mean,\n" +
            "  stddev_samp(mtsr.value) AS stddev_samp,\n" +
            "  stddev_pop(mtsr.value)  AS stddev_pop,\n" +
            "  var_samp(mtsr.value)    AS var_samp,\n" +
            "  var_pop(mtsr.value)     AS var_pop\n" +
            "FROM test_results tr\n" +
            "  JOIN group_results gr on gr.id = tr.test_group_result_id\n" +
            "  JOIN mushra_test_results mtr on mtr.id = tr.id\n" +
            "  JOIN mushra_test_sample_results mtsr ON mtsr.test_result_id = tr.id\n" +
            " WHERE gr.test_id = ?1\n" +
            "GROUP BY gr.test_id, mtsr.key\n" +
            "ORDER BY mtsr.key ASC", nativeQuery = true)
    List<Object[]> getResultsFormatted(Long testId);

}
