package com.sqap.api.domain.audio.test.result.abx;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class AbxFormattedResult {
    private BigInteger test_id;
    private BigInteger answers;
    private BigInteger a_answers;
    private BigInteger b_answers;
    private BigInteger correct_answers;
    private BigInteger correct_a_answers;
    private BigInteger correct_b_answers;
    private Double pValue;


}
