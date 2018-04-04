package com.sqap.api.domain.audio.test.single.abx;

import com.sqap.api.domain.base.SecuredJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@RepositoryRestResource( collectionResourceRel = "tests", path="abxTests")
@Repository
public interface AbxTestRepository extends SecuredJpaRepository<AbxTestEntity, Long> {

    @Override
    default String getTargetType() {
        return AbxTestEntity.class.getTypeName();
    }

    @Query(value = "SELECT DISTINCT\n" +
            "gr.test_id AS test_id,\n" +
            "count(tr.id) AS answers,\n" +
            "count( CASE answer\n" +
            "WHEN 'A'\n" +
            "THEN 1\n" +
            "ELSE NULL END ) AS a_answers,\n" +
            "count( CASE answer\n" +
            "WHEN 'B'\n" +
            "THEN 1\n" +
            "ELSE NULL END ) AS b_answers,\n" +
            "count( CASE WHEN answer = blind_key\n" +
            "THEN 1\n" +
            "ELSE NULL END ) AS correct_answers,\n" +
            "count( CASE WHEN (answer = blind_key AND answer = 'A')\n" +
            "THEN 1\n" +
            "ELSE NULL END ) AS correct_a_answers,\n" +
            "count( CASE WHEN (answer = blind_key AND answer = 'B')\n" +
            "THEN 1\n" +
            "ELSE NULL END ) AS correct_b_answers\n" +
            "from test_results tr\n" +
            "JOIN group_results gr on gr.id = tr.test_group_result_id\n" +
            "JOIN abx_group_results agr on agr.id = tr.test_group_result_id\n" +
            "JOIN abx_test_results atr ON atr.id = tr.id\n" +
            "WHERE gr.test_id = ?1\n" +
            "GROUP BY gr.test_id", nativeQuery = true)
    List<Object[]> getResultsFormatted(Long testId);
}
