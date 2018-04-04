package com.sqap.api.domain.audio.test.result.dto;

import com.sqap.api.domain.audio.test.result.TestResultGroupEntity;
import com.sqap.api.domain.audio.test.result.abx.AbxAnswerType;
import com.sqap.api.domain.audio.test.result.abx.AbxTestResultEntity;
import com.sqap.api.domain.audio.test.result.abx.AbxTestResultGroupEntity;
import com.sqap.api.domain.audio.test.result.mushra.MushraTestResultEntity;
import com.sqap.api.domain.audio.test.result.mushra.MushraTestResultGroupEntity;
import com.sqap.api.domain.audio.test.result.mushra.MushraTestSampleResultEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class TestResultCreator {

    public static TestResultGroupEntity create(TestGroupResultDto testGroupResultDto) {
        if (testGroupResultDto instanceof AbxTestGroupResultDto) {
            Set<AbxTestResultEntity> results = ((AbxTestGroupResultDto) testGroupResultDto)
                    .getResults()
                    .stream()
                    .map(res -> new AbxTestResultEntity(AbxAnswerType.valueOf(res.getAbxBlindSampleKey()),
                            AbxAnswerType.valueOf(res.getAbxResult()),
                            res.getIteration()))
                    .collect(Collectors.toSet());
            return new AbxTestResultGroupEntity(testGroupResultDto.getPrecedingQuestions(), results);
        } else if (testGroupResultDto instanceof MushraTestGroupResultDto) {
            Set<MushraTestResultEntity> results = ((MushraTestGroupResultDto) testGroupResultDto)
                    .getResults()
                    .stream()
                    .map(res -> new MushraTestResultEntity(res.getIteration(), res.getSampleResults()
                            .stream()
                            .map(dto -> new MushraTestSampleResultEntity(dto.getKey(), dto.getValue()))
                            .collect(Collectors.toSet()))
                    )
                    .collect(Collectors.toSet());
            return new MushraTestResultGroupEntity(testGroupResultDto.getPrecedingQuestions(), results);
        } else {
            throw new RuntimeException("Wrong result type");
        }
    }
}
