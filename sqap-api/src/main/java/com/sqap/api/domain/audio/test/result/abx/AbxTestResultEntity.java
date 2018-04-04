package com.sqap.api.domain.audio.test.result.abx;

import com.sqap.api.domain.audio.test.result.TestResultEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ABX_TEST_RESULTS")
@Getter
@PrimaryKeyJoinColumn(name="ID")
@NoArgsConstructor
public class AbxTestResultEntity extends TestResultEntity {

    @Column(name = "BLIND_KEY", nullable = false)
    @Enumerated(EnumType.STRING)
    private AbxAnswerType blindSampleKey;

    @Column(name = "ANSWER", nullable = false)
    @Enumerated(EnumType.STRING)
    private AbxAnswerType answer;

    public AbxTestResultEntity(AbxAnswerType blindSampleKey, AbxAnswerType answer, Integer iteration) {
        super(iteration);
        this.blindSampleKey = blindSampleKey;
        this.answer = answer;
    }

}
