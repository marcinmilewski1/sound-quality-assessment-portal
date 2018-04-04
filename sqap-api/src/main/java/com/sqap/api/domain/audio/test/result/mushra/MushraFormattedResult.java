package com.sqap.api.domain.audio.test.result.mushra;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@SqlResultSetMapping(
//        name="MushraTestRepository.getResultsFormatted",
//        classes={
//                @ConstructorResult(
//                        targetClass=MushraFormattedResult.class,
//                        columns={
//                                @ColumnResult(name="test_id"),
//                                @ColumnResult(name="sample_key"),
//                                @ColumnResult(name="total_answers"),
//                                @ColumnResult(name="mean"),
//                                @ColumnResult(name="stddev_samp"),
//                                @ColumnResult(name="stddev_pop"),
//                                @ColumnResult(name="var_samp"),
//                                @ColumnResult(name="var_pop")
//                        }
//                )
//        }
//)
//@NamedNativeQuery(name = "MushraTestRepository.getResultsFormatted",
//        query = "SELECT\n" +
//                "  tr.test_id AS test_id,\n" +
//                "  mtsr.key   AS sample_key,\n" +
//                "  count(mtr.id) AS total_answers,\n" +
//                "  avg(mtsr.value) as mean,\n" +
//                "  stddev_samp(mtsr.value) as stddev_samp,\n" +
//                "  stddev_pop(mtsr.value) as stddev_pop,\n" +
//                "  var_samp(mtsr.value) as var_samp,\n" +
//                "  var_pop(mtsr.value) as var_pop\n" +
//                "FROM test_results tr\n" +
//                "  JOIN audio_tests at ON tr.test_id = at.id\n" +
//                "  JOIN mushra_test_results mtr ON mtr.id = tr.id\n" +
//                "  JOIN mushra_test_sample_results mtsr ON mtsr.test_result_id = tr.id\n" +
//                "  WHERE at.id = ?\n" +
//                "GROUP BY tr.test_id, mtsr.key\n")
public class MushraFormattedResult {
    private Object testId;
    private Object sampleKey;
    private Object totalAnswers;
    private Object mean;
    private Object sampleStdDeviation;
    private Object populationStdDeviation;
    private Object sampleVariance;
    private Object populationVariance;
}
